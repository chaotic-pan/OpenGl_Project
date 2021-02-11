package projekt;

import static org.lwjgl.opengl.GL30.*;

import lenz.opengl.AbstractOpenGLBase;
import lenz.opengl.ShaderProgram;
import lenz.opengl.Texture;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Project extends AbstractOpenGLBase {

	ModelFactory factory = new ModelFactory();
	HashMap<String, Model> modelMap = new HashMap<>();
	float angle;

	public static void main(String[] args) {
		new Project().start("CG Projekt", 700, 700);
	}

	@Override
	protected void init() {
		modelMap.put("pyramid",factory.getModel("pyramid"));
		modelMap.put("cube", factory.getModel("cube"));
		modelMap.put("donut", factory.getModel("donut"));

		glEnable(GL_DEPTH_TEST); // z-Buffer aktivieren
		glEnable(GL_CULL_FACE); // backface culling aktivieren
	}

	@Override
	public void update() {
		angle+= 0.01f;

		Matrix4 orbit = new Matrix4().translate(0,0,-2.5f).rotateZ(2*angle);

		modelMap.get("cube").setTransMatrix(new Matrix4().scale(0.5f).translate(0,0,-2.5f)
								.rotateX(angle/2).rotateY(angle/2).rotateZ(angle/2));
		modelMap.get("pyramid").setTransMatrix(new Matrix4().multiply(orbit).scale(0.3f).translate(-7,5,0)
								.rotateZ(5*angle).rotateX(2*angle));
		modelMap.get("donut").setTransMatrix(new Matrix4().multiply(orbit).scale(0.3f).
								translate(9,0,0).rotateZ(3*angle).rotateX(angle));
	}

	@Override
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		for (Model value : modelMap.values()) {
			value.render();
		}
	}

	@Override
	public void destroy() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
}
