package com.nw.primefinder.service.helper;

import com.nw.primefinder.model.GetPrimeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.stream.Stream;

import static com.nw.primefinder.Constants.PRIMES_TASK_EXECUTOR;
import static com.nw.primefinder.TestUtil.getPrimesTaskExecutor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class AbstractStrategyTest {
    protected PrimeFinderStrategy primeFinderStrategy;
     @ParameterizedTest
    @MethodSource("testInputProvider")
    void shouldTestGetPrimes(GetPrimeRequest request, List<Integer> getPrimesExpected) {
        List<Integer> getPrimesActual = primeFinderStrategy.getPrimes(request, getPrimesTaskExecutor() );
        assertEquals(getPrimesExpected.size(), getPrimesExpected.size());
        assertTrue(getPrimesExpected.containsAll(getPrimesActual));
    }

    private static Stream<Arguments> testInputProvider() {
        int initial19 = 19;
        List<Integer> primesTill19 = List.of(2, 3, 5, 7, 11, 13, 17, 19);

        GetPrimeRequest getPrimeRequest19 = GetPrimeRequest.builder()
                .initial(initial19)
                .build();

        int initial20 = 20;
        List<Integer> primesTill20 = List.of(2, 3, 5, 7, 11, 13, 17, 19);

        GetPrimeRequest getPrimeRequest20 = GetPrimeRequest.builder()
                .initial(initial20)
                .build();

        int initial22 = 22;
        List<Integer> primesTill22 = List.of(2, 3, 5, 7, 11, 13, 17, 19);

        GetPrimeRequest getPrimeRequest22 = GetPrimeRequest.builder()
                .initial(initial22)
                .build();

        int initial23 = 23;
        List<Integer> primesTill23 = List.of(2, 3, 5, 7, 11, 13, 17, 19, 23);

        GetPrimeRequest getPrimeRequest23 = GetPrimeRequest.builder()
                .initial(initial23)
                .build();

        return Stream.of(
                Arguments.of(getPrimeRequest19, primesTill19),
                Arguments.of(getPrimeRequest20, primesTill20),
                Arguments.of(getPrimeRequest22, primesTill22),
                Arguments.of(getPrimeRequest23, primesTill23)

        );
    }
}
