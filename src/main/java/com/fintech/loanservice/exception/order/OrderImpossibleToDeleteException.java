package com.fintech.loanservice.exception.order;

import com.fintech.loanservice.constants.Code;
import com.fintech.loanservice.exception.CustomException;

public class OrderImpossibleToDeleteException extends CustomException {

    public OrderImpossibleToDeleteException() {
        super(Code.ORDER_IMPOSSIBLE_TO_DELETE, "Невозможно удалить заказ");
    }
}
