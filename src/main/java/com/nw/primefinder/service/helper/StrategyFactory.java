package com.nw.primefinder.service.helper;

import com.nw.primefinder.model.Strategy;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StrategyFactory {
    private static Map<Strategy, PrimeFinderStrategy> map = new HashMap<>();
    static {
        map.put(Strategy.BRUTE_FORCE, new BruteForceStrategy());
        map.put(Strategy.SQUARE_ROOT, new SquareRootStrategy());
        map.put(Strategy.SIEVE_OF_ERATOSTHENES, new SieveOfEratosthenesStrategy());
        map.put(Strategy.TWO_TO_TEN_AND_PRIME, new TwoToTenAndPrimeStrategy());
    }
    public static PrimeFinderStrategy getPrimeFinderStrategy(Strategy strategy){
       return Optional.ofNullable(map.get(strategy)).orElse(map.get(Strategy.SIEVE_OF_ERATOSTHENES));
    }
}
