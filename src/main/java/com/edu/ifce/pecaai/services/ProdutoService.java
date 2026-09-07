package com.edu.ifce.pecaai.services;

import com.edu.ifce.pecaai.dto.ProdutoRequestDTO;
import com.edu.ifce.pecaai.entities.Loja;
import com.edu.ifce.pecaai.entities.Produto;
import com.edu.ifce.pecaai.repositories.LojaRepository;
import com.edu.ifce.pecaai.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private LojaRepository lojaRepository; // Adicionado para buscar a loja

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public List<Produto> listarPorLoja(Long lojaId) {
        return produtoRepository.findByLojaId(lojaId);
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + id));
    }

    // Método atualizado para receber o DTO e vincular a loja manualmente
    public Produto salvar(ProdutoRequestDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco());

        // Busca a loja no banco de dados usando o ID que veio do Postman
        Loja loja = lojaRepository.findById(dto.lojaId())
                .orElseThrow(() -> new RuntimeException("Loja não encontrada com ID: " + dto.lojaId()));

        // Faz o vínculo que estava faltando
        produto.setLoja(loja);

        return produtoRepository.save(produto);
    }

    public void deletar(Long id) {
        buscarPorId(id);
        produtoRepository.deleteById(id);
    }
}