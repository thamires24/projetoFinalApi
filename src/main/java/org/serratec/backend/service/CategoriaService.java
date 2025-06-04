package org.serratec.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.backend.dto.CategoriaRequestDTO;
import org.serratec.backend.dto.CategoriaResponseDTO;
import org.serratec.backend.entity.Categoria;
import org.serratec.backend.exception.CategoriaException;
import org.serratec.backend.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listarTodas() {
        return categoriaRepository.findAll().stream()
                .map(CategoriaResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoriaResponseDTO buscarPorId(Long id) throws CategoriaException {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaException("Categoria com ID " + id + " não encontrada."));
        return new CategoriaResponseDTO(categoria);
    }

    @Transactional
    public CategoriaResponseDTO salvar(CategoriaRequestDTO categoriaRequestDTO) throws CategoriaException {
        Categoria categoria = new Categoria();
        categoria.setNomeCategoria(categoriaRequestDTO.getNomeCategoria());
        
        Categoria categoriaSalva = categoriaRepository.save(categoria);
        return new CategoriaResponseDTO(categoriaSalva);
    }

    @Transactional
    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO categoriaRequestDTO) throws CategoriaException {
        Categoria categoriaExistente = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaException("Categoria com ID " + id + " não encontrada para atualização."));


        categoriaExistente.setNomeCategoria(categoriaRequestDTO.getNomeCategoria());
        Categoria categoriaAtualizada = categoriaRepository.save(categoriaExistente);
        return new CategoriaResponseDTO(categoriaAtualizada);
    }

    @Transactional
    public void deletar(Long id) throws CategoriaException {
        if (!categoriaRepository.existsById(id)) {
            throw new CategoriaException("Categoria com ID " + id + " não encontrada para exclusão.");
        }
        categoriaRepository.deleteById(id);
    }
}