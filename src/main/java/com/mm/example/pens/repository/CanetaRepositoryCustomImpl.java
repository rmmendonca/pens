package com.mm.example.pens.repository;

import com.mm.example.pens.model.Caneta;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CanetaRepositoryCustomImpl implements CanetaRepositoryCustom{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Caneta> filtrar(Double precoMin, Double precoMax, String modelo, LocalDate dataFabMin, LocalDate dataFabMax) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select * from MM.CANETA2 where 1=1 ");

        List<Object> params = new ArrayList<>();

        if (precoMin != null) {
            sql.append(" AND preco >= ?");
            params.add(precoMin);
        }
        if (precoMax != null) {
            sql.append(" AND preco <= ?");
            params.add(precoMax);
        }
        if (modelo != null) {
            sql.append(" AND modelo = ?");
            params.add(modelo);
        }
        if (dataFabMin != null) {
            sql.append(" AND data_fabricacao >= ?");
            params.add(dataFabMin);
        }
        if (dataFabMax != null) {
            sql.append(" AND data_fabricacao <= ?");
            params.add(dataFabMax);
        }

        Query q = em.createNativeQuery(sql.toString(), Caneta.class);

        for (int i = 0; i < params.size(); i++) {
            q.setParameter(i + 1, params.get(i));
        }

        return q.getResultList();
    }
}
