import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

/**
 * @author wanghuan
 */
public class SingleThreadPoolTest {

  private static int missionCapacity = 5000;

  private static int[] myMission = new int[missionCapacity];

  private static int capacity = 50;

  static {
    for (int i = 0; i < missionCapacity; i++) {
      myMission[i] = i;
    }
  }

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ExecutorService executorService = Executors.newCachedThreadPool();
    new ThreadPoolExecutor(2, // 核心线程数
        10, // 最大线程数
        60L, // 空闲线程等待新任务最长时间
        TimeUnit.SECONDS, // 空闲线程等待新任务最长时间单位
        new LinkedBlockingQueue<>(2), // 存放任务的队列
        Executors.defaultThreadFactory(), // 线程工厂
        new AbortPolicy()); // 拒绝策略
    for (int i = 0; i < myMission.length; ) {
      int[] ints = Arrays.copyOfRange(myMission, i, i += 50);
      Future<?> submit = executorService.submit(new MyTask(ints));
      Object o = submit.get();
      System.out.println(o);
//      executorService.execute(new MyTask(ints));
    }
  }

}
