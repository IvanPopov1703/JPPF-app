package org.jppf.application.template;

import matrix.Matrix;
import org.jppf.node.protocol.AbstractTask;

/**
 * Класс описывающий задачу, в которой производится умножение матрицы
 * на столбец другой матрицы.
 * Задача всегда определяется как имплементация интерфейса Task<T>,
 * но в данном примере мы не имплементируем интерфейс Task<T> напрамую,
 * мы наследуемся от класса AbstractTask<T>, который, в свою очередь,
 * имплементирует все методы интерфейса Task<T>
 */
public class MultiplyMatricesTask extends AbstractTask<Integer[]> {

    private final Matrix matrix;
    private final Integer[] vector;

    private final Integer rowCount;
    private final Integer colCount;

    /**
     * Конструктор с параметрами
     * @param matrix первая матрица, которая будет умножаться на столбец
     * @param vector столбец второй матрицы
     */
    public MultiplyMatricesTask(Matrix matrix, Integer[] vector) {
        this.matrix = matrix;
        this.vector = vector;
        this.rowCount = matrix.getRowCount();
        this.colCount = matrix.getColCount();
    }

    /**
     * Метод, в котором реализовано умножение матрицы на столбец
     */
    @Override
    public void run() {
        // Результирующий вектор
        final Integer[] resultVec = new Integer[this.colCount];
        int sum;
        for (int i = 0; i < rowCount; i++) { // Умножение матрицы на столбец
            sum = 0;
            for (int j = 0; j < colCount; j++) {
                sum += matrix.getElem(i, j) * vector[j];
            }
            resultVec[i] = sum;
        }

        /**
         * Установка результата выполнения задачи, данный метод предназначен
         * для удобства хранения результатов выполнения задачи
         * для последующего извлечения
         */
        setResult(resultVec);
    }
}
