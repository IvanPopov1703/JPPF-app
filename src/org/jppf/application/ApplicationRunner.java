package org.jppf.application;

import matrix.Matrix;
import org.jppf.JPPFException;
import org.jppf.client.JPPFClient;
import org.jppf.client.JPPFJob;
import org.jppf.node.protocol.Task;

import java.util.Arrays;
import java.util.List;

/**
 * Класс в котором реализована работа с JPPF
 */
public class ApplicationRunner {

    public static void main(final String... args) {

        /*
          Вызов конструктора, при создании JPPFClient, заставляет JPPF прочитать конфигурационный файл
          и соединиться с одним или несколькими драйверами JPPF.
         */
        try (final JPPFClient jppfClient = new JPPFClient()) {

            final ApplicationRunner runner = new ApplicationRunner();

            // Выполняем блокирующее задание
            //runner.executeBlockingJob(jppfClient);

            // Выполнием не блокирующее задание
            runner.executeNonBlockingJob(jppfClient);

        } catch (final Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Метод для создания задания, которое будет отправляться на сервер
     * для выполнения
     *
     * @param jobName имя задания
     * @return экземпляр класса {@link JPPFJob}
     * @throws JPPFException исключение, если при создании или добавления заданий произошла ошибка
     */
    public JPPFJob createJob(final String jobName) throws JPPFException {
        // Создаём задание
        final JPPFJob job = new JPPFJob();
        // Устанавливаем имя задания
        job.setName(jobName);

        /*Matrix firstMatrix = new Matrix(4, 4, 1000);
        Matrix secondMatrix = new Matrix(4, 4, 1000);*/
        Matrix firstMatrix = new Matrix(1000, 1000, 100000);
        Matrix secondMatrix = new Matrix(1000, 1000, 100000);

        /*
          Создаём задачи и добавляем их в задание
          В новую задачу передаём первую матрицу и соотвествующий столбец
          из второй матрицы, в результате, каждая наша задача будет возвращать
          по столбцу из которых и будет строиться результирующая матрица
         */
        for (int i = 0; i < secondMatrix.getColCount(); i++) {
            job.add(new MultiplyMatricesTask(firstMatrix, secondMatrix.getColumnByIndex(i)));
        }

        return job;
    }

    /**
     * Метод для выполнения задания в блокирующем режиме,
     * т.е. приложение будет заблокировано до завершения выполнения задания
     *
     * @param jppfClient экземпляр {@link JPPFClient} который отправляет задание на выполненине
     * @throws Exception исключение, если при выполнении задания возникает ошибка
     */
    public void executeBlockingJob(final JPPFClient jppfClient) throws Exception {
        // Создаём задание
        final JPPFJob job = createJob("Blocking Job");

        final long startTime = System.currentTimeMillis();

        /*
          C помощью метода submit мы отправляем задание на сервер для выполнения и ждем его завершения
          Результаты возвращаются в виде списка экземпляров Task<T>,
          в том же порядке, в котором изначально они были добавлены в задание
          Но во время обработки задач, не гарантируется тот порядок обработки,
          в котором они были добавлены в задание
         */
        final List<Task<?>> results = jppfClient.submit(job);

        final long endTime = System.currentTimeMillis();
        System.out.println("Job processing time: " + (endTime - startTime));

        // Переходим к обработке результатов
        processExecutionResults(job.getName(), results);
    }

    /**
     * Метод для выполнения задачи в неблокирующем режиме,
     * т.е. приложение не заблокируется во время вызова метода обработки задания
     *
     * @param jppfClient экземпляр {@link JPPFClient} который отправляет задание на выполненине
     * @throws Exception исключение, если при выполнении задания возникает ошибка
     */
    public void executeNonBlockingJob(final JPPFClient jppfClient) throws Exception {
        // Создаём задание
        final JPPFJob job = createJob("Non Blocking Job");

        final long startTime = System.currentTimeMillis();

        /*
          Выполнение неблокирующего задания асинхронно,
          приложение не дожидается завершения обработки задания
         */
        jppfClient.submitAsync(job);

        /*
          Для получения результатов выполнения задания используем метод awaitResults(),
          этот метод сразу возвращает результаты, если задание выполнено,
          иначе он ждёт завершения выполнения задания
         */
        final List<Task<?>> results = job.awaitResults();
        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime));
        // process the results
        processExecutionResults(job.getName(), results);
    }

    /**
     * Метод для обработки результатов задания
     *
     * @param jobName имя задания, результаты которого обрабатываются
     * @param results результаты заданий после выполнения
     */
    public synchronized void processExecutionResults(final String jobName, final List<Task<?>> results) {
        //Печатаем имя задания
        System.out.println("Results for job - " + jobName);

        // Проходим по всему списку результатов
        for (final Task<?> task : results) {
            // Если при выполнении задания возникла ошибка,
            // то обрабатываем эту ситуацию, иначе обрабатываем результат
            if (task.getThrowable() != null) {
                // Обработка ошибки
                System.out.println("When performing task, an exception occurred: " + task.getThrowable().getMessage());
            } else {
                // Обработка результата
                Integer[] col = (Integer[]) task.getResult();
                System.out.println(Arrays.toString(col));
            }
        }
    }
}
