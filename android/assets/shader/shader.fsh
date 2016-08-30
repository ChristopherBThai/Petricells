varying vec4 v_color;
varying vec2 v_texCoord0;
varying vec2 v_blurTexCoords[14];

uniform sampler2D u_sampler2D;

void main(){
	gl_FragColor = texture2D(u_sampler2D, v_texCoord0) *v_color;

    gl_FragColor += texture2D(u_sampler2D, v_blurTexCoords[ 0])*v_color*0.0044299121055113265;
    gl_FragColor += texture2D(u_sampler2D, v_blurTexCoords[ 1])*v_color*0.00895781211794;
    gl_FragColor += texture2D(u_sampler2D, v_blurTexCoords[ 2])*v_color*0.0215963866053;
    gl_FragColor += texture2D(u_sampler2D, v_blurTexCoords[ 3])*v_color*0.0443683338718;
    gl_FragColor += texture2D(u_sampler2D, v_blurTexCoords[ 4])*v_color*0.0776744219933;
    gl_FragColor += texture2D(u_sampler2D, v_blurTexCoords[ 5])*v_color*0.115876621105;
    gl_FragColor += texture2D(u_sampler2D, v_blurTexCoords[ 6])*v_color*0.147308056121;
    gl_FragColor += texture2D(u_sampler2D, v_texCoord0        )*v_color*0.159576912161;
    gl_FragColor += texture2D(u_sampler2D, v_blurTexCoords[ 7])*v_color*0.147308056121;
    gl_FragColor += texture2D(u_sampler2D, v_blurTexCoords[ 8])*v_color*0.115876621105;
    gl_FragColor += texture2D(u_sampler2D, v_blurTexCoords[ 9])*v_color*0.0776744219933;
    gl_FragColor += texture2D(u_sampler2D, v_blurTexCoords[10])*v_color*0.0443683338718;
    gl_FragColor += texture2D(u_sampler2D, v_blurTexCoords[11])*v_color*0.0215963866053;
    gl_FragColor += texture2D(u_sampler2D, v_blurTexCoords[12])*v_color*0.00895781211794;
    gl_FragColor += texture2D(u_sampler2D, v_blurTexCoords[13])*v_color*0.0044299121055113265;
}