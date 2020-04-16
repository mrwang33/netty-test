import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
    for (int i = 0; i < myMission.length; ) {
      int[] ints = Arrays.copyOfRange(myMission, i, i += 50);
      Future<?> submit = executorService.submit(new MyTask(ints));
      Object o = submit.get();
      System.out.println(o);
//      executorService.execute(new MyTask(ints));
    }
  }

}
