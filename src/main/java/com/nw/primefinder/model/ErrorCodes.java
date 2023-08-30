package com.nw.primefinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCodes {
    APPLICATION_EXCEPTION(
            "ERR_001",
            "Exception has occurred, please contact system admin"
    ),
    METHOD_ARGUMENT_MISMATCH_EXCEPTION(
            "ERR_002",
            "Invalid value"
    );

    private final String code;
    private final String description;
}
