package com.fintech.loanservice.exception;

import com.fintech.loanservice.constants.Code;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Error {
    Code code;
    String message;
}
