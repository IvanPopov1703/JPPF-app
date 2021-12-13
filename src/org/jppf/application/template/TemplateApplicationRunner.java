/*
 * JPPF.
 * Copyright (C) 2005-2019 JPPF Team.
 * http://www.jppf.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jppf.application.template;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import matrix.Matrix;
import matrix.MyVector;
import org.jppf.JPPFException;
import org.jppf.client.JPPFClient;
import org.jppf.client.JPPFConnectionPool;
import org.jppf.client.JPPFJob;
import org.jppf.node.protocol.Task;
import org.jppf.utils.Operator;

/**
 * This is a template JPPF application runner.
 * It is fully commented and is designed to be used as a starting point
 * to write an application using JPPF.
 * @author Laurent Cohen
 */
public class TemplateApplicationRunner {

  /**
   * The entry point for this application runner to be run from a Java command line.
   * @param args by default, we do not use the command line arguments,
   * however nothing prevents us from using them if need be.
   */
  public static void main(final String...args) {

    // create the JPPFClient. This constructor call causes JPPF to read the configuration file
    // and connect with one or multiple JPPF drivers.
    // создать JPPFClient. Этот вызов конструктора заставляет JPPF прочитать конфигурационный файл
    // и соединиться с одним или несколькими драйверами JPPF.
    try (final JPPFClient jppfClient = new JPPFClient()) {

      // create a runner instance.
      final TemplateApplicationRunner runner = new TemplateApplicationRunner();

      // create and execute a blocking job
      // создать и выполнить блокирующее задание
      runner.executeBlockingJob(jppfClient);
      //runner.executeNonBlockingJob(jppfClient);
      //runner.executeNonBlockingJob(jppfClient);

      // create and execute a non-blocking job
      //runner.executeNonBlockingJob(jppfClient);

      // create and execute 3 jobs concurrently
      //runner.executeMultipleConcurrentJobs(jppfClient, 1);

    } catch(final Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Create a JPPF job that can be submitted for execution.
   * @param jobName an arbitrary, human-readable name given to the job.
   * @return an instance of the {@link org.jppf.client.JPPFJob JPPFJob} class.
   * @throws Exception if an error occurs while creating the job or adding tasks.
   */
  /*public JPPFJob createJob(final String jobName) throws Exception {

    // В этом методе мы создаём задание и заполяем его задачами

    // создаём задание
    final JPPFJob job = new JPPFJob();

    // устанавливаем ему имя, которое можем использовать для маниторинга и управления заданием
    *//**
     * Если мы не установим имя заданию, то это будет сделано автомотически
     * в виде строки из 32 шестнадцатеричных символов
     *//*
    //job.setName(jobName);

    // add a task to the job.
    // Добавляем задачу в задание
    //final Task<?> task = job.add(new TemplateJPPFTask());

    *//**
     * job.add(Object task, Object... args)
     * первый аргумент, это объект задачи
     *
     * Необязательные аргументы используются, когда мы хотим выполнить другие формы задач,
     * которые не являются реализацией задачи.
     *//*
    //final Task<?> task = job.add(new TemplateJPPFTask());

    // provide a user-defined name for the task
    // Задаём пользовательское имя для задания
    //task.setId(jobName + " - Template task");

    // add more tasks here ...
    // Добавьте сюда другие задачи

    // there is no guarantee on the order of execution of the tasks,
    // however the results are guaranteed to be returned in the same order as the tasks.
    // порядок выполнения задач не гарантируется, однако результаты гарантированно будут
    // возвращены в том же порядке, что и задания

    for (int i = 0; i < 10; i++) {
      job.add(new TemplateJPPFTask());
    }

    job.getSLA().setSuspended(true);

    return job;
  }*/

  public JPPFJob createJob(final String jobName) throws JPPFException {
    // создаём задание
    final JPPFJob job = new JPPFJob();
    job.setName(jobName);

    /*Matrix firstMatrix = new Matrix(new Integer[][] {{2, 3, 8}, {4, 1, 5}, {7, 3, 2}});
    Matrix secondMatrix = new Matrix(new Integer[][] {{3, 4, 5}, {7, 1, 2}, {2, 4, 1}});*/
    /*Matrix firstMatrix = new Matrix(new Integer[][] {{2, 3}, {4, 1}});
    Matrix secondMatrix = new Matrix(new Integer[][] {{3, 4}, {7, 1}});*/

    Matrix firstMatrix = new Matrix(4, 4, 1000);
    Matrix secondMatrix = new Matrix(4, 4, 1000);

    for (int i = 0; i < secondMatrix.getColCount(); i++) {
      job.add(new MultiplyMatricesTask(firstMatrix, secondMatrix.getColumnByIndex(i)));
    }

    //MyVector v1 = new MyVector(new Integer[]{2, 5, 1, 3});
    //MyVector v2 = new MyVector(new Integer[]{2, 5, 1, 3});
    //MyVector v3 = new MyVector(new Integer[]{2, 5, 1, 3});
/*    Integer[] arr1 = new Integer[]{2, 5, 1, 3};
    Integer[] arr2 = new Integer[]{10, 5, 11, 7};
    Integer[] arr3 = new Integer[]{12, 56, 1, 2};*/

    /*job.add(new ArrSumTask(v1, 1));
    job.add(new ArrSumTask(v1, 0));*/
    /*for (int i = 0; i < secondMatrix.getColCount(); i++) {
      job.add(new MultiplyMatricesTask(firstMatrix, secondMatrix.getColumnByIndex(i)));
    }*/

    return job;
  }

  /**
   * Execute a job in blocking mode. The application will be blocked until the job execution is complete.
   * @param jppfClient the {@link JPPFClient} instance which submits the job for execution.
   * @throws Exception if an error occurs while executing the job.
   */

  /**
   * Выполнение задания в режиме блокировки.
   * Приложение будет заблокировано до завершения выполнения задания.
   * @param jppfClient экземпляр {@link JPPFClient}, который отправляет задание на выполнение.
   * @создает исключение, если при выполнении задания возникает ошибка.
   */
  /*public void executeBlockingJob(final JPPFClient jppfClient) throws Exception {
    // Create a job
    // Создание задания
    final JPPFJob job = createJob("Template blocking job");

    // Submit the job and wait until the results are returned.
    // The results are returned as a list of Task<?> instances,
    // in the same order as the one in which the tasks where initially added to the job.
    // Отправьте задание и дождитесь получения результатов.
    // Результаты возвращаются в виде списка экземпляров Task<?>,
    // в том же порядке, в котором задания были первоначально добавлены к заданию.

    // с помощью метода submit мы отправляем задание на сервер для выполнения и ждем его завершения
    System.out.println("Method executeBlockingJob start processing");
    final List<Task<?>> results = jppfClient.submit(job);
    System.out.println("Method executeBlockingJob end processing");
    // process the results
    // обрабатываем результаты

    processExecutionResults(job.getName(), results);
  }*/

  public void executeBlockingJob(final JPPFClient jppfClient) throws Exception {
    final JPPFJob job = createJob("Job name 1");

    // с помощью метода submit мы отправляем задание на сервер для выполнения и ждем его завершения
    final long startTime = System.currentTimeMillis();
    jppfClient.submit(job);
    final long endTime = System.currentTimeMillis();
    System.out.println("Total execution time: " + (endTime - startTime));
    final List<Task<?>> results = job.awaitResults();
    processExecutionResults(job.getName(), results);
  }

  /**
   * Execute a job in non-blocking mode. The application has the responsibility
   * for handling the notification of job completion and collecting the results.
   * @param jppfClient the {@link JPPFClient} instance which submits the job for execution.
   * @throws Exception if an error occurs while executing the job.
   */
  public void executeNonBlockingJob(final JPPFClient jppfClient) throws Exception {
    // Create a job
    final JPPFJob job = createJob("Template non-blocking job");

    // Submit the job. This call returns immediately without waiting for the execution of
    // the job to complete. As a consequence, the object returned for a non-blocking job is
    // always null. Note that we are calling the exact same method as in the blocking case.


    // the non-blocking job execution is asynchronous, we can do anything else in the meantime
    // ...
    // We are now ready to get the results of the job execution.
    // We use JPPFJob.awaitResults() for this. This method returns immediately with
    // the results if the job has completed, otherwise it waits until the job execution is complete.
    final long startTime = System.currentTimeMillis();
    jppfClient.submitAsync(job);
    final long endTime = System.currentTimeMillis();
    System.out.println("Total execution time: " + (endTime - startTime));
    final List<Task<?>> results = job.awaitResults();
    // process the results
    processExecutionResults(job.getName(), results);
  }

  /**
   * Execute multiple jobs in parallel from the same JPPFClient.
   * <p>This is an extension of the {@code executeNonBlockingJob()} method, with one additional step:
   * to ensure that a sufficient number of connections to the server are present, so that jobs can be submitted concurrently.
   * The number of connections determines the number of jobs that can be submitted in parallel.
   * It can be set in the JPPF configuration or dynamically with the {@link JPPFConnectionPool} API.
   * <p>As a result, the call to {@code executeNonBlockingJob(jppfClient)} is effectively
   * equivalent to {@code executeMultipleConccurentJobs(jppfClient, 1)}.
   * <p>There are many patterns that can be applied to parallel job execution, you are encouraged to read
   * the <a href="http://www.jppf.org/doc/v4/index.php?title=Submitting_multiple_jobs_concurrently">dedicated section</a>
   * of the JPPF documentation for details and code samples. 
   * @param jppfClient the JPPF client which submits the jobs.
   * @param numberOfJobs the number of jobs to execute.
   * @throws Exception if any error occurs.
   */
  public void executeMultipleConcurrentJobs(final JPPFClient jppfClient, int numberOfJobs) throws Exception {
    // ensure that the client connection pool has as many connections
    // as the number of jobs to execute
    //numberOfJobs = 1;
    ensureNumberOfConnections(jppfClient, numberOfJobs);

    // this list will hold all the jobs submitted for execution,
    // so we can later collect and process their results
    final List<JPPFJob> jobList = new ArrayList<>(numberOfJobs);

    final long startTime = System.currentTimeMillis();
    // create and submit all the jobs
    for (int i=1; i<=numberOfJobs; i++) {
      // create a job with a distinct name
      final JPPFJob job = createJob("Template concurrent job " + i);

      // submit the job for execution, without blocking the current thread
      jppfClient.submitAsync(job);

      // add this job to the list
      jobList.add(job);
    }
    final long endTime = System.currentTimeMillis();
    System.out.println("Total execution time: " + (endTime - startTime));

    // wait until the jobs are finished and process their results.
    for (final JPPFJob job: jobList) {
      // wait if necessary for the job to complete and collect its results
      final List<Task<?>> results = job.awaitResults();

      // process the job results
      processExecutionResults(job.getName(), results);
    }
  }

  /**
   * Ensure that the JPPF client has the desired number of connections.  
   * @param jppfClient the JPPF client which submits the jobs.
   * @param numberOfConnections the desired number of connections.
   * @throws Exception if any error occurs.
   */
  public void ensureNumberOfConnections(final JPPFClient jppfClient, final int numberOfConnections) throws Exception {
    // wait until the client has at least one connection pool with at least one avaialable connection
    final JPPFConnectionPool pool = jppfClient.awaitActiveConnectionPool();

    // if the pool doesn't have the expected number of connections, change its size
    if (pool.getConnections().size() != numberOfConnections) {
      // set the pool size to the desired number of connections
      pool.setSize(numberOfConnections);
    }

    // wait until all desired connections are available (ACTIVE status)
    pool.awaitActiveConnections(Operator.AT_LEAST, numberOfConnections);
  }

  /**
   * Process the execution results of each submitted task.
   * @param jobName the name of the job whose results are processed.
   * @param results the tasks results after execution on the grid.
   */
  public synchronized void processExecutionResults(final String jobName, final List<Task<?>> results) {
    // print a results header
    //Печатаем имя задания
    System.out.printf("Results for job '%s' :\n", jobName);
    // process the results
    // проходим по всему списку результатов
    for (final Task<?> task: results) {
      final String taskName = task.getId();
      // if the task execution resulted in an exception
      // если при выполнении задания возникла ошибка,
      // то обрабатываем это ситуацию, иначе обрабатываем результат
      if (task.getThrowable() != null) {
        // process the exception here ...
        System.out.println(taskName + ", an exception was raised: " + task.getThrowable ().getMessage());
      } else {
        Integer[] col = (Integer[]) task.getResult();
        System.out.println(Arrays.toString(col));
      }
    }
  }
}
