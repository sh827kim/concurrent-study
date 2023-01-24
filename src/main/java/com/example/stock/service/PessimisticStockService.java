package com.example.stock.service;

import com.example.stock.repository.StockRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    //@Transactional
    public synchronized void decrease(Long id, Long quantity) {
        var stock = stockRepository.findById(id).orElseThrow();
        stock.decrease(quantity);
        stockRepository.saveAndFlush(stock);
    }
}
