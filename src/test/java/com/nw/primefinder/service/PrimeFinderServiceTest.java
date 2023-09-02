package com.nw.primefinder.service;

import com.nw.primefinder.model.GetPrimeRequest;
import com.nw.primefinder.model.GetPrimeResponse;
import com.nw.primefinder.model.Strategy;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.nw.primefinder.TestUtil.getPrimesTaskExecutor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

 class PrimeFinderServiceTest {
    private PrimeFinderService primeFinderService;

    private static Stream<Arguments> testInputProvider() {
        int initial10 = 10;
        List<Integer> primesTill10 = List.of(2, 3, 5, 7);

        int initial100 = 100;
        List<Integer> primesTill100 = List.of(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97);

        int initial1000 = 1000;
        List<Integer> primesTill1000 = List.of(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997);


        GetPrimeRequest getPrimeRequest10 = GetPrimeRequest.builder()
                .initial(initial10)
                .strategy(Strategy.SIEVE_OF_ERATOSTHENES)
                .build();
        GetPrimeResponse getPrimeResponse10 = new GetPrimeResponse(initial10, primesTill10);
        GetPrimeRequest getPrimeRequest100 = GetPrimeRequest.builder()
                .initial(initial100)
                .strategy(Strategy.TWO_TO_TEN_AND_PRIME)
                .build();
        GetPrimeResponse getPrimeResponse100 = new GetPrimeResponse(initial100, primesTill100);

        GetPrimeRequest getPrimeRequest1000 = GetPrimeRequest.builder()
                .initial(initial1000)
                .strategy(Strategy.SQUARE_ROOT)
                .build();
        GetPrimeResponse getPrimeResponse1000 = new GetPrimeResponse(initial1000, primesTill1000);
        GetPrimeRequest getPrimeRequest100BruteForce = GetPrimeRequest.builder()
                .initial(initial1000)
                .strategy(Strategy.BRUTE_FORCE)
                .build();
        GetPrimeResponse getPrimeResponse1000BruteForce = new GetPrimeResponse(initial1000, primesTill1000);

        GetPrimeRequest getPrimeRequest100NullStrategy = GetPrimeRequest.builder()
                .initial(initial1000)
                .strategy(null)
                .build();
        GetPrimeResponse getPrimeResponse1000NullStrategy = new GetPrimeResponse(initial1000, primesTill1000);


        return Stream.of(
                Arguments.of(getPrimeRequest10, getPrimeResponse10),
                Arguments.of(getPrimeRequest100, getPrimeResponse100),
                Arguments.of(getPrimeRequest1000, getPrimeResponse1000),
                Arguments.of(getPrimeRequest100BruteForce, getPrimeResponse1000BruteForce),
                Arguments.of(getPrimeRequest100NullStrategy, getPrimeResponse1000NullStrategy)

        );
    }

    @BeforeEach
    void setup() {
        primeFinderService = new PrimeFinderService(getPrimesTaskExecutor());

    }

    @ParameterizedTest
    @MethodSource("testInputProvider")
    @SneakyThrows
    void shouldTestGetPrimes(GetPrimeRequest request, GetPrimeResponse getPrimeResponseExpected) {
        GetPrimeResponse getPrimeResponseActual = primeFinderService.getPrimes(request);
        assertEquals(getPrimeResponseExpected.getInitial(), getPrimeResponseActual.getInitial());
        assertEquals(getPrimeResponseExpected.getPrimes().size(), getPrimeResponseActual.getPrimes().size());
        assertTrue(getPrimeResponseExpected.getPrimes().containsAll(getPrimeResponseActual.getPrimes()));
    }
}
