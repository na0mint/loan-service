package com.fintech.loanservice.exception.order;

import com.fintech.loanservice.constants.Code;
import com.fintech.loanservice.exception.CustomException;

public class LoanConsiderationException extends CustomException {

    public LoanConsiderationException() {

        super(Code.LOAN_CONSIDERATION ,"Заявка рассматривается");
    }
}
