package projekt;

//Alle Operationen ändern das Matrixobjekt selbst und geben das eigene Matrixobjekt zurück
//Dadurch kann man Aufrufe verketten, z.B.
//Matrix4 m = new Matrix4().scale(5).translate(0,1,0).rotateX(0.5f);
public class Matrix4 {
	private float[][] mat4;

	public Matrix4() {
		this.mat4= new float[][]{{1, 0, 0, 0},
								 {0, 1, 0, 0},
								 {0, 0, 1, 0},
								 {0, 0, 0, 1}};
	}

	public Matrix4(Matrix4 copy) {
		this.mat4= new float[4][4];
		for (int i=0; i<4; i++) {
			System.arraycopy(copy.mat4[i], 0, this.mat4[i], 0, 4);
		}
	}

	public Matrix4(float near, float far) {
		this.mat4= new float[][]{	{near, 0, 0, 0},
						 			{0, near, 0, 0},
									{0, 0, (-far-near)/(far-near), (-2*far*near)/(far-near)},
									{0, 0, 0, 1} };

	}

	public Matrix4 multiply(Matrix4 other) {
		float[][] mat4 = new float[4][4];

		for(int i=0; i<4; i++) {
			for (int j=0; j<4; j++) {
				for (int k=0; k<4; k++) {
					mat4[i][j] += other.mat4[i][k] * this.mat4[k][j];
				}
			}
		}

		this.mat4=mat4;
		return this;
	}

	public Matrix4 translate(float x, float y, float z) {
		Matrix4 product = new Matrix4();
		product.mat4 = new float[][] {  	{1, 0, 0, 0},
											{0, 1, 0, 0},
											{0, 0, 1, 0},
											{x, y, z, 1} };


		return multiply(product);
	}

	public Matrix4 scale(float uniformFactor) {
		Matrix4 scaleM = new Matrix4();
		scaleM.mat4 = new float[][] {   {uniformFactor, 0, 0, 0},
										{0, uniformFactor, 0, 0},
										{0, 0, uniformFactor, 0},
										{0, 0, 0, 1} };


		return multiply(scaleM);
	}

	public Matrix4 scale(float sx, float sy, float sz) {
		Matrix4 scaleM = new Matrix4();
		scaleM.mat4 = new float[][] { {sx, 0, 0, 0},
									  {0, sy, 0, 0},
									  {0, 0, sz, 0},
									  {0, 0, 0, 1} };

		return multiply(scaleM);
	}

	public Matrix4 rotateX(float angle) {
		Matrix4 xM = new Matrix4();
		xM.mat4 = new float[][] { 	{1, 0, 0, 0},
									{0, (float) Math.cos(angle), (float) Math.sin(angle), 0},
									{0, (float) -(Math.sin(angle)), (float) Math.cos(angle), 0},
									{0, 0, 0, 1} };

		return multiply(xM);
	}

	public Matrix4 rotateY(float angle) {
		Matrix4 yM = new Matrix4();
		yM.mat4 = new float[][] { 	{(float) Math.cos(angle), 0, (float) -(Math.sin(angle)), 0},
									{0, 1, 0, 0},
									{(float) Math.sin(angle), 0, (float) Math.cos(angle), 0},
									{0, 0, 0, 1} };

		return multiply(yM);
	}

	// Cheese, PLEASE CHILL YOUR CHILI!!!!!!!!!!!!!

	public Matrix4 rotateZ(float angle) {
		Matrix4 zM = new Matrix4();
		zM.mat4 = new float[][] { 	{(float) Math.cos(angle), (float) Math.sin(angle), 0, 0},
									{(float) -(Math.sin(angle)), (float) Math.cos(angle), 0, 0},
									{0, 0, 1, 0},
									{0, 0, 0, 1} };


		return multiply(zM);
	}

	public float[] getValuesAsArray() {
		float[] values = new float[16];

		for (int i=0; i<4; i++) {
			System.arraycopy(this.mat4[i], 0, values, i * 4, 4);
		}

		return values;
	}
}
