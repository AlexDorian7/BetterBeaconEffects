#version 150

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in vec2 UV1;
in vec3 Normal;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

out vec4 vertexColor;
out vec2 texCoord0;
out vec2 texCoord1;
out vec3 fragPos;
out vec3 normal;

void main() {
    vec4 viewPosition = ModelViewMat * vec4(Position, 1.0);
    gl_Position = ProjMat * viewPosition;

    normal = normalize(mat3(ModelViewMat) * Normal);
    fragPos = viewPosition.xyz;
    vertexColor = Color;
    texCoord0 = UV0;
    texCoord1 = UV1;
}
