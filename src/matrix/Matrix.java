package matrix;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Класс описывающий матрицу
 */
public class Matrix implements Serializable {
    private Integer[][] matr;
    private final Integer rowCount;
    private final Integer colCount;
    private Random rand;

    /**
     * Конструктор с параметрами
     *
     * @param matr матрица в виде массива
     */
    public Matrix(Integer[][] matr) {
        this.matr = matr;
        this.rowCount = matr.length;
        this.colCount = this.rowCount;
    }

    /**
     * Конструктор с параметрами
     *
     * @param rowCount           количество строк
     * @param colCount           количество столбцов
     * @param rangeForInitMatrix верхняя граница чисел, для инициализации матрицы
     */
    public Matrix(Integer rowCount, Integer colCount, Integer rangeForInitMatrix) {
        try {
            this.rand = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            this.rand = new Random();
        }
        this.rowCount = rowCount;
        this.colCount = colCount;
        matr = new Integer[rowCount][colCount];
        initMatrix(rangeForInitMatrix);
    }

    /**
     * Метод для инициализации матрицы случайными часлами
     *
     * @param rangeForInitMatrix верхняя граница чисел
     */
    private void initMatrix(Integer rangeForInitMatrix) {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                matr[i][j] = rand.nextInt(rangeForInitMatrix);
            }
        }
    }

    /**
     * Метод для проверки корректности введённого индекса
     *
     * @param indexRow номер строки
     * @param indexCol номер столбца
     * @return если индексы заданы правильно - true, иначе false
     */
    /*private boolean checkIndex(Integer indexRow, Integer indexCol) {
        return (
                (indexRow < 0 || indexRow > rowCount - 1)
                        || (indexCol < 0 || indexCol > colCount - 1)
        );
    }*/

    private boolean checkIndex(Integer indexRow, Integer indexCol) {
        boolean ok = (indexRow >= 0 && indexRow < rowCount);
        if (ok) {
            ok = (indexCol >= 0 && indexCol < colCount);
        }
        ok = (indexRow >= 0 && indexRow < rowCount) && (indexCol >= 0 && indexCol < colCount);

        return ok;
    }

    /**
     * Метод для получения значения по индексу
     *
     * @param indexRow номер строки
     * @param indexCol номер столбца
     * @return значение, которое находится по заданному индексу
     * @throws NullPointerException если индексы заданы не правильно, выбрасывается исключение
     */
    public Integer getElem(Integer indexRow, Integer indexCol) throws NullPointerException {
        if (!checkIndex(indexRow, indexCol)) {
            throw new NullPointerException("Out of matrix boundaries");
        }
        return matr[indexRow][indexCol];
    }

    /**
     * Метод для установки значения по индексу
     *
     * @param indexRow номер строки
     * @param indexCol номер столбца
     * @param value    значение
     * @throws NullPointerException если индексы заданы не правильно, выбрасывается исключение
     */
    public void setElem(Integer indexRow, Integer indexCol, Integer value) throws NullPointerException {
        if (!checkIndex(indexRow, indexCol)) {
            throw new NullPointerException("Out of matrix boundaries");
        }
        matr[indexRow][indexCol] = value;
    }

    /**
     * Метод, который возвращает матрицу в виде массива
     *
     * @return массив с матрицей
     */
    public Integer[][] getMatrixAsArray() {
        return this.matr;
    }

    /**
     * Метод для получения количества строк в матрице
     *
     * @return количество строк
     */
    public Integer getRowCount() {
        return rowCount;
    }

    /**
     * Метод для получения количества столбцов в матрице
     *
     * @return количество стобцов
     */
    public Integer getColCount() {
        return colCount;
    }

    /**
     * Метод для получения столбца по индексу
     *
     * @param columnIndex индекс столбца
     * @return столбец матрицы
     * @throws NullPointerException если индекс задан не правильно, выбрасывается исключение
     */
    public Integer[] getColumnByIndex(Integer columnIndex) throws NullPointerException {
        if (columnIndex < 0 || columnIndex >= colCount) {
            throw new NullPointerException("Out of matrix boundaries");
        }
        Integer[] resultCol = new Integer[this.colCount];
        for (int i = 0; i < this.rowCount; i++) {
            resultCol[i] = matr[i][columnIndex];
        }

        return resultCol;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                str.append(matr[i][j]).append(" ");
            }
            str.append("\n");
        }
        return str.toString();
    }
}
