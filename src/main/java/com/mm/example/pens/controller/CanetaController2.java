package com.mm.example.pens.controller;

import com.mm.example.pens.dto.CanetaAtualizaPrecoDto;
import com.mm.example.pens.service.CanetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/caneta")
public class CanetaController2 {
    @Autowired
    private CanetaService canetaService;



//    @GetMapping
//    public List<Caneta> listar() {
//        return canetaService.findAll();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Caneta> buscar(@PathVariable Long id) {
//        return canetaService.findById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @PostMapping
//    public ResponseEntity<Caneta> criar(@RequestBody Caneta caneta) {
//        Caneta salva = canetaService.save(caneta);
//        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Caneta> atualizar(@PathVariable Long id, @RequestBody Caneta caneta) {
//        return canetaService.findById(id)
//                .map(existente -> {
//                    caneta.setId(id);
//                    return ResponseEntity.ok(canetaService.save(caneta));
//                })
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> remover(@PathVariable Long id) {
//        if (canetaService.findById(id).isPresent()) {
//            canetaService.deleteById(id);
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.notFound().build();
//    }
}
