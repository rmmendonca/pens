package com.mm.example.pens.service;

import com.mm.example.pens.exception.BadRequestException;
import com.mm.example.pens.model.Caneta;
import com.mm.example.pens.repository.CanetaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CanetaServiceTest {
    @Mock
    private CanetaRepository canetaRepository;

    @InjectMocks
    private CanetaService canetaService;

    private Caneta caneta;
    private List<Caneta> canetas;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        caneta = new Caneta();
        caneta.setID(1L);
        caneta.setModelo("BIC");
        caneta.setPreco(new BigDecimal("5.00"));
        caneta.setDataProducao(LocalDate.of(2023,1,1));

        Caneta c1 = new Caneta();
        c1.setID(1L);
        c1.setModelo("BIC");
        c1.setPreco(new BigDecimal("5.00"));
        c1.setDataProducao(LocalDate.of(2023,1,1));

        Caneta c2 = new Caneta();
        c2.setID(2L);
        c2.setModelo("Faber");
        c2.setPreco(new BigDecimal("10.00"));
        c2.setDataProducao(LocalDate.of(2022,6,15));

        canetas = List.of(c1, c2);
    }

    @Nested
    @DisplayName("Cenários de Sucesso")
    class Sucesso {
        @Test
        void deveRetornarCanetaPorId() {
            when(canetaRepository.findById(1L)).thenReturn(Optional.of(caneta));
            Optional<Caneta> result = canetaService.findById(1L);
            assertTrue(result.isPresent());
            assertEquals("BIC", result.get().getModelo());
        }

        @Test
        void deveSalvarCaneta() {
            when(canetaRepository.save(any(Caneta.class))).thenReturn(caneta);
            Caneta result = canetaService.save(caneta);
            assertNotNull(result);
            assertEquals("BIC", result.getModelo());
        }

        @Test
        void deveRetornarTodasCanetas() {
            when(canetaRepository.findAll()).thenReturn(canetas);
            List<Caneta> result = canetaService.findAll();
            assertThat(result).hasSize(2);
        }

        @Test
        void deveDeletarCanetaQuandoIdExiste() {
            when(canetaRepository.findById(1L)).thenReturn(Optional.of(caneta));
            canetaService.deleteById(1L);
            verify(canetaRepository, times(1)).deleteById(1L);
        }

    }

    @Nested
    @DisplayName("Cenários de Falha")
    class Falha {
        @Test
        void deveRetornarVazioQuandoIdNaoExiste() {
            when(canetaRepository.findById(2L)).thenReturn(Optional.empty());
            Optional<Caneta> result = canetaService.findById(2L);
            assertFalse(result.isPresent());
        }
        @Test
        void naoDeveSalvarCanetaComModeloNulo() {
            Caneta invalida = new Caneta();
            invalida.setPreco(new BigDecimal("5.00"));
            invalida.setDataProducao(LocalDate.now());
            invalida.setModelo(null);

            assertThrows(BadRequestException.class, () -> canetaService.save(invalida));
        }

        @Test
        void naoDeveSalvarCanetaComPrecoNulo() {
            Caneta invalida = new Caneta();
            invalida.setModelo("BIC");
            invalida.setDataProducao(LocalDate.now());
            invalida.setPreco(null);

            assertThrows(BadRequestException.class, () -> canetaService.save(invalida));
        }

        @Test
        void naoDeveSalvarCanetaComPrecoNegativo() {
            Caneta invalida = new Caneta();
            invalida.setModelo("BIC");
            invalida.setPreco(new BigDecimal("-10.00"));
            invalida.setDataProducao(LocalDate.now());

            assertThrows(BadRequestException.class, () -> canetaService.save(invalida));
        }

        @Test
        void naoDeveSalvarCanetaComDataFabricacaoFutura() {
            Caneta invalida = new Caneta();
            invalida.setModelo("BIC");
            invalida.setPreco(new BigDecimal("5.00"));
            invalida.setDataProducao(LocalDate.now().plusYears(1));

            assertThrows(BadRequestException.class, () -> canetaService.save(invalida));
        }
    }

    @Nested
    @DisplayName("Cenários de Borda")
    class Borda {
        @Test
        void deveSalvarCanetaComPrecoZero() {
            caneta.setPreco(BigDecimal.ZERO);
            when(canetaRepository.save(any(Caneta.class))).thenReturn(caneta);
            Caneta result = canetaService.save(caneta);
            assertThat(result.getPreco()).isZero();
        }

        @Test
        void deveSalvarCanetaComDataProducaoHoje() {
            caneta.setDataProducao(LocalDate.now());
            when(canetaRepository.save(any(Caneta.class))).thenReturn(caneta);
            Caneta result = canetaService.save(caneta);
            assertThat(result.getDataProducao()).isEqualTo(LocalDate.now());
        }
    }
}