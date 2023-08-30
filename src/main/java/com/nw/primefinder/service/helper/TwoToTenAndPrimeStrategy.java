package com.nw.primefinder.service.helper;

import com.nw.primefinder.model.GetPrimeRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
public class TwoToTenAndPrimeStrategy implements PrimeFinderStrategy {
    /**
     * Repeat for each number from 2 to n mark the number prime if it's not divisible by any number from 2 to 10 and any prime number less than it.
     * @param  request containing initial value and strategy
     * @return List of primes
     */
    @Override
    public List<Integer> getPrimes(GetPrimeRequest request) {
        log.trace("Started executing getPrimes (request: {} )", request);
        int initial = request.getInitial();

        List<Integer> listOfPrimes = new ArrayList<>(Arrays.asList(2,3,5,7,11,13,17,19));
        if(initial <= 20){
            return listOfPrimes.stream().filter(num -> num <= initial).collect(Collectors.toList());
        }

        List<Integer> fourToTenNonPrimeList = List.of(4,6,8,9,10);

        for(int i =21; i<= initial; i++){
            boolean flag = true;
            for(int j: fourToTenNonPrimeList){
                if(i % j ==0){
                    flag = false;
                    break;
                }
            }
            if(!flag){
                continue;
            }
            int jThPrime;
            for(int j = 0; (jThPrime = listOfPrimes.get(j)) <= i/2; j++){
                if(i % jThPrime ==0){
                    flag = false;
                    break;
                }
            }
            if(flag){
                listOfPrimes.add(i);
            }

        }
        log.trace("Finished executing getPrimes");
        return listOfPrimes;
    }
}
