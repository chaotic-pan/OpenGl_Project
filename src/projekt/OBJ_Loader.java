package projekt;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class OBJ_Loader {
    private final float[] vertexArray;
    private final float[] textureArray;
    private final float[] normalArray;

    public OBJ_Loader(String filePath) throws IOException {
        ArrayList<Float> vertexList = new ArrayList<>();
        ArrayList<Float> textureList = new ArrayList<>();
        ArrayList<Float> normalList = new ArrayList<>();
        ArrayList<String> faces = new ArrayList<>();

        File myObj = new File(filePath);
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            String[] currentLine = data.split(" ");
            if (data.startsWith("v ")) {
                for (int i = 1; i <= 3; i++)
                    vertexList.add(Float.parseFloat(currentLine[i]));
            }
            else if (data.startsWith("vt ")) {
                for (int i = 1; i <= 2; i++)
                    textureList.add(Float.parseFloat(currentLine[i]));
            }
            else if (data.startsWith("vn ")) {
                for (int i = 1; i <= 3; i++)
                    normalList.add(Float.parseFloat(currentLine[i]));
            }
            else if (data.startsWith("f ")) {
                faces.addAll(Arrays.asList(currentLine).subList(1, 4));
            }
        }

        vertexArray = sort(faces, vertexList,3, 0);
        textureArray = sort(faces, textureList,2, 1);
        normalArray = sort(faces, normalList,3, 2);
    }

    private float[] sort(ArrayList<String> faces, ArrayList<Float> dataList, int dimensions, int place) {
        float[] arr = new float[faces.size()*dimensions];
        for (int i=0; i<faces.size(); i++) {
            String[] numberStrs = faces.get(i).split("/");
            int[] currentLine = new int[numberStrs.length];
            for(int j=0; j<numberStrs.length; j++)
                currentLine[j] = Integer.parseInt(numberStrs[j]);

            for (int j=0; j<dimensions; j++) {
                arr[dimensions*i+j]= dataList.get(dimensions*(currentLine[place]-1)+j);
            }
        }
        return arr;
    }

    public float[] getVertex(){
        return vertexArray;
    }

    public float[] getTexture(){
        return textureArray;
    }

    public float[] getNormal(){
        return normalArray;
    }
}
