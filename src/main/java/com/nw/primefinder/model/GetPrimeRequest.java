package com.nw.primefinder.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetPrimeRequest {
    private int initial;
    private Strategy strategy;
}
