package com.nw.primefinder.service.helper;

import com.nw.primefinder.model.GetPrimeRequest;

import java.util.List;

public interface PrimeFinderStrategy {
    List<Integer> getPrimes(GetPrimeRequest request);
}
