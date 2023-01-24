package com.example.stock.facade;

import com.example.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedissonLockStockFacade {
    private final RedissonClient redissonClient;

    private final StockService stockService;

    public void decrease(Long key, Long quantity) {
        var lock = redissonClient.getLock(key.toString());

        try {
            var available = lock.tryLock(5, 1, TimeUnit.SECONDS);

            if (!available) {
                return;
            }

            stockService.decrease(key, quantity);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        } finally {
            lock.unlock();
        }
    }

}
