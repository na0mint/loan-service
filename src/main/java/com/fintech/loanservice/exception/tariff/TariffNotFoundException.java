package com.fintech.loanservice.exception.tariff;

import com.fintech.loanservice.constants.Code;
import com.fintech.loanservice.exception.CustomException;

public class TariffNotFoundException extends CustomException {

    public TariffNotFoundException() {
        super(Code.TARIFF_NOT_FOUND, "Тариф не найден");
    }
}
