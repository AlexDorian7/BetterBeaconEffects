#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;

uniform mat4 ProjMat;
uniform mat4 ModelViewMat;
uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

in vec4 vertexColor;
in vec2 texCoord0;
in vec2 texCoord1;
in vec3 fragPos;
in vec3 normal;

out vec4 fragColor;

mat3 computeTBN(vec3 normal) {
    vec3 tangent = normalize(abs(normal.z) < 0.999 ? cross(normal, vec3(0.0, 0.0, 1.0))
    : cross(normal, vec3(1.0, 0.0, 0.0)));
    vec3 bitangent = cross(normal, tangent);
    return transpose(mat3(tangent, bitangent, normal));
}

vec2 parallaxMapping(vec2 texCoords, sampler2D depthMap, vec3 viewDir, float heightScale) {
    float height = texture(depthMap, texCoords).r;
    vec2 p = viewDir.xy  * (height * heightScale);
    return texCoords - p;
}

vec2 steepParallaxMapping(vec2 texCoords, sampler2D depthMap, vec3 viewDir, float heightScale)
{
    // number of depth layers
    const float numLayers = 10;
    // calculate the size of each layer
    float layerDepth = 1.0 / numLayers;
    // depth of current layer
    float currentLayerDepth = 0.0;
    // the amount to shift the texture coordinates per layer (from vector P)
    vec2 P = viewDir.xy * heightScale;
    vec2 deltaTexCoords = P / numLayers;

    // get initial values
    vec2  currentTexCoords     = texCoords;
    float currentDepthMapValue = texture(depthMap, currentTexCoords).r;

    while(currentLayerDepth < currentDepthMapValue)
    {
        // shift texture coordinates along direction of P
        currentTexCoords -= deltaTexCoords;
        // get depthmap value at current texture coordinates
        currentDepthMapValue = texture(depthMap, currentTexCoords).r;
        // get depth of next layer
        currentLayerDepth += layerDepth;
    }

    return currentTexCoords;
}

vec4 sampleLayer(vec2 texCoord, float depth, vec2 normal) {
    vec2 shifted = texCoord/depth + normal * depth;
    return texture(Sampler0, shifted);
}

void main() {
    vec3 viewDir = normalize(-fragPos); // view direction in view space
    mat3 TBN = computeTBN(normal);
    vec3 viewDirT = TBN * viewDir;

    vec4 color = vec4(0, 0, 0, 1);

    for (int i=1; i<4; i++) {
        color += sampleLayer(texCoord0, i/2.0, normal.xy);
    }

    color *= vertexColor * ColorModulator;
    float fragmentDistance = -ProjMat[3].z / ((gl_FragCoord.z) * -2.0 + 1.0 - ProjMat[2].z);
    fragColor = linear_fog(color, fragmentDistance, FogStart, FogEnd, FogColor);
}
