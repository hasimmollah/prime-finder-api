package com.nw.primefinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCodes {
    APPLICATION_EXCEPTION(
            "ERR_001",
            "Number format exception occurred"
    ),
    METHOD_ARGUMENT_MISMATCH_EXCEPTION(
            "ERR_002",
            "Argument mismatch"
    );

    private final String code;
    private final String description;
}
