package com.nw.primefinder.service.helper;

import com.nw.primefinder.model.GetPrimeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SquareRootStrategyTest extends AbstractStrategyTest{


    @BeforeEach
    void setup() {
        primeFinderStrategy = new SquareRootStrategy();
    }

}
