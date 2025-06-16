package com.bankapp.dto.Request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {
    @NotNull
    @DecimalMin(value = "1.00", message = "Transactions of amount $1 or less are not supported")
    private BigDecimal amount;
}

