package com.mm.example.pens.repository;

import com.mm.example.pens.model.Caneta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CanetaRepository extends JpaRepository<Caneta, Long>, CanetaRepositoryCustom {
    @Query(nativeQuery = true, value = " select * from MM.CANETA2 a where upper(a.MODELO) like upper('%:modelo%') ")
    ArrayList<Caneta> findAllByModelo(String modelo);
}
