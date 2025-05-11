package com.mm.example.pens.controller;

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
    public CanetaController(CanetaService canetaService) {
        this.canetaService = canetaService;
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

}
