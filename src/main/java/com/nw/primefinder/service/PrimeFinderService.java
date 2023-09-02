package com.nw.primefinder.service;

import com.nw.primefinder.model.GetPrimeRequest;
import com.nw.primefinder.model.GetPrimeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import static com.nw.primefinder.Constants.PRIMES_TASK_EXECUTOR;
import static com.nw.primefinder.service.helper.StrategyFactory.getPrimeFinderStrategy;

@Service
@Slf4j
public class PrimeFinderService {
    @Qualifier(PRIMES_TASK_EXECUTOR)
    @Autowired
    private ThreadPoolTaskExecutor primesTaskExecutor;

    public PrimeFinderService(ThreadPoolTaskExecutor primesTaskExecutor) {
        this.primesTaskExecutor = primesTaskExecutor;
    }

    /**
     * This method get list of primes based on the strategy provided.
     *
     * @param request
     * @return
     */
    @Cacheable(value = "primes", key = "#request.initial")
    public GetPrimeResponse getPrimes(GetPrimeRequest request) {
        log.trace("Started executing getPrimes ( request : {} )", request);
        GetPrimeResponse getPrimeResponse = new GetPrimeResponse(request.getInitial(), getPrimeFinderStrategy(request.getStrategy()).getPrimes(request, primesTaskExecutor));
        log.trace("Finished executing getPrimes");
        return getPrimeResponse;
    }

}
