INSERT INTO caneta (id, modelo, preco, data_fabricacao)
VALUES (1, 'BIC', 5.00, TO_DATE('2023-01-01', 'YYYY-MM-DD'));

INSERT INTO caneta (id, modelo, preco, data_fabricacao)
VALUES (2, 'Compactor', 7.50, SYSDATE); --substitui o now() CURRENT_TIMESTAMP para timestamp

UPDATE caneta
SET preco = 6.00
WHERE id = 1;

UPDATE caneta c
SET c.preco = c.preco * 1.10
WHERE EXISTS (
    SELECT 1
    FROM fabricante f
    WHERE c.fabricante_id = f.id
      AND f.nome = 'Faber-Castell'
);

MERGE INTO caneta c
    USING fabricante f
    ON (c.fabricante_id = f.id)
    WHEN MATCHED THEN
        UPDATE SET c.preco = c.preco * 1.10
    WHERE f.nome = 'Faber-Castell';