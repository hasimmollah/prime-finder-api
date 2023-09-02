package com.nw.primefinder.service.helper;

import com.nw.primefinder.model.GetPrimeRequest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;

public interface PrimeFinderStrategy {
    List<Integer> getPrimes(GetPrimeRequest request, ThreadPoolTaskExecutor primesTaskExecutor);
}
