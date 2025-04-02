package dev.hudsonprojects.backend;


import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Experiments {

    private static final Map<String, String> map = new HashMap<>();

    public static void main(String[] args) {


        Queue<Integer> numbers = new ConcurrentLinkedQueue<>();

        IntStream.range(1,10).forEach(numbers::add);

        for(var number : numbers){
            if (number % 2 ==0) {
                numbers.remove(number);
            }
        }
        System.out.println(numbers);
    }

}

class TestSync {
    private final Map<String, String> map = new ConcurrentHashMap<>();
    private final Lock lock = new ReentrantLock();

    public void put(String k, String v) {
        lock.lock();
        System.out.println("Lock obtido put");
        try {
            Thread.sleep(1000);
            map.put(k, v);
        } catch (InterruptedException e) {
        }finally {
            System.out.println("Lock liberado put");
            lock.unlock();

        }

    }

    public String get(String k) {
        lock.lock();
        System.out.println("Lock obtido get");
        try {
            Thread.sleep(5000);
            return map.get(k);
        } catch (InterruptedException e) {
            return null;
        } finally {
            System.out.println("Lock liberado get");
            lock.unlock();
        }
    }
}
