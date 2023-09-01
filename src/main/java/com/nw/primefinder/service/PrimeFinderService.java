package com.nw.primefinder.service;

import com.nw.primefinder.model.GetPrimeRequest;
import com.nw.primefinder.model.GetPrimeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static com.nw.primefinder.service.helper.StrategyFactory.getPrimeFinderStrategy;

@Service
@Slf4j
public class PrimeFinderService {

    /**
     * This method get list of primes based on the strategy provided.
     * @param request
     * @return
     */
    @Cacheable(value = "primes", key = "#request.initial")
    public GetPrimeResponse getPrimes(GetPrimeRequest request){
        log.trace("Started executing getPrimes ( request : {} )", request);
        GetPrimeResponse getPrimeResponse = new GetPrimeResponse(request.getInitial(), getPrimeFinderStrategy( request.getStrategy()).getPrimes(request));
        log.trace("Finished executing getPrimes");
        return getPrimeResponse;
    }

}
