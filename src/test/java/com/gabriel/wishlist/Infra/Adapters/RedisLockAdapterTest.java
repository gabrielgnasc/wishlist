package com.gabriel.wishlist.Infra.Adapters;

import com.gabriel.wishlist.Infra.Adapters.Redis.RedisLockAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class RedisLockAdapterTest {


    @Mock
    private RedissonClient redissonClient;

    @Mock
    private RLock rLock;

    @InjectMocks
    private RedisLockAdapter redisLockAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void testAcquireLock_Success() throws InterruptedException {
        String lockKey = "lock:test";
        when(redissonClient.getLock(lockKey)).thenReturn(rLock);
        when(rLock.tryLock(10, 20, TimeUnit.SECONDS)).thenReturn(true);

        boolean result = redisLockAdapter.acquireLock("test", 10, 20);

        assertTrue(result);
        verify(rLock, times(1)).tryLock(10, 20, TimeUnit.SECONDS);
    }

    @Test
    void testAcquireLock_Failure() throws InterruptedException {
        String lockKey = "lock:test";
        when(redissonClient.getLock(lockKey)).thenReturn(rLock);
        when(rLock.tryLock(10, 20, TimeUnit.SECONDS)).thenReturn(false);

        boolean result = redisLockAdapter.acquireLock("test", 10, 20);

        assertFalse(result);
        verify(rLock, times(1)).tryLock(10, 20, TimeUnit.SECONDS);
    }

    @Test
    void testAcquireLock_InterruptedException() throws InterruptedException {
        String lockKey = "lock:test";
        when(redissonClient.getLock(lockKey)).thenReturn(rLock);
        when(rLock.tryLock(10, 20, TimeUnit.SECONDS)).thenThrow(new InterruptedException());

        boolean result = redisLockAdapter.acquireLock("test", 10, 20);

        assertFalse(result);
        verify(rLock, times(1)).tryLock(10, 20, TimeUnit.SECONDS);
    }

    @Test
    void testReleaseLock_LockHeldByCurrentThread() {
        String lockKey = "lock:test";
        when(redissonClient.getLock(lockKey)).thenReturn(rLock);
        when(rLock.isHeldByCurrentThread()).thenReturn(true);

        redisLockAdapter.releaseLock("test");

        verify(rLock, times(1)).unlock();
    }

    @Test
    void testReleaseLock_LockNotHeldByCurrentThread() {
        String lockKey = "lock:test";
        when(redissonClient.getLock(lockKey)).thenReturn(rLock);
        when(rLock.isHeldByCurrentThread()).thenReturn(false);

        redisLockAdapter.releaseLock("test");

        verify(rLock, never()).unlock();
    }
}
