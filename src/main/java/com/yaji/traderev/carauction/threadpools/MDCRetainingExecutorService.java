package com.yaji.traderev.carauction.threadpools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.slf4j.MDC;

public class MDCRetainingExecutorService implements ExecutorService {

  private ExecutorService executorService;

  public MDCRetainingExecutorService(ExecutorService executorService) {
    this.executorService = executorService;
  }

  public Runnable runnableWithMdcSet(Runnable runnable, final Map<String, String> mdcContext) {

    return new Runnable() {
      @Override
      public void run() {
        MDC.setContextMap(mdcContext);
        try {
          runnable.run();
        } finally {
          MDC.clear();
        }
      }
    };
  }

  public <T> Callable<T> callableWithMdcSet(
      Callable<T> callable, final Map<String, String> mdcContext) {

    return new Callable<T>() {
      @Override
      public T call() throws Exception {
        MDC.setContextMap(mdcContext);
        try {
          return callable.call();
        } finally {
          MDC.clear();
        }
      }
    };
  }

  @Override
  public void execute(Runnable command) {
    Runnable runnable = runnableWithMdcSet(command, MDC.getCopyOfContextMap());
    executorService.execute(runnable);
  }

  @Override
  public void shutdown() {
    executorService.shutdown();
  }

  @Override
  public List<Runnable> shutdownNow() {
    return executorService.shutdownNow();
  }

  @Override
  public boolean isShutdown() {
    return executorService.isShutdown();
  }

  @Override
  public boolean isTerminated() {
    return executorService.isTerminated();
  }

  @Override
  public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
    return executorService.awaitTermination(timeout, unit);
  }

  @Override
  public <T> Future<T> submit(Callable<T> task) {
    Callable<T> callable = callableWithMdcSet(task, MDC.getCopyOfContextMap());
    return executorService.submit(callable);
  }

  @Override
  public <T> Future<T> submit(Runnable task, T result) {
    Runnable runnable = runnableWithMdcSet(task, MDC.getCopyOfContextMap());
    return executorService.submit(runnable, result);
  }

  @Override
  public Future<?> submit(Runnable task) {
    Runnable runnable = runnableWithMdcSet(task, MDC.getCopyOfContextMap());
    return executorService.submit(runnable);
  }

  private <T> List<Callable<T>> setMdcForCallables(Collection<? extends Callable<T>> tasks) {
    List<Callable<T>> callables = new ArrayList<>();
    for (Callable<T> task : tasks) {
      Callable<T> callable = callableWithMdcSet(task, MDC.getCopyOfContextMap());
      callables.add(callable);
    }
    return callables;
  }

  @Override
  public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
      throws InterruptedException {
    Collection<Callable<T>> mdcSetCallables = setMdcForCallables(tasks);
    return executorService.invokeAll(mdcSetCallables);
  }

  @Override
  public <T> List<Future<T>> invokeAll(
      Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
      throws InterruptedException {
    Collection<Callable<T>> mdcSetCallables = setMdcForCallables(tasks);
    return executorService.invokeAll(mdcSetCallables, timeout, unit);
  }

  @Override
  public <T> T invokeAny(Collection<? extends Callable<T>> tasks)
      throws InterruptedException, ExecutionException {
    Collection<Callable<T>> mdcSetCallables = setMdcForCallables(tasks);
    return executorService.invokeAny(mdcSetCallables);
  }

  @Override
  public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
      throws InterruptedException, ExecutionException, TimeoutException {
    Collection<Callable<T>> mdcSetCallables = setMdcForCallables(tasks);
    return executorService.invokeAny(mdcSetCallables, timeout, unit);
  }
}
