package org.serratec.backend.controller;

import java.util.List;
import java.util.Optional;

import org.serratec.backend.dto.DevolucaoRequestDTO;
import org.serratec.backend.dto.DevolucaoResponseDTO;
import org.serratec.backend.entity.Devolucao;
import org.serratec.backend.repository.DevolucaoRepository;
import org.serratec.backend.service.DevolucaoService;
import org.serratec.backend.service.FotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/devolucoes")
public class DevolucaoController {
	
	@Autowired
	private DevolucaoRepository devolucaoRepository;
	
	@Autowired
	private DevolucaoService devolucaoService;
	
	
	@GetMapping
	public ResponseEntity <List<Devolucao>> listarTodos() {
		List<Devolucao> devolucaos = devolucaoRepository.findAll();
		return ResponseEntity.ok(devolucaos);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Devolucao> listarPorId(@PathVariable Long id) {
		Optional<Devolucao> devolucao = devolucaoRepository.findById(id);
		return ResponseEntity.ok(devolucao.get());
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<Devolucao> atualizar(@PathVariable Long id, @RequestBody Devolucao devolucao) {
		return ResponseEntity.notFound().build();
}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		if (devolucaoRepository.existsById(id)) {
			devolucaoRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/cadastro")
	@ResponseStatus(HttpStatus.CREATED)
	public List<Devolucao> inserir(@RequestBody List<Devolucao> devolucao) {
		return devolucaoRepository.saveAll(devolucao);

	}
	
	
    @PostMapping("/solicitar")
    public ResponseEntity<?> solicitarNovaDevolucao(@Valid @RequestBody DevolucaoRequestDTO requestDTO) {
        try {
            DevolucaoResponseDTO responseDTO = devolucaoService.registrarNovaDevolucao(requestDTO);
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) { 
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) { 
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) { 
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao processar a solicitação de devolução.");
        }
    }
	
}
