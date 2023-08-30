package com.nw.primefinder.service;

import com.nw.primefinder.model.GetPrimeRequest;
import com.nw.primefinder.model.GetPrimeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.nw.primefinder.service.helper.StrategyFactory.getPrimeFinderStrategy;

@Service
@Slf4j
public class PrimeFinderService {
    public GetPrimeResponse getPrimes(GetPrimeRequest request){
        log.trace("Started executing getPrimes ( request : {} )", request);
        GetPrimeResponse getPrimeResponse = new GetPrimeResponse();
        getPrimeResponse.setInitial( request.getInitial());
        getPrimeResponse.setPrimes(getPrimeFinderStrategy( request.getStrategy()).getPrimes(request));
        log.trace("Finished executing getPrimes");
        return getPrimeResponse;
    }

}
