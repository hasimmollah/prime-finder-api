package com.nw.primefinder.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;
@JsonTypeName("Error")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT ,use = JsonTypeInfo.Id.NAME)
@Data
@AllArgsConstructor
public class ErrorResponse {
    @JsonProperty("Message")
    private String message;
    @JsonProperty("Code")
    private String code;
}
