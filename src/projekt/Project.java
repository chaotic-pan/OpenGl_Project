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

	private ShaderProgram shaderProgramCube;
	private ShaderProgram shaderProgramPyramid;
	private ShaderProgram shaderProgramDonut;

	public static void main(String[] args) {
		new Project().start("CG Projekt", 700, 700);
	}

	int vaoCube;
	int vaoPyramid;
	int vaoDonut;
	int numCornersCube;
	int numCornersPyramid;
	int numCornersDonut;
	float angle;
	Matrix4 transMatrixCube;
	Matrix4 transMatrixPyramid;
	Matrix4 transMatrixDonut;
	Texture textPathCube;
	Texture textPathDonut;

	@Override
	protected void init() {
		Matrix4 proMatrix = new Matrix4(1.0f, 10f);

		shaderProgramCube = new ShaderProgram("cube");
		glUseProgram(shaderProgramCube.getId());
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
		float[] colorsCube = new float[coordinatesCube.length];
		float[] normalsCube = new float[] {
				0, 0, 1,		0, 0, 1,		0, 0, 1,
				0, 0, 1,		0, 0, 1,		0, 0, 1,

				-1, 0, 0,		-1, 0, 0,		-1, 0, 0,
				-1, 0, 0,		-1, 0, 0,		-1, 0, 0,

				0, 0, -1,		0, 0, -1,		0, 0, -1,
				0, 0, -1,		0, 0, -1,		0, 0, -1,

				1, 0, 0,		1, 0, 0,		1, 0, 0,
				1, 0, 0,		1, 0, 0,		1, 0, 0,

				0, 1, 0,		0, 1, 0,		0, 1, 0,
				0, 1, 0,		0, 1, 0,		0, 1, 0,

				0, -1, 0,		0, -1, 0,		0, -1, 0,
				0, -1, 0,		0, -1, 0,		0, -1, 0,
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
		textPathCube = new Texture("worldTex.jpg");
		numCornersCube = (coordinatesCube.length) / 3;

		vaoCube = glGenVertexArrays();
		glBindVertexArray(vaoCube);

		int vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, coordinatesCube, GL_STATIC_DRAW);
		glVertexAttribPointer(0,3,GL_FLOAT,false,0,0);
		glEnableVertexAttribArray(0);

		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, colorsCube, GL_STATIC_DRAW);
		glVertexAttribPointer(1,3,GL_FLOAT,false,0,0);
		glEnableVertexAttribArray(1);

		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, normalsCube, GL_STATIC_DRAW);
		glVertexAttribPointer(2,3,GL_FLOAT,false,0,0);
		glEnableVertexAttribArray(2);


		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, texCube, GL_STATIC_DRAW);
		glVertexAttribPointer(3,2,GL_FLOAT,false,0,0);
		glEnableVertexAttribArray(3);
		glBindTexture(GL_TEXTURE_2D, textPathCube.getId());

		int lo = glGetUniformLocation(shaderProgramCube.getId(), "projectionsMatrix");
		glUniformMatrix4fv(lo, false, proMatrix.getValuesAsArray());


		shaderProgramPyramid = new ShaderProgram("pyramid");
		glUseProgram(shaderProgramPyramid.getId());
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
		float[] normalsPyramid = Normals.calcNormals(coordinatesPyramid);

		numCornersPyramid = (coordinatesPyramid.length) / 3;
		vaoPyramid = glGenVertexArrays();
		glBindVertexArray(vaoPyramid);

		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, coordinatesPyramid, GL_STATIC_DRAW);
		glVertexAttribPointer(0,3,GL_FLOAT,false,0,0);
		glEnableVertexAttribArray(0);

		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, colorsPyramid, GL_STATIC_DRAW);
		glVertexAttribPointer(1,3,GL_FLOAT,false,0,0);
		glEnableVertexAttribArray(1);

		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, normalsPyramid, GL_STATIC_DRAW);
		glVertexAttribPointer(2,3,GL_FLOAT,false,0,0);
		glEnableVertexAttribArray(2);
		lo = glGetUniformLocation(shaderProgramPyramid.getId(), "projectionsMatrix");
		glUniformMatrix4fv(lo, false, proMatrix.getValuesAsArray());


		shaderProgramDonut = new ShaderProgram("donut");
		glUseProgram(shaderProgramDonut.getId());

		OBJ_Loader donutObj = null;
		try {
			donutObj = new OBJ_Loader("src\\res\\donut.obj");
		} catch (IOException e) {
			e.printStackTrace();
		}

		assert donutObj != null;
		float[] coordinatesDonut = donutObj.getVertex();
		float[] colorsDonut = new float[coordinatesDonut.length];
		float[] normalsDonut = donutObj.getNormal();
		float[] texDonut = donutObj.getTexture();

		textPathDonut = new Texture("moonTex.png");
		numCornersDonut = (coordinatesDonut.length) / 3;
		vaoDonut = glGenVertexArrays();
		glBindVertexArray(vaoDonut);

		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, coordinatesDonut, GL_STATIC_DRAW);
		glVertexAttribPointer(0,3,GL_FLOAT,false,0,0);
		glEnableVertexAttribArray(0);

		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, colorsDonut, GL_STATIC_DRAW);
		glVertexAttribPointer(1,3,GL_FLOAT,false,0,0);
		glEnableVertexAttribArray(1);

		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, normalsDonut, GL_STATIC_DRAW);
		glVertexAttribPointer(2,3,GL_FLOAT,false,0,0);
		glEnableVertexAttribArray(2);

		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, texDonut, GL_STATIC_DRAW);
		glVertexAttribPointer(3,2,GL_FLOAT,false,0,0);
		glEnableVertexAttribArray(3);
		glBindTexture(GL_TEXTURE_2D, textPathDonut.getId());
		lo = glGetUniformLocation(shaderProgramDonut.getId(), "projectionsMatrix");
		glUniformMatrix4fv(lo, false, proMatrix.getValuesAsArray());

		glEnable(GL_DEPTH_TEST); // z-Buffer aktivieren
		glEnable(GL_CULL_FACE); // backface culling aktivieren
	}

	@Override
	public void update() {
		angle+= 0.01f;

		transMatrixCube = new Matrix4();
		transMatrixCube.scale(0.5f).translate(0,0,-2.5f).rotateX(angle/2).rotateY(angle/2).rotateZ(angle/2);

		Matrix4 orbit = new Matrix4();
		orbit.translate(0,0,-2.5f).rotateZ(2*angle);

		transMatrixPyramid = new Matrix4();
		transMatrixPyramid.multiply(orbit);
		transMatrixPyramid.scale(0.3f).translate(-7,5,0).rotateZ(5*angle).rotateX(2*angle);

		transMatrixDonut = new Matrix4();
		transMatrixDonut.multiply(orbit);
		transMatrixDonut.scale(0.3f).translate(9,0,0).rotateZ(3*angle).rotateX(angle);
	}

	@Override
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		glUseProgram(shaderProgramCube.getId());
		float[] matrixVal = transMatrixCube.getValuesAsArray();
		int loc = glGetUniformLocation(shaderProgramCube.getId(), "posJ");
		glUniformMatrix4fv(loc, false, matrixVal);

		glBindVertexArray(vaoCube);
		glBindTexture(GL_TEXTURE_2D, textPathCube.getId());
		glDrawArrays(GL_TRIANGLES, 0, numCornersCube);
		glDrawArrays(GL_COLOR, 0, numCornersCube);


		glUseProgram(shaderProgramPyramid.getId());
		matrixVal = transMatrixPyramid.getValuesAsArray();
		loc = glGetUniformLocation(shaderProgramPyramid.getId(), "posJ");
		glUniformMatrix4fv(loc, false, matrixVal);

		glBindVertexArray(vaoPyramid);
		glDrawArrays(GL_TRIANGLES, 0, numCornersPyramid);
		glDrawArrays(GL_COLOR, 0, numCornersPyramid);


		glUseProgram(shaderProgramDonut.getId());
		matrixVal = transMatrixDonut.getValuesAsArray();
		loc = glGetUniformLocation(shaderProgramDonut.getId(), "posJ");
		glUniformMatrix4fv(loc, false, matrixVal);

		glBindVertexArray(vaoDonut);
		glBindTexture(GL_TEXTURE_2D, textPathDonut.getId());
		glDrawArrays(GL_TRIANGLES, 0, numCornersDonut);
		glDrawArrays(GL_COLOR, 0, numCornersDonut);
	}

	@Override
	public void destroy() {
	}
}
