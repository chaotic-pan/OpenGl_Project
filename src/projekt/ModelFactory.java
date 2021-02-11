package projekt;

import lenz.opengl.Texture;

import java.io.IOException;

public class ModelFactory {
    private Matrix4 proMatrix;

    public Model getModel(String modelType){
        proMatrix = new Matrix4(1.0f, 10f);

        if(modelType == null){
            return null;
        }
        if(modelType.equalsIgnoreCase("cube")) {
            return cube();
        }
        else if(modelType.equalsIgnoreCase("pyramid")){
            return pyramid();
        }
        else if(modelType.equalsIgnoreCase("donut")) {
            return donut();
        }
        return null;
    }

    private Model cube() {
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

        return new TexturedModel("cube", coordinatesCube, null, null, texCube,
                                new Texture("worldTex.jpg"), proMatrix);
    }

    private Model pyramid() {
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
        return new Model("pyramid", coordinatesPyramid, colorsPyramid, null, proMatrix);
    }

    private Model donut() {
        OBJ_Loader donutObj = null;
        try {
            donutObj = new OBJ_Loader("src\\res\\donut.obj");
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert donutObj != null;

        return new TexturedModel("donut", donutObj.getVertex(), null, donutObj.getNormal(),
                donutObj.getTexture(), new Texture("moonTex.png"), proMatrix);
    }
}
