package com.gabriel.wishlist.Infra.Adapters.Redis;

import com.gabriel.wishlist.Application.Interfaces.ExternalServices.IDistributedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisLockAdapter implements IDistributedLock {

    private final RedissonClient redissonClient;

    @Autowired
    public RedisLockAdapter(RedissonClient redissonClient) {

        this.redissonClient = redissonClient;
    }


    public boolean acquireLock(String id, long waitTime, long leaseTime) {
        String lockKey = "lock:" + id;
        RLock lock = redissonClient.getLock(lockKey);

        try {
            return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    public void releaseLock(String id) {
        String lockKey = "lock:" + id;
        RLock lock = redissonClient.getLock(lockKey);

        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }
}