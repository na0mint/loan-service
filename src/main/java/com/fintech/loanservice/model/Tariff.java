package com.fintech.loanservice.model;

import com.fintech.loanservice.constants.TariffType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Tariff {

    long id;

    @NotNull(message = "Tariff type is required")
    TariffType type;

    @NotNull(message = "Interest rate is required")
    @Pattern(regexp = "[6-9]|1[0-9]|2[0-2]%")
    String interestRate;
}
