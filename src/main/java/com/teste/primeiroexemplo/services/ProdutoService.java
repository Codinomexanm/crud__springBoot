package com.teste.primeiroexemplo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teste.primeiroexemplo.models.Produto;
import com.teste.primeiroexemplo.models.exception.ResourceNotFoundException;
import com.teste.primeiroexemplo.respository.ProdutoRepository;
import com.teste.primeiroexemplo.shared.ProdutoDTO;

@Service
public class ProdutoService {
  @Autowired
  private ProdutoRepository produtoRepository;

  /**
   * Método para retorna uma lista de produtos
   * 
   * @return -lista de produtos
   */
  public List<ProdutoDTO> obterTodos() {
    List<Produto> produtos = produtoRepository.findAll();
    return produtos.stream()
        .map(produto -> new ModelMapper().map(produto, ProdutoDTO.class))
        .collect(Collectors.toList());
  }

  /**
   * Método que retorna o produto encontrado pelo seu ID.
   * 
   * @param id do produto que será localizado.
   * @return retorna um produto caso seja encontrado.
   * 
   */
  public Optional<ProdutoDTO> obterPorId(Integer id) {
    Optional<Produto> produto = produtoRepository.findById(id);
    if (produto.isEmpty()) {
      throw new ResourceNotFoundException("produto com id:" + id + " não encontrado ");
    }
    ProdutoDTO dto = new ModelMapper().map(produto.get(), ProdutoDTO.class);
    return Optional.of(dto);
  }

  /**
   * Método para adicionar produto na lista.
   * 
   * @param produto que será adicionado.
   * @return retorna o produto que foi adicionado a lista.
   */
  public ProdutoDTO adicionar(ProdutoDTO produtoDTO) {
    // remove o id para conseguir fazer o cadastro
    produtoDTO.setId(null);
    // criar um objeto de mappeamento
    ModelMapper mapper = new ModelMapper();
    // converter um produto dto em um produto
    Produto produto = mapper.map(produtoDTO, Produto.class);
    // salva no banco de dados
    produto = produtoRepository.save(produto);
    // retorna o produto que foi adicionado a lista
    produtoDTO.setId(produto.getId());
    return produtoDTO;
  }

  /**
   * Método para deletar o produto por id.
   * 
   * @param id que será deletado.
   */
  public void deletar(Integer id) {
    // verifica se o produto existe
    Optional<Produto> produto = produtoRepository.findById(id);
    // se não existir lança uma exceção
    if (produto.isEmpty()) {
      throw new ResourceNotFoundException("não foi possível obter o produto com o id: " + id + " produto não existe ");
    }
    produtoRepository.deleteById(id);
  }

  /**
   * Método para atualizar o produto na lista
   * 
   * @param produto que será atualizado
   * @return retorna o produto após atualizar a lista
   */
  public ProdutoDTO atualizar(Integer id, ProdutoDTO produtoDTO) {
    // passar o id para o produto DTo
    produtoDTO.setId(id);
    // criar objeto de mapeamento
    ModelMapper mapper = new ModelMapper();
    // converter em um produto DTO
    Produto produto = mapper.map(produtoDTO, Produto.class);
    // retornar o produto DTO atualizado
    produtoRepository.save(produto);
    return produtoDTO;
  }
}
