package com.nw.primefinder.service.helper;

import org.junit.jupiter.api.BeforeEach;

class TwoToTenAndPrimeStrategyTest extends AbstractStrategyTest{


    @BeforeEach
    void setup() {
        primeFinderStrategy = new TwoToTenAndPrimeStrategy();
    }

}
