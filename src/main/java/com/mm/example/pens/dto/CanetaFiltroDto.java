package com.mm.example.pens.dto;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
public class CanetaFiltroDto {
    private Double precoMin;
    private Double precoMax;
    private String modelo;
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private LocalDate dataFabMin;
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private LocalDate dataFabMax;
}
