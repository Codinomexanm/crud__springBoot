package com.teste.primeiroexemplo.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teste.primeiroexemplo.models.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

}
