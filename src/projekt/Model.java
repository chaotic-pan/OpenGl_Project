package projekt;

import lenz.opengl.ShaderProgram;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Model {
    private final ShaderProgram shader;
    private int vao;
    private final int numCorners;
    private Matrix4 transMatrix;

    public Model(String name, float[] coordinates, float[] colors, float[] normals, Matrix4 proMatrix){
        shader = new ShaderProgram(name);
        glUseProgram(shader.getId());

        if (colors==null) colors = new float[coordinates.length];
        if (normals==null) normals = Normals.calcNormals(coordinates);

        numCorners = (coordinates.length) / 3;

        initVao(coordinates, colors, normals);

        int lo = glGetUniformLocation(shader.getId(), "projectionsMatrix");
        glUniformMatrix4fv(lo, false, proMatrix.getValuesAsArray());
    }

    private void initVao(float[] coordinates, float[] colors, float[] normals) {
        vao = glGenVertexArrays();
        glBindVertexArray(vao);
        initVbo(coordinates, 0);
        initVbo(colors, 1);
        initVbo(normals, 2);
    }

    private void initVbo(float[] data, int index) {
        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
        glVertexAttribPointer(index, 3,GL_FLOAT,false,0,0);
        glEnableVertexAttribArray(index);
    }

    public ShaderProgram getShader(){
        return shader;
    }

    public int getVao(){
        return vao;
    }

    public int getNumCorners(){
        return numCorners;
    }

    public void setTransMatrix(Matrix4 transMatrix){
        this.transMatrix=transMatrix;
    }

    public Matrix4 getTransMatrix(){
        return transMatrix;
    }
}
