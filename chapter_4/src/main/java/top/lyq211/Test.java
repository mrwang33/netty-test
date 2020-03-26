package top.lyq211;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wanghuan
 */
public class Test {

  public static void main(String[] args) {
    ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    Condition condition1 = lock.newCondition();
    new Thread1(lock, condition, condition1).start();
    new Thread2(lock, condition1, condition).start();
  }
}
