package com.fintech.loanservice.exception;


import com.fintech.loanservice.constants.Code;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Data
public class CustomException extends RuntimeException {

    Error error;

    public CustomException(Code code, String message) {
        error = new Error(code, message);
    }
}
