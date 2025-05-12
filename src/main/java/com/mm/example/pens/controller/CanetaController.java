package com.mm.example.pens.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mm.example.pens.dto.CanetaAtualizaPrecoDto;
import com.mm.example.pens.dto.CanetaFiltroDto;
import com.mm.example.pens.exception.ResourceNotFoundException;
import com.mm.example.pens.model.Caneta;
import com.mm.example.pens.service.CanetaService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/canetas")
public class CanetaController {

    private final CanetaService canetaService;
    private final ObjectMapper objectMapper;

    public CanetaController(CanetaService canetaService, ObjectMapper objectMapper) {
        this.canetaService = canetaService;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public List<Caneta> listar(){
        return canetaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return canetaService.findById(id).map(ResponseEntity :: ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/geh/{id}")
    public ResponseEntity<?> findByIdGEH(@PathVariable Long id){
        return canetaService.findById(id).map(ResponseEntity :: ok)
                .orElseThrow(() -> new ResourceNotFoundException("caneta nao encontrada com o id " +id));
    }

    @PostMapping("/filtro")
    public List<Caneta> findByFiltro(@RequestBody CanetaFiltroDto filtroDto){
        return canetaService.findByFiltro(filtroDto);
    }

    @PostMapping("/filtroModelo")
    public List<Caneta> findByFiltroModelo(@RequestBody String modelo){
        return canetaService.findByModelo(modelo);
    }

    @PatchMapping("/{id}/preco")
    public ResponseEntity<?> patchPreco(@PathVariable Long id, @RequestBody @Validated CanetaAtualizaPrecoDto dto) {
        return canetaService.findById(id)
                .map(existente -> {
                    existente.setPreco(dto.getPreco());
                    return ResponseEntity.ok(canetaService.save(existente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Caneta> atualizar(@PathVariable Long id, @RequestBody Caneta caneta) {
        return canetaService.findById(id)
                .map(existente -> {
                    caneta.setID(id);
                    return ResponseEntity.ok(canetaService.save(caneta));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/put2/{id}")
    public ResponseEntity<Caneta> atualizarOM(@PathVariable Long id, @RequestBody Caneta caneta) {
        return canetaService.findById(id)
                .map(existente -> {
                    try {
                        objectMapper.updateValue(existente, caneta);
                    } catch (JsonMappingException e) {
                        throw new RuntimeException(e);
                    }
                    return ResponseEntity.ok(canetaService.save(existente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return canetaService.findById(id)
                .map(existente -> {
                    canetaService.deleteById(id);
                    return ResponseEntity.noContent().build();
                }).orElse(ResponseEntity.notFound().build());
    }

}
