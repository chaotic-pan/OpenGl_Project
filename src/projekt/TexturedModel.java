package projekt;

import lenz.opengl.Texture;

import static org.lwjgl.opengl.GL15.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL15.glBindTexture;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

public class TexturedModel extends Model {
    private final Texture texturePath;

    public TexturedModel(String name, float[] coordinates, float[] colors, float[] normals, float[] texture,
                         Texture texturePath, Matrix4 proMatrix) {
        super(name, coordinates, colors, normals, proMatrix);
        this.texturePath=texturePath;

        initVao(texture);

        int lo = glGetUniformLocation(super.getShader().getId(), "projectionsMatrix");
        glUniformMatrix4fv(lo, false, proMatrix.getValuesAsArray());
    }

    private void initVao(float[] texture) {
        initVbo(texture, 3, 2);
        glBindTexture(GL_TEXTURE_2D, texturePath.getId());
    }

    public void render() {
        glBindTexture(GL_TEXTURE_2D, texturePath.getId());
        super.render();

    }
}
