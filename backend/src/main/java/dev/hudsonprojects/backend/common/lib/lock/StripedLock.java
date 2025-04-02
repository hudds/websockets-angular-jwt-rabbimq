package dev.hudsonprojects.backend.common.lib.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StripedLock {

    private final List<Lock> locks;

    public StripedLock(int stripes) {
        if(stripes <= 0){
            throw new IllegalArgumentException("The number of stripes must me positive");
        }
        locks = new ArrayList<>(stripes);
        for(int i = 0; i < stripes; i++){
            locks.add(new ReentrantLock());
        }
    }

    public Lock getLock(Object key) {
        return locks.get(getId(key));
    }

    private int getId(Object key) {
        int hashCode = key.hashCode();
        return Math.abs(hashCode) % locks.size();
    }
}
