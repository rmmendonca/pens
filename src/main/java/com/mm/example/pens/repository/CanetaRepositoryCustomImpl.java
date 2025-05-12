package com.mm.example.pens.repository;

import com.mm.example.pens.model.Caneta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CanetaRepositoryCustomImpl implements CanetaRepositoryCustom{

    private final DataSource dataSource;
    @PersistenceContext
    private EntityManager em;

    public CanetaRepositoryCustomImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

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


    @Override
    public List<Caneta> filtrarJDBC(Double precoMin, Double precoMax, String modelo, LocalDate dataFabMin, LocalDate dataFabMax) {
        List<Caneta> resultado = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM MM.CANETA2 WHERE 1=1");
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
            params.add(java.sql.Date.valueOf(dataFabMin));
        }
        if (dataFabMax != null) {
            sql.append(" AND data_fabricacao <= ?");
            params.add(java.sql.Date.valueOf(dataFabMax));
        }

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            //set parametros
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            //preenchendo objetos para retorno
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Caneta caneta = new Caneta();
                    caneta.setID(rs.getLong("id"));
                    caneta.setModelo(rs.getString("modelo"));
                    caneta.setPreco(rs.getBigDecimal("preco"));
                    caneta.setQuantidade(rs.getInt("quantidade"));
                    java.sql.Date dataFab = rs.getDate("data_fabricacao");
                    if (dataFab != null) {
                        caneta.setDataProducao(dataFab.toLocalDate());
                    }
                    resultado.add(caneta);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao executar consulta JDBC", e);
        }

        return resultado;

    }
}