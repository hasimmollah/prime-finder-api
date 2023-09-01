package com.nw.primefinder.service.helper;

import com.nw.primefinder.model.GetPrimeRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BruteForceStrategy implements PrimeFinderStrategy {
    /**
     * Iterate each number from 2 to n, mark the number as prime if it's not divisible by any number from 2 to half the number.
     * @param  request containing initial value and strategy
     * @return List of primes
     */
    @Override
    public List<Integer> getPrimes(GetPrimeRequest request) {
        log.trace("Started executing getPrimes (request: {} )", request);
        int initial = request.getInitial();
        List<Integer> list = new ArrayList<>();
        for(int i =2; i<= initial;i++){
            if(isPrime(i)){
                list.add(i);
            }
        }
        log.trace("Finished executing getPrimes");
        return list;
    }

    private boolean isPrime(int n){
        boolean isPrime = true;
        for(int i = 2; i<= n/2; i++){
            if(n%i ==0){
                isPrime = false;
                break;
            }
        }
        return isPrime;
    }
}
