package com.nw.primefinder.service.helper;

import com.nw.primefinder.model.GetPrimeRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SieveOfEratosthenesStrategy implements PrimeFinderStrategy {
    /**
     * Initially mark all numbers from 2 to n as prime.
     * Then starting with number 2 mark all its multiples as non prime, repeat for all numbers for which square is less than equals to n
     * @param  request containing initial value and strategy
     * @return List of primes
     */
    @Override
    public List<Integer> getPrimes(GetPrimeRequest request) {
        log.trace("Started executing getPrimes (request: {} )", request);
        int n = request.getInitial();
        List<Integer> list = new ArrayList<>();
        boolean prime[] = new boolean[n + 1];
        for (int i = 0; i <= n; i++){
            prime[i] = true;
        }

        for (int i = 2; i * i <= n; i++) {
            if (prime[i] == true) {
                for (int j = i * i; j <= n; j += i)
                    prime[j] = false;
            }
        }

        for (int i = 2; i <= n; i++) {
            if (prime[i] == true){
                list.add(i);
            }
        }
        log.trace("Finished executing getPrimes");
        return list;
    }
}
