package matrix;

import java.util.Arrays;

public class Main {

    public static Integer[] mult(Matrix matrix, Integer[] vector, Integer rowCount, Integer colCount) {
        final Integer[] resultVec = new Integer[] {0, 0, 0};

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                resultVec[i] += matrix.getElem(i, j) * vector[j];
            }
        }

        return resultVec;
    }

    public static void main(String[] args) {
        /*Matrix m1 = new Matrix(3, 3);
        Matrix m2 = new Matrix(3, 3);*/

        Integer[][] matr = new Integer[][] {{2, 3, 8}, {4, 1, 5}, {7, 3, 2}};
        Integer[] vec = new Integer[]{3, 7, 2};
        Matrix mt = new Matrix(matr);
    }
}
