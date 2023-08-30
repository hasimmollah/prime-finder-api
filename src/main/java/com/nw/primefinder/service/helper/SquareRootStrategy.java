package com.nw.primefinder.service.helper;

import com.nw.primefinder.model.GetPrimeRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SquareRootStrategy implements PrimeFinderStrategy {
    /**
     * Repeat for each number from 2 to n mark the number as prime if it's not divisible by any number from 2 to closest square root.
     * @param  request containing initial value and strategy
     * @return List of primes
     */
    @Override
    public List<Integer> getPrimes(GetPrimeRequest request) {
        log.trace("Started executing getPrimes (request: {} )", request);
        int initial = request.getInitial();
        List<Integer> list = new ArrayList<>();
        boolean flag ;
        for (int i = 2; i <= initial; i++) {
            flag = true;
            for (int j = 2; j * j <= i; j++) {
                if (i % j == 0) {
                    flag = false;
                    break;
                }
            }
            if(flag){
                list.add(i);
            }
        }
        log.trace("Finished executing getPrimes");
        return list;
    }
}
