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

mat3 computeTBN(vec3 normal, vec2 uv, vec3 pos)
{
    vec3 dp1 = dFdx(pos);
    vec3 dp2 = dFdy(pos);
    vec2 duv1 = dFdx(uv);
    vec2 duv2 = dFdy(uv);

    vec3 tangent = normalize(dp1 * duv2.y - dp2 * duv1.y);
    vec3 bitangent = normalize(-dp1 * duv2.x + dp2 * duv1.x);

    return mat3(tangent, bitangent, normal);
}

mat3 computeFallbackTBN(vec3 N)
{
    vec3 up = vec3(0.0, 1.0, 0.0);
    vec3 right = vec3(1.0, 0.0, 0.0);
    vec3 tangent = normalize(abs(N.y) < 0.999 ? cross(up, N) : cross(right, N));
    vec3 bitangent = normalize(cross(N, tangent));
    return mat3(tangent, bitangent, N);
}


vec2 parallaxMapping(vec2 texCoords, sampler2D heightMap, vec3 viewDir, float heightScale) {
    float height = 1-texture(heightMap, texCoords).r;
    vec2 p = viewDir.xy  * (height * heightScale);
    return texCoords - p;
}

vec2 steepParallaxMapping(vec2 texCoords, sampler2D heightMap, vec3 viewDir, float heightScale)
{
    // number of depth layers
    const float minLayers = 8.0;
    const float maxLayers = 32.0;
    float numLayers = mix(maxLayers, minLayers, max(dot(vec3(0.0, 0.0, 1.0), viewDir), 0.0));
    // calculate the size of each layer
    float layerDepth = 1.0 / numLayers;
    // depth of current layer
    float currentLayerDepth = 0.0;
    // the amount to shift the texture coordinates per layer (from vector P)
    vec2 P = viewDir.xy * heightScale;
    vec2 deltaTexCoords = P / numLayers;

    // get initial values
    vec2  currentTexCoords     = texCoords;
    float currentDepthMapValue = 1-texture(heightMap, currentTexCoords).r;

    while(currentLayerDepth < currentDepthMapValue)
    {
        // shift texture coordinates along direction of P
        currentTexCoords -= deltaTexCoords;
        // get depthmap value at current texture coordinates
        currentDepthMapValue = 1-texture(heightMap, currentTexCoords).r;
        // get depth of next layer
        currentLayerDepth += layerDepth;
    }

    // get texture coordinates before collision (reverse operations)
    vec2 prevTexCoords = currentTexCoords + deltaTexCoords;

    // get depth after and before collision for linear interpolation
    float afterDepth  = currentDepthMapValue - currentLayerDepth;
    float beforeDepth = 1-texture(heightMap, prevTexCoords).r - currentLayerDepth + layerDepth;

    // interpolation of texture coordinates
    float weight = afterDepth / (afterDepth - beforeDepth);
    vec2 finalTexCoords = prevTexCoords * weight + currentTexCoords * (1.0 - weight);

    return finalTexCoords;
}

void main() {


    vec3 viewDir = normalize(-fragPos); // view direction in view space

    mat3 TBN;
    float upness = abs(dot(normalize(normal), vec3(0, 0, 1)));
    if (upness > 0.9) {
        // Near-top or bottom face, fallback to axis-aligned TBN
        TBN = computeFallbackTBN(normalize(normal));
    } else {
        // Default to screen-space TBN
        TBN = computeTBN(normalize(normal), texCoord0, fragPos);
    }


    vec3 viewDirT = TBN * viewDir;
    vec2 texCoord = steepParallaxMapping(texCoord0, Sampler0, viewDirT, 0.1);
    vec4 tex = texture(Sampler0, texCoord);
    vec4 color = tex; //vec4((viewDir+1)/2, 1); //vec4((TBN[1]+1)/2, 1);

    color *= vertexColor * ColorModulator;
    float fragmentDistance = -ProjMat[3].z / ((gl_FragCoord.z) * -2.0 + 1.0 - ProjMat[2].z);
    fragColor = linear_fog(color, fragmentDistance, FogStart, FogEnd, FogColor);
}
