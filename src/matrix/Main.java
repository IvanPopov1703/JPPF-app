package matrix;

import java.util.Arrays;

public class Main {

    public static Integer[] mult(Matrix matrix, Integer[] vector) {
        final Integer[] resultVec = new Integer[matrix.getColCount()];
        final Integer rowCount = matrix.getRowCount();
        final Integer colCount = matrix.getColCount();
        int sum;
        for (int i = 0; i < rowCount; i++) { // Умножение матрицы на столбец
            sum = 0;
            for (int j = 0; j < colCount; j++) {
                sum += matrix.getElem(i, j) * vector[j];
            }
            resultVec[i] = sum;
        }
        return resultVec;
    }

    public static void main(String[] args) {
        Integer[] res = new Integer[0];

        Integer[][] m1 = new Integer[][] {{2, 3, 8}, {4, 1, 5}, {7, 3, 2}};
        Integer[][] m2 = new Integer[][] {{3, 4, 5}, {7, 1, 2}, {2, 4, 1}};

        Matrix firstMatrix = new Matrix(1000, 1000, 100000);
        Matrix secondMatrix = new Matrix(1000, 1000, 100000);

        final long startTime = System.currentTimeMillis();
        for (int i = 0; i < secondMatrix.getColCount(); i++) {
             res = mult(firstMatrix, secondMatrix.getColumnByIndex(i));
        }
        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime));

        System.out.println(Arrays.toString(res));
    }
}
