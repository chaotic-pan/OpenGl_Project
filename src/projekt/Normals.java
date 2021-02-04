package projekt;

public class Normals {

    public static float[] calcNormals(float[] coordinates) {
        float[] normals = new float[coordinates.length];
        for (int i=0; i<coordinates.length; i+=9) {

            float[] a = new float[]{coordinates[i], coordinates[i+1], coordinates[i+2]};
            float[] b = new float[]{coordinates[i+3], coordinates[i+4], coordinates[i+5]};
            float[] c = new float[]{coordinates[i+6], coordinates[i+7], coordinates[i+8]};

            float[] n= crossMultiply(calcDirectionVector(a,b), calcDirectionVector(a,c));
            normalize(n);

            for (int j = 0; j < 9; j++) {
                normals[i + j] = n[j%3];
            }
        }
        return normals;
    }

    private static float[] calcDirectionVector(float[] a, float[] b) {
        return new float[]{b[0]-a[0], b[1]-a[1], b[2]-a[2]};
    }

    private static float[] crossMultiply(float[]x, float[]y){
        float[]normals= new float[3];

        normals[0] = (x[1] * y[2]) - (x[2] * y[1]);
        normals[1] = (x[2] * y[0]) - (x[0] * y[2]);
        normals[2] = (x[0] * y[1]) - (x[1] * y[0]);

        return normals;
    }

    public static void normalize(float[] coordinates) {
        for (int i = 0; i < coordinates.length; i += 3) {
            float l = (float) Math.sqrt(Math.pow(coordinates[i], 2) + Math.pow(coordinates[i + 1], 2) + Math.pow(coordinates[i + 2], 2));
            for (int j = 0; j < 3; j++)
                coordinates[j] /= l;
        }
    }
}