package com.mm.example.pens.service;

import com.mm.example.pens.dto.CanetaFiltroDto;
import com.mm.example.pens.model.Caneta;
import com.mm.example.pens.repository.CanetaRepository;
import org.springframework.stereotype.Service;

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
        return canetaRepository.findById(id);
    }

    public List<Caneta> findByFiltro(CanetaFiltroDto f){
        return canetaRepository.filtrar(f.getPrecoMin(),f.getPrecoMax(),f.getModelo(), f.getDataFabMin(), f.getDataFabMax());
    }

    public List<Caneta> findByModelo(String modelo){
        return canetaRepository.findAllByModelo(modelo);
    }

    public Caneta save(Caneta caneta) {
        return canetaRepository.save(caneta);
    }


//    public List<CanetaEntity> findAll() {
//        return canetaRepository.findAll();
//    }
//
//    public Optional<CanetaEntity> findById(Long id) {
//        return canetaRepository.findById(id);
//    }
//

//
//    public void deleteById(Long id) {
//        canetaRepository.deleteById(id);
//    }
}
