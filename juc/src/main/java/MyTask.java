import java.util.concurrent.Callable;

/**
 * @author wanghuan
 */
public class MyTask implements Callable<Integer> {

  private int[] mission;
  private int result = 0;

  MyTask(int[] mission) {
    this.mission = mission;
  }

/*  @Override
  public void run() {
    for (int i : mission) {
      for (; i > 1; ) {
        result += i * --i;
      }
    }
    System.out.println(result);
  }*/

  /**
   * Computes a result, or throws an exception if unable to do so.
   *
   * @return computed result
   * @throws Exception if unable to compute a result
   */
  @Override
  public Integer call() throws Exception {
    for (int i : mission) {
      for (; i > 1; ) {
        result += i * --i;
      }
    }
    return result;
  }
}
