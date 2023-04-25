package com.fintech.loanservice.exception.order;

import com.fintech.loanservice.constants.Code;
import com.fintech.loanservice.exception.CustomException;

public class TryLaterException extends CustomException {

    public TryLaterException() {
        super(Code.TRY_LATER, "Попробуйте позже");
    }
}
