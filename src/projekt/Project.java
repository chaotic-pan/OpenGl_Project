package projekt;

import lenz.opengl.AbstractOpenGLBase;

import java.util.HashMap;

import static org.lwjgl.opengl.GL30.*;

public class Project extends AbstractOpenGLBase {

	private final HashMap<String, Model> modelMap = new HashMap<>();
	private float angle;

	public static void main(String[] args) {
		new Project().start("CG Projekt", 700, 700);
	}

	@Override
	protected void init() {
		ModelFactory factory = new ModelFactory();
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
		modelMap.get("pyramid").setTransMatrix(new Matrix4(orbit).scale(0.3f).translate(-7,5,0)
								.rotateZ(5*angle).rotateX(2*angle));
		modelMap.get("donut").setTransMatrix(new Matrix4(orbit).scale(0.3f).
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
