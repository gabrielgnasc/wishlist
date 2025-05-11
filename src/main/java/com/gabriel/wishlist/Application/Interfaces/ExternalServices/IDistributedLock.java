package com.gabriel.wishlist.Application.Interfaces.ExternalServices;

public interface IDistributedLock {
    boolean acquireLock(String id, long waitTime, long leaseTime);
    void releaseLock(String id);
}
