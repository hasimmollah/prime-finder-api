package com.nw.primefinder.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GetPrimeResponse {
    @JsonProperty("Initial")
    private int initial;
    @JsonProperty("Primes")
    private List<Integer> primes;
}
