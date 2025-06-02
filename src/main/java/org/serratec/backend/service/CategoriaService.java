package org.serratec.backend.service;

import java.util.List;
import java.util.Optional;

import org.serratec.backend.entity.Categoria;
import org.serratec.backend.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    public Categoria salvar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria atualizar(Long id, Categoria categoriaAtualizada) throws Exception {
        Optional<Categoria> categoriaExistente = categoriaRepository.findById(id);
        if (!categoriaExistente.isPresent()) {
            throw new Exception("Categoria não encontrada com id: " + id);
        }
        Categoria categoria = categoriaExistente.get();
        categoria.setNomeCategoria(categoriaAtualizada.getNomeCategoria());
        return categoriaRepository.save(categoria);
    }

    public void deletar(Long id) throws Exception {
        Optional<Categoria> categoriaExistente = categoriaRepository.findById(id);
        if (!categoriaExistente.isPresent()) {
            throw new Exception("Categoria não encontrada com id: " + id);
        }
        categoriaRepository.deleteById(id);
    }
}