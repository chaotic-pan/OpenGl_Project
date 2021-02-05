package projekt;

import lenz.opengl.Texture;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class TexturedModel extends Model {
    Texture texturePath;

    public TexturedModel(String name, float[] coordinates, float[] colors, float[] normals, float[] texture, Texture texturePath, Matrix4 proMatrix) {
        super(name, coordinates, colors, normals, proMatrix);
        this.texturePath=texturePath;

        initVao(texture);

        int lo = glGetUniformLocation(super.getShader().getId(), "projectionsMatrix");
        glUniformMatrix4fv(lo, false, proMatrix.getValuesAsArray());
    }

    protected void initVao(float[] texture) {
        glBindVertexArray(super.getVao());
        initVbo(texture);
    }

    private void initVbo(float[] data) {
        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
        glVertexAttribPointer(3, 2,GL_FLOAT,false,0,0);
        glEnableVertexAttribArray(3);
        glBindTexture(GL_TEXTURE_2D, texturePath.getId());
    }

    public void setTexture(Texture texturePath){
        this.texturePath=texturePath;
    }

    public Texture getTexturePath(){
        return texturePath;
    }
}
