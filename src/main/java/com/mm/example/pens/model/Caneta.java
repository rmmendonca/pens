package com.mm.example.pens.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "CANETA2", schema = "MM")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "caneta_seq", schema = "MM", sequenceName = "CANETA_SEQ", allocationSize = 1)
public class Caneta {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "caneta_seq")
    private Long ID;

    @Column(name = "MODELO")
    private String modelo;

    @Column(name = "DATA_PRODUCAO")
    private LocalDate dataProducao;

    @Column(name = "QUANTIDADE")
    private Integer quantidade;

    @Column(name = "PRECO")
    private BigDecimal preco;


}
