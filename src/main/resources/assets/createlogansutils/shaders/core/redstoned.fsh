#version 150

uniform sampler2D DiffuseSampler;
uniform vec2 InSize;
uniform float Time;
uniform float Intensity;

in vec2 texCoord;
out vec4 fragColor;

float rand(vec2 co) {
    return fract(sin(dot(co, vec2(12.9898, 78.233)) + Time) * 43758.5453);
}

void main() {
    vec2 uv = texCoord;
    vec2 texel = 1.0 / InSize;

    // box blur
    vec4 color = vec4(0.0);
    float samples = 0.0;
    for (float x = -2.0; x <= 2.0; x += 1.0) {
        for (float y = -2.0; y <= 2.0; y += 1.0) {
            color += texture(DiffuseSampler, uv + vec2(x, y) * texel * 1.5 * Intensity);
            samples += 1.0;
        }
    }
    color /= samples;

    // punch contrast up hard
    color.rgb = (color.rgb - 0.5) * (1.0 + 1.2 * Intensity) + 0.5;

    // flickering static/fuzz, per-pixel per-frame
    float noise = rand(gl_FragCoord.xy) * 0.25 * Intensity;
    color.rgb += noise - (0.125 * Intensity);

    // faint red push to sell "redstoned" rather than generic drunk/nausea
    color.rgb = mix(color.rgb, color.rgb * vec3(1.15, 0.85, 0.85), 0.3 * Intensity);

    fragColor = vec4(clamp(color.rgb, 0.0, 1.0), 1.0);
}