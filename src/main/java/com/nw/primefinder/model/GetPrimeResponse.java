package com.nw.primefinder.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPrimeResponse {
    @JsonProperty("Initial")
    private int initial;
    @JsonProperty("Primes")
    private List<Integer> primes;
}
