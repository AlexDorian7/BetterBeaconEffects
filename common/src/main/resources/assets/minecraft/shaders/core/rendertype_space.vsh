#version 330
#extension GL_ARB_separate_shader_objects : require

#include <minecraft:fog.glsl>
#include <minecraft:projection.glsl>
#include <minecraft:dynamictransforms.glsl>

layout(location = 0) in vec3 Position;
layout(location = 1) in vec4 Color;
layout(location = 2) in vec3 Normal;

layout(location = 0) out vec4 texProj0;
layout(location = 1) out float sphericalVertexDistance;
layout(location = 2) out float cylindricalVertexDistance;
layout(location = 3) out vec4 vertexColor;
layout(location = 4) out vec3 normal;
layout(location = 5) out vec3 viewPosition;

void main() {
    vec4 modelViewPosition = ModelViewMat * vec4(Position, 1.0);

    gl_Position = ProjMat * modelViewPosition;

    viewPosition = modelViewPosition.xyz;

    texProj0 = projection_from_position(gl_Position);
    sphericalVertexDistance = fog_spherical_distance(Position);
    cylindricalVertexDistance = fog_cylindrical_distance(Position);

    vertexColor = Color;
    normal = Normal;
}