package com.mm.example.pens.service;

import com.mm.example.pens.controller.CanetaController;
import com.mm.example.pens.model.Caneta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CanetaController.class)
public class CanetaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CanetaService canetaService;

    private Caneta caneta;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        caneta = new Caneta();
        caneta.setID(1L);
        caneta.setModelo("BIC");
        caneta.setPreco(new BigDecimal("5.00"));
        caneta.setDataProducao(LocalDate.of(2023,1,1));

    }

    @Nested
    @DisplayName("Cenários de Sucesso")
    class Sucesso{
        @Test
        void deveRetornarCanetaPorId() throws Exception {
            when(canetaService.findById(1L)).thenReturn(Optional.of(caneta));
            mockMvc.perform(get("/canetas/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.modelo").value("BIC"));
        }
    }

    @Nested
    @DisplayName("Cenários de Falha")
    class Falha{
        @Test
        void deveRetornarNotFoundQuandoIdNaoExiste() throws Exception {
            when(canetaService.findById(99L)).thenReturn(Optional.empty());
            mockMvc.perform(get("/canetas/99"))
                    .andExpect(status().isNotFound());
        }

        @Test
        void deveRetornarBadRequestQuandoIdInvalido() throws Exception{
            mockMvc.perform(get("/canetas/K")).andExpect(status().isBadRequest());
        }


    }

    @Nested
    @DisplayName("Cenários de borda")
    class Borda{
        @Test
        void deveRetornarBadRequestQuandoIdForNegativo() throws Exception {
            mockMvc.perform(get("/canetas/-1"))
                    .andExpect(status().isNotFound()); //not found pq spring nao aceita long negativo no path variable
        }

        @Test
        void deveRetornarNotFoundQuandoIdForMaxLong() throws Exception {
            when(canetaService.findById(Long.MAX_VALUE)).thenReturn(Optional.empty());
            mockMvc.perform(get("/canetas/" + Long.MAX_VALUE))
                    .andExpect(status().isNotFound());
        }
    }

}
