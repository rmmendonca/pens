package com.mm.example.pens.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CanetaAtualizaPrecoDto {
    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "O valor deve ser maior que 0.0")
    private BigDecimal preco;
}
