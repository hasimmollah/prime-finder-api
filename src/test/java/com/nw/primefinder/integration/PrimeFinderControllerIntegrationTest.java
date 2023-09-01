package com.nw.primefinder.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.nw.primefinder.model.ErrorCodes;
import com.nw.primefinder.model.ErrorResponse;
import com.nw.primefinder.model.GetPrimeResponse;
import com.nw.primefinder.model.Strategy;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("IntegrationTest")
public class PrimeFinderControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    private static Stream<Arguments> testInputProvider() {
        String initial10 = "10";
        List<Integer> primesTill10 = List.of(2,3,5,7);

        String initial100 = "100";
        List<Integer> primesTill100 = List.of(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97);

        String initial1000 = "1000";
        List<Integer> primesTill1000 = List.of(2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97,101,103,107,109,113,127,131,137,139,149,151,157,163,167,173,179,181,191,193,197,199,211,223,227,229,233,239,241,251,257,263,269,271,277,281,283,293,307,311,313,317,331,337,347,349,353,359,367,373,379,383,389,397,401,409,419,421,431,433,439,443,449,457,461,463,467,479,487,491,499,503,509,521,523,541,547,557,563,569,571,577,587,593,599,601,607,613,617,619,631,641,643,647,653,659,661,673,677,683,691,701,709,719,727,733,739,743,751,757,761,769,773,787,797,809,811,821,823,827,829,839,853,857,859,863,877,881,883,887,907,911,919,929,937,941,947,953,967,971,977,983,991,997);
        ObjectMapper om = new ObjectMapper();
        ObjectMapper xmlMapper = new XmlMapper();
        return Stream.of(
                Arguments.of(initial10, Strategy.TWO_TO_TEN_AND_PRIME, true, MediaType.APPLICATION_JSON, prepareConsumerForSuccessfulMvcResult(initial10, om, primesTill10)),
                Arguments.of(initial100, Strategy.BRUTE_FORCE, true, MediaType.APPLICATION_JSON, prepareConsumerForSuccessfulMvcResult(initial100, om,  primesTill100)),
                Arguments.of(initial1000, Strategy.SQUARE_ROOT, true, MediaType.APPLICATION_JSON, prepareConsumerForSuccessfulMvcResult(initial1000, om,  primesTill1000)),
                Arguments.of(initial10, Strategy.SIEVE_OF_ERATOSTHENES, true, MediaType.APPLICATION_XML, prepareConsumerForSuccessfulMvcResult(initial10, xmlMapper,  primesTill10)),
                Arguments.of(initial10 + "<script></script>", Strategy.SIEVE_OF_ERATOSTHENES, true, MediaType.APPLICATION_XML, prepareConsumerForSuccessfulMvcResult(initial10, xmlMapper,  primesTill10)),
                Arguments.of("12ts", Strategy.SIEVE_OF_ERATOSTHENES, false, MediaType.APPLICATION_JSON, prepareConsumerForErrorResponseMvcResult( om, new ErrorResponse(
                        ErrorCodes.METHOD_ARGUMENT_MISMATCH_EXCEPTION.getCode(),
                        ErrorCodes.METHOD_ARGUMENT_MISMATCH_EXCEPTION.getDescription()))),
                Arguments.of("12ts", null, false, MediaType.APPLICATION_JSON, prepareConsumerForErrorResponseMvcResult( om, new ErrorResponse(
                        ErrorCodes.METHOD_ARGUMENT_MISMATCH_EXCEPTION.getCode(),
                        ErrorCodes.METHOD_ARGUMENT_MISMATCH_EXCEPTION.getDescription())))
        );
    }
    private static Consumer<MvcResult> prepareConsumerForSuccessfulMvcResult(String initial,ObjectMapper om, List<Integer> expectedPrimes){
        return  (mvcResult) -> {
            GetPrimeResponse getPrimeResponseActual = null;
            try {
                getPrimeResponseActual = om.readValue(mvcResult.getResponse().getContentAsString(), GetPrimeResponse.class);
            } catch (Exception e) {
                fail("Shouldn't reach here");
            }
            assertEquals(Integer.valueOf(initial), getPrimeResponseActual.getInitial());
            assertEquals(expectedPrimes.size(), getPrimeResponseActual.getPrimes().size());
            assertTrue(expectedPrimes.containsAll( getPrimeResponseActual.getPrimes()));
        };
    }

    private static Consumer<MvcResult> prepareConsumerForErrorResponseMvcResult( ObjectMapper om, ErrorResponse errorResponseExpected){
        return  (mvcResult) -> {
            ErrorResponse errorResponseActual = null;
            try {
                errorResponseActual = om.readValue(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);
            } catch (Exception e) {
                fail("Shouldn't reach here");
            }
            assertEquals(errorResponseExpected.getCode(), errorResponseActual.getCode());
            assertTrue(errorResponseActual.getMessage().contains(errorResponseExpected.getMessage()));
        };
    }


    @ParameterizedTest
    @MethodSource("testInputProvider")
    public void shouldExecuteQueryAndValidateResponse(String initial, Strategy strategy, boolean isSuccessScenario, MediaType mediaType, Consumer<MvcResult> responseValidator) throws Exception {

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/prime/" + initial);
        if(strategy!=null){
            mockHttpServletRequestBuilder.header("strategy", strategy);
        }

        mockHttpServletRequestBuilder.accept(mediaType);


        ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
        final MvcResult mvcResult;
        if(isSuccessScenario){
            resultActions = resultActions.andExpect(request().asyncStarted());
            mvcResult =  resultActions.andReturn();
            mockMvc.perform(asyncDispatch(mvcResult));
        } else {
            mvcResult =  resultActions.andReturn();
        }

        responseValidator.accept(mvcResult);




    }
}
