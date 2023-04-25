package com.fintech.loanservice.exception.order;

import com.fintech.loanservice.constants.Code;
import com.fintech.loanservice.exception.CustomException;

public class LoanIsApprovedException extends CustomException {

    public LoanIsApprovedException() {
        super(Code.LOAN_ALREADY_APPROVED ,"Заявка уже одобрена");
    }
}
