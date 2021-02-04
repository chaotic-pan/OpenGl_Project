//vertex shader first
#version 330

layout(location=0) in vec4 cornersJ;
layout(location=1) in vec3 colorJ;
layout(location=2) in vec3 normalsJ;
uniform mat4 posJ;
uniform mat4 projectionsMatrix;
out vec4 normals;
out vec3 p;
out vec3 color;

void main() {

    gl_Position = projectionsMatrix * posJ * cornersJ;

    mat4 normalsMat = inverse(transpose(posJ));
    normals = normalsMat * vec4(normalsJ,1);
    p= (posJ * cornersJ).xyz;

    color=colorJ;

}