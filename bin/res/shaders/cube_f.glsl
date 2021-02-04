//fragment shader second
#version 330

in vec3 color;
in vec4 normals;
in vec3 p;
out vec3 pixelfarbe;
vec3 lightPos = vec3(0.5f,0.5f,3);

in vec2 uvCoords;
uniform sampler2D sampler;

void main() {
    vec3 N = normalize(normals.xyz);
    vec3 L = normalize(lightPos - p);
    vec3 R = reflect(-L,N);
    vec3 V = normalize(-p);
    float I = dot(L,N) + dot(R,V);

    pixelfarbe= texture(sampler,uvCoords).rgb*I;

    //pixelfarbe= color*I;
}