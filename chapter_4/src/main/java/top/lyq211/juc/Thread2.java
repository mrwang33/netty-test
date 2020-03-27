package top.lyq211.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wanghuan
 */
public class Thread2 extends Thread {

  private Condition condition;
  private Condition otherCondition;
  private ReentrantLock lock;

  public Thread2(ReentrantLock lock, Condition condition, Condition otherCondition) {
    this.condition = condition;
    this.otherCondition = otherCondition;
    this.lock = lock;
  }

  @Override
  public void run() {
    for (int i = 1; i < 100; i++) {
      lock.lock();
      System.out.println(i);
      try {
        otherCondition.signalAll();
        condition.await();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
