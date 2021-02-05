package projekt;

import static org.lwjgl.opengl.GL30.*;

import lenz.opengl.AbstractOpenGLBase;
import lenz.opengl.ShaderProgram;
import lenz.opengl.Texture;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Project extends AbstractOpenGLBase {

	Model pyramid;
	TexturedModel cube;
	TexturedModel donut;
	float angle;

	public static void main(String[] args) {
		new Project().start("CG Projekt", 700, 700);
	}

	@Override
	protected void init() {
		Matrix4 proMatrix = new Matrix4(1.0f, 10f);


		float[] coordinatesCube = new float[]{
				1, 1, 1, 		-1, 1, 1, 		-1, -1, 1,
				1, 1, 1, 		-1, -1, 1, 		1, -1, 1,

				-1, 1, 1,	 	-1, 1, -1, 		-1, -1, -1,
				-1, 1, 1, 		-1, -1, -1, 	-1, -1, 1,

				1, 1, -1, 		1, -1, -1, 		-1, -1, -1,
				1, 1, -1, 		-1, -1, -1, 	-1, 1, -1,

				1, 1, -1, 		1, 1, 1, 		1, -1, 1,
				1, 1, -1, 		1, -1, 1, 		1, -1, -1,

				1, 1, -1, 		-1, 1, -1, 		-1, 1, 1,
				1, 1, -1, 		-1, 1, 1, 		1, 1, 1,

				1, -1, -1, 		1, -1, 1, 		-1, -1, 1,
				1, -1, -1, 		-1, -1, 1, 		-1, -1, -1,
		};
		float[] texCube = new float[] {
				0.5f, 0, 		 	0.25f, 0,  			0.25f, 0.33f,
				0.5f, 0,  			0.25f, 0.33f,		0.5f, 0.33f,

				0, 0.34f, 			0, 0.66f,  			0.25f, 0.66f,
				0, 0.34f, 			0.25f, 0.67f, 		0.25f, 0.34f,

				0.499f, 1,			0.499f, 0.66f,		0.251f, 0.66f,
				0.499f, 1,	 		0.251f, 0.66f, 		0.251f, 1,

				0.75f, 0.663f,	 	0.75f, 0.334f,		0.5f, 0.334f,
				0.75f, 0.663f,		0.5f, 0.334f,	 	0.5f, 0.662f,

				0.75f, 0.663f,	 	1, 0.663f,			1, 0.334f,
				0.75f, 0.663f,		1, 0.334f,	 		0.75f, 0.334f,

				0.5f, 0.663f,	 	0.5f, 0.334f,		0.25f, 0.334f,
				0.5f, 0.663f,		0.25f, 0.334f,	 	0.25f, 0.662f,
		};

		cube = new TexturedModel("cube", coordinatesCube, null, null, texCube,
									new Texture("worldTex.jpg"), proMatrix);



		float[] coordinatesPyramid = new float[]{
				1f, -1.2f, -1f, 		1f, -1.2f, 1f, 			-1f, -1.2f, 1f,
				1f, -1.2f, -1f, 		-1f, -1.2f, 1f, 		-1f, -1.2f, -1f,

				0f, 1.2f, 0f, 			-1f, -1.2f, 1f, 		1f, -1.2f, 1f,
				0f, 1.2f, 0f, 			1f, -1.2f, 1f, 			1f, -1.2f, -1f,

				0f, 1.2f, 0f, 			1f, -1.2f, -1f, 		-1f, -1.2f, -1f,
				0f, 1.2f, 0f, 			-1f, -1.2f, -1f, 		-1f, -1.2f, 1f,
		};
		float[] colorsPyramid = new float[]{
				0.7f, 0.2f, 0.2f, 		0.7f, 0.2f, 0.2f, 		0.7f, 0.2f, 0.2f,
				0.7f, 0.2f, 0.2f, 		0.7f, 0.2f, 0.2f, 		0.7f, 0.2f, 0.2f,
				0.2f, 0.7f, 0.2f, 		0.2f, 0.7f, 0.2f, 		0.2f, 0.7f, 0.2f,
				0.2f, 0.2f, 0.7f, 		0.2f, 0.2f, 0.7f, 		0.2f, 0.2f, 0.7f,
				0.7f, 0.7f, 0.2f, 		0.7f, 0.7f, 0.2f, 		0.7f, 0.7f, 0.2f,
				0.7f, 0.2f, 0.7f, 		0.7f, 0.2f, 0.7f, 		0.7f, 0.2f, 0.7f,
		};

		pyramid = new Model("pyramid", coordinatesPyramid, colorsPyramid, null, proMatrix);


		OBJ_Loader donutObj = null;
		try {
			donutObj = new OBJ_Loader("src\\res\\donut.obj");
		} catch (IOException e) {
			e.printStackTrace();
		}

		assert donutObj != null;
		donut = new TexturedModel("donut", donutObj.getVertex(), null, donutObj.getNormal(),
									donutObj.getTexture(), new Texture("moonTex.png"), proMatrix);

		glEnable(GL_DEPTH_TEST); // z-Buffer aktivieren
		glEnable(GL_CULL_FACE); // backface culling aktivieren
	}

	@Override
	public void update() {
		angle+= 0.01f;

		Matrix4 transMatrixCube = new Matrix4();
		transMatrixCube.scale(0.5f).translate(0,0,-2.5f).rotateX(angle/2).rotateY(angle/2).rotateZ(angle/2);
		cube.setTransMatrix(transMatrixCube);

		Matrix4 orbit = new Matrix4();
		orbit.translate(0,0,-2.5f).rotateZ(2*angle);

		Matrix4 transMatrixPyramid = new Matrix4();
		transMatrixPyramid.multiply(orbit);
		transMatrixPyramid.scale(0.3f).translate(-7,5,0).rotateZ(5*angle).rotateX(2*angle);
		pyramid.setTransMatrix(transMatrixPyramid);

		Matrix4 transMatrixDonut = new Matrix4();
		transMatrixDonut.multiply(orbit);
		transMatrixDonut.scale(0.3f).translate(9,0,0).rotateZ(3*angle).rotateX(angle);
		donut.setTransMatrix(transMatrixDonut);
	}

	@Override
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		glUseProgram(cube.getShader().getId());
		float[] matrixVal = cube.getTransMatrix().getValuesAsArray();
		int loc = glGetUniformLocation(cube.getShader().getId(), "posJ");
		glUniformMatrix4fv(loc, false, matrixVal);

		glBindVertexArray(cube.getVao());
		glBindTexture(GL_TEXTURE_2D, cube.getTexturePath().getId());
		glDrawArrays(GL_TRIANGLES, 0, cube.getNumCorners());
		glDrawArrays(GL_COLOR, 0, cube.getNumCorners());



		glUseProgram(pyramid.getShader().getId());
		matrixVal = pyramid.getTransMatrix().getValuesAsArray();
		loc = glGetUniformLocation(pyramid.getShader().getId(), "posJ");
		glUniformMatrix4fv(loc, false, matrixVal);

		glBindVertexArray(pyramid.getVao());
		glDrawArrays(GL_TRIANGLES, 0, pyramid.getNumCorners());
		glDrawArrays(GL_COLOR, 0, pyramid.getNumCorners());


		glUseProgram(donut.getShader().getId());
		matrixVal = donut.getTransMatrix().getValuesAsArray();
		loc = glGetUniformLocation(donut.getShader().getId(), "posJ");
		glUniformMatrix4fv(loc, false, matrixVal);

		glBindVertexArray(donut.getVao());
		glBindTexture(GL_TEXTURE_2D, donut.getTexturePath().getId());
		glDrawArrays(GL_TRIANGLES, 0, donut.getNumCorners());
		glDrawArrays(GL_COLOR, 0, donut.getNumCorners());
	}

	@Override
	public void destroy() {
	}
}
