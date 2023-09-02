package com.nw.primefinder.service.helper;

import com.nw.primefinder.model.GetPrimeRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static com.nw.primefinder.Constants.HALF;

@Slf4j
public class TwoToTenAndPrimeStrategy implements PrimeFinderStrategy {
    /**
     * Repeat for each number from 2 to n mark the number prime if it's not divisible by any number from 2 to 10 and any prime number less than it.
     *
     * @param request            containing initial value and strategy
     * @param primesTaskExecutor
     * @return List of primes
     */
    @Override
    @SneakyThrows
    public List<Integer> getPrimes(GetPrimeRequest request, ThreadPoolTaskExecutor primesTaskExecutor) {
        log.trace("Started executing getPrimes (request: {} )", request);
        int initial = request.getInitial();

        List<Integer> listOfPrimes = new ArrayList<>(Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19));
        if (initial <= 20) {
            return listOfPrimes.stream().filter(num -> num <= initial).collect(Collectors.toList());
        }

        List<Integer> fourToTenNonPrimeList = List.of(4, 6, 8, 9, 10);

        for (int i = 21; i <= initial; i++) {
            boolean isPrime = true;
            for (int j : fourToTenNonPrimeList) {
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (!isPrime) {
                continue;
            }

            Iterator<Integer> itr = listOfPrimes.iterator();
            while (itr.hasNext()) {
                int currPrime = itr.next();
                if (currPrime > Math.pow(i, HALF) + 1) {
                    break;
                }
                if (i % currPrime == 0) {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime) {
                listOfPrimes.add(i);
            }
        }
        log.trace("Finished executing getPrimes");
        return listOfPrimes;
    }


    private List<Integer> getPrimesWithOutMultiThread(GetPrimeRequest request) {
        int initial = request.getInitial();

        List<Integer> listOfPrimes = new ArrayList<>(Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19));
        if (initial <= 20) {
            return listOfPrimes.stream().filter(num -> num <= initial).collect(Collectors.toList());
        }

        List<Integer> fourToTenNonPrimeList = List.of(4, 6, 8, 9, 10);

        for (int i = 21; i <= initial; i++) {
            boolean isPrime = true;
            for (int j : fourToTenNonPrimeList) {
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (!isPrime) {
                continue;
            }

            Iterator<Integer> itr = listOfPrimes.iterator();
            while (itr.hasNext()) {
                int jThPrime = itr.next();
                if (jThPrime > Math.pow(i, HALF) + 1) {
                    break;
                }
                if (i % jThPrime == 0) {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime) {
                listOfPrimes.add(i);
            }
        }
        return listOfPrimes;
    }
}
