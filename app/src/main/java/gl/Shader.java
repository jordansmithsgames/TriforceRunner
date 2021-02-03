package gl;

import android.opengl.GLES30;

public class Shader {


    public int shaderProgram=0;
   // public int sceneMatricesBuffer;


    public Shader(String vertexShaderCode, String fragmentShaderCode){
        shaderProgram= Shader.compileShader(vertexShaderCode,fragmentShaderCode,new String[]{"vertexPosition"});
        GLES30.glUseProgram(shaderProgram);
    }

    public Shader(String vertexShaderCode, String fragmentShaderCode, String[] attribLocations){
        shaderProgram= Shader.compileShader(vertexShaderCode,fragmentShaderCode,attribLocations);
        GLES30.glUseProgram(shaderProgram);
    }

   /* public void setSceneMatricesBuffer(int buffer){
        sceneMatricesBuffer=buffer;
        //GLES30.glUseProgram(shaderProgram);
        GLES30.glBindBufferBase( GLES30.GL_UNIFORM_BUFFER, 0, sceneMatricesBuffer  );
        //GLES30.glUseProgram(0);
    }*/

    public void setUniformBuffer(int buffer,int slot){

    }

    public void use(){
        // Add program to OpenGL ES environment
        GLES30.glUseProgram(shaderProgram);
    }


    public void setUniformFloat(String name, float value){
        int mHandle = GLES30.glGetUniformLocation(shaderProgram, name);
        GLES30.glUniform1f(mHandle,value);
    }

    public void setUniformInteger(String name, int value){
        int mHandle = GLES30.glGetUniformLocation(shaderProgram, name);
        GLES30.glUniform1i(mHandle,value);
    }

    public void setUniformVec2(String name, float[] value){
        int mHandle = GLES30.glGetUniformLocation(shaderProgram, name);
        GLES30.glUniform2fv(mHandle, 1,value , 0);
    }

    public void setUniformVec3(String name, float[] value){
        int mHandle = GLES30.glGetUniformLocation(shaderProgram, name);
        GLES30.glUniform3fv(mHandle, 1,value , 0);
    }

    public void setUniformVec4(String name, float[] value){
        int mHandle = GLES30.glGetUniformLocation(shaderProgram, name);
        GLES30.glUniform4fv(mHandle, 1,value , 0);
    }

    public void setUniformMat3(String name, float[] value){
        int mHandle = GLES30.glGetUniformLocation(shaderProgram, name);
        GLES30.glUniformMatrix3fv(mHandle, 1,false,value , 0);
    }

    public void setUniformMat4(String name, float[] value){
        int mHandle = GLES30.glGetUniformLocation(shaderProgram, name);
        GLES30.glUniformMatrix4fv(mHandle, 1,false,value , 0);
    }

    public int getUniformLocation(String name){
        return GLES30.glGetUniformLocation(shaderProgram, name);
    }



    public static int compileShader(int type, String shaderCode){

        // create a vertex shader type (GLES30.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES30.GL_FRAGMENT_SHADER)
        int shader = GLES30.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES30.glShaderSource(shader, shaderCode);
        GLES30.glCompileShader(shader);

        return shader;
    }

    public static int compileShader(String vertexShaderCode, String fragmentShaderCode, String[] attribLocations){

        String QuestVertexShaderPreamble="#version 300 es\n"+
                "#define DISABLE_MULTIVIEW 0\n"+
                "#ifndef DISABLE_MULTIVIEW\n"+
                "	#define DISABLE_MULTIVIEW 0\n"+
                "#endif\n"+
                "#define NUM_VIEWS 2\n"+
                "#if defined( GL_OVR_multiview2 ) && ! DISABLE_MULTIVIEW\n"+
                "	#extension GL_OVR_multiview2 : enable\n"+
                "	layout(num_views=NUM_VIEWS) in;\n"+
                "	#define VIEW_ID gl_ViewID_OVR\n"+
                "#else\n"+
                "	uniform lowp int ViewID;\n"+
                "	#define VIEW_ID ViewID\n"+
                "#endif\n";

        String QuestFragmentShaderPreamble="#version 300 es\n";

        int vertexShader = Shader.compileShader(GLES30.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = Shader.compileShader(GLES30.GL_FRAGMENT_SHADER,
                fragmentShaderCode);
        // create empty OpenGL ES Program
        int mProgram = GLES30.glCreateProgram();

        // add the vertex shader to program
        GLES30.glAttachShader(mProgram, vertexShader);

        // add the fragment shader to program
        GLES30.glAttachShader(mProgram, fragmentShader);

        for(int i=0;i<attribLocations.length;i++)
        GLES30.glBindAttribLocation( mProgram, i, attribLocations[i] );


        // creates OpenGL ES program executables
        GLES30.glLinkProgram(mProgram);

        int[] linkStatus = new int[1];
        GLES30.glGetProgramiv(mProgram, GLES30.GL_LINK_STATUS,linkStatus,0);
        if (linkStatus[0] != GLES30.GL_TRUE) {
            String error = GLES30.glGetProgramInfoLog(mProgram);
            throw new RuntimeException("GLES Error: "+error);
        }

        int l= GLES30.glGetUniformBlockIndex( mProgram, "SceneMatrices" );
        GLES30.glUniformBlockBinding( mProgram, l, 0 );//Set the to SceneMatrices the binding point 0




        return mProgram;
    }


}
