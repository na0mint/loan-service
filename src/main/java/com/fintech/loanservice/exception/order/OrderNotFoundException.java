package com.fintech.loanservice.exception.order;

import com.fintech.loanservice.constants.Code;
import com.fintech.loanservice.exception.CustomException;

public class OrderNotFoundException extends CustomException {

    public OrderNotFoundException() {
        super(Code.ORDER_NOT_FOUND, "Заявка не найдена");
    }
}
