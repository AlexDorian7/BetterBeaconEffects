#version 330
#extension GL_ARB_separate_shader_objects : require

#include <minecraft:fog.glsl>
#include <minecraft:matrix.glsl>
#include <minecraft:globals.glsl>
#include <minecraft:texture_sampling.glsl>
#include <minecraft:projection.glsl>
#include <minecraft:dynamictransforms.glsl>
#include <minecraft:dither.glsl>

uniform sampler2D Sampler0;
uniform sampler2D Sampler1;

layout(location = 0) in vec4 texProj0;
layout(location = 1) in float sphericalVertexDistance;
layout(location = 2) in float cylindricalVertexDistance;
layout(location = 3) in vec4 vertexColor;
layout(location = 4) in vec3 normal;
layout(location = 5) in vec3 viewPosition;

layout(location = 0) out vec4 fragColor;

void main() {
    vec3 viewPos = viewPosition;

    mat4 inverseModelView = inverse(ModelViewMat);

    vec3 cameraPosition = vec3(CameraBlockPos) + CameraOffset;
    vec3 playerPos = cameraPosition;

    vec3 upVec = vec3(0.0, 1.0, 0.0);
    vec3 eastVec = vec3(1.0, 0.0, 0.0);

    float NdotU = dot(normal, upVec);

    float animationTime = GameTime * 1200.0;

    float dither = Bayer64(gl_FragCoord.xy);

    vec4 c = vec4(vec3(dither * 0.125), 1.0);

    int repeat = 9;
    float dismult = 2.0;


    for (int j = 0; j < repeat; j++) {
        float add = float(j+dither) * 0.5 / float(repeat);
        for (int i = 1; i <= 9; i++) {
            float colormult = 1.0/i;
            float rotation = (i - 0.1 * i + 0.71 * i - 11 * i + 21) * 0.01 + i * 0.01;
            float Cos = cos(radians(rotation));
            float Sin = sin(radians(rotation));
            vec2 offset = vec2(0.0, 1.0/(3600.0/24.0)) * pow(16.0 - i, 2.0) * 0.004;

            float distance = i * dismult;
            vec3 wpos = normalize((inverseModelView * vec4(viewPos * distance, 1.0)).xyz);
            if (abs(NdotU) > 0.5) {
                wpos.xz /= wpos.y;
                wpos.xz *= 0.06 * sign(- playerPos.y);
                wpos.xz *= abs(playerPos.y) + i * dismult + add;
                wpos.xz -= cameraPosition.xz * 0.05;
            } else {
                vec3 absPos = abs(playerPos);
                if (abs(dot(normal, eastVec)) > 0.5) {
                    wpos.xz = wpos.yz / wpos.x;
                    wpos.xz *= 0.06 * sign(- playerPos.x);
                    wpos.xz *= abs(playerPos.x) + i * dismult + add;
                    wpos.xz -= cameraPosition.yz * 0.05;
                } else {
                    wpos.xz = wpos.yx / wpos.z;
                    wpos.xz *= 0.06 * sign(- playerPos.z);
                    wpos.xz *= abs(playerPos.z) + i * dismult + add;
                    wpos.xz -= cameraPosition.yx * 0.05;
                }
            }
            vec2 pos = wpos.xz * 0.125;

            vec2 wind = fract((animationTime + 984.0) * (i + 8) * 0.125 * offset);
            vec2 coord = mat2(Cos, Sin, -Sin, Cos) * pos + wind;
            if (mod(float(i), 4) < 1.5) coord = coord.yx + vec2(-1.0, 1.0) * wind.y;
            vec2 texSize = vec2(1.0 / 256.0, 1.0 / 256.0);
            vec3 psample = pow(sampleNearest(Sampler1, coord, texSize).rgb, vec3(0.85)) * vertexColor.rgb * colormult;
            c.rgb += psample * length(psample.rgb) * (3000.0 / repeat);
        }
    }
    c.rgb *= 0.1;
    fragColor = c;
}
