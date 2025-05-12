package com.mm.example.pens.service;

import com.mm.example.pens.dto.CanetaFiltroDto;
import com.mm.example.pens.exception.BadRequestException;
import com.mm.example.pens.model.Caneta;
import com.mm.example.pens.repository.CanetaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CanetaService{

    private final CanetaRepository canetaRepository;

    public CanetaService(CanetaRepository canetaRepository) {
        this.canetaRepository = canetaRepository;
    }

    public List<Caneta> findAll(){
        return canetaRepository.findAll();
    }

    public Optional<Caneta> findById(Long id){
        if (id == null || id < 0L){
            throw new BadRequestException("Id não pode ser negativo");
        }
        return canetaRepository.findById(id);
    }

    public List<Caneta> findByFiltro(CanetaFiltroDto f){
        return canetaRepository.filtrar(f.getPrecoMin(),f.getPrecoMax(),f.getModelo(), f.getDataFabMin(), f.getDataFabMax());
    }

    public List<Caneta> findByModelo(String modelo){
        return canetaRepository.findAllByModelo(modelo);
    }

    public Caneta save(Caneta caneta) {
        if (caneta.getModelo() == null || caneta.getModelo().isBlank()) {
            throw new BadRequestException("Modelo é obrigatório");
        }
        if (caneta.getPreco() == null) {
            throw new BadRequestException("Preço é obrigatório");
        }
        if (caneta.getPreco().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("Preço não pode ser negativo");
        }
        if (caneta.getDataProducao() != null && caneta.getDataProducao().isAfter(LocalDate.now())) {
            throw new BadRequestException("Data de fabricação não pode ser futura");
        }
        return canetaRepository.save(caneta);
    }

    public void deleteById(Long id){
        canetaRepository.deleteById(id);
    }
}
