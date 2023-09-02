package com.nw.primefinder.service.helper;

import com.nw.primefinder.model.GetPrimeRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Slf4j
public class SquareRootStrategy implements PrimeFinderStrategy {
    /**
     * Repeat for each number from 2 to n mark the number as prime if it's not divisible by any number from 2 to closest square root.
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
        List<Future<Integer>> list = new ArrayList<>();
        for (int i = 2; i <= initial; i++) {
            int num = i;
            list.add(primesTaskExecutor.submit(() -> {
                boolean isPrime = true;
                for (int j = 2; j * j <= num; j++) {
                    if (num % j == 0) {
                        isPrime = false;
                        break;
                    }
                }
                int result;
                if (isPrime) {
                    result = num;
                } else {
                    result = -1;
                }
                return result;
            }));
        }
        List<Integer> primes = list.stream().map(future -> {
            try {
                return future.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).filter(num -> num != -1).collect(Collectors.toList());
        log.trace("Finished executing getPrimes");
        return primes;
    }
}
