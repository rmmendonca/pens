package com.mm.example.pens.repository;

import com.mm.example.pens.model.Caneta;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;


public interface CanetaRepositoryCustom {
    List<Caneta> filtrar (Double precoMin, Double precoMax, String modelo, LocalDate dataFabMin, LocalDate dataFabMax);

    List<Caneta> filtrarJDBC(Double precoMin, Double precoMax, String modelo, LocalDate dataFabMin, LocalDate dataFabMax);
}
