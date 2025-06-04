package org.serratec.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.backend.config.MailConfig;
import org.serratec.backend.dto.ClienteRequestDTO;
import org.serratec.backend.dto.ClienteResponseDTO;
import org.serratec.backend.dto.EnderecoRequestDTO;
import org.serratec.backend.entity.Cliente;
import org.serratec.backend.entity.ClientePerfil;
import org.serratec.backend.entity.Endereco;
import org.serratec.backend.exception.ClienteException;
import org.serratec.backend.exception.EnderecoException;
import org.serratec.backend.repository.ClientePerfilRepository;
import org.serratec.backend.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoService enderecoService;

	@Autowired
	private ClientePerfilRepository clientePerfilRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private MailConfig mailConfig;

	private static final Long ID_PERFIL_CLIENTE_PADRAO = 1L;

	@Transactional(readOnly = true)
	public List<ClienteResponseDTO> listarTodos() {
		return clienteRepository.findAll().stream().map(ClienteResponseDTO::new).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public ClienteResponseDTO buscarPorId(Long id) throws ClienteException {
		Cliente cliente = clienteRepository.findById(id)
				.orElseThrow(() -> new ClienteException("Cliente com ID " + id + " não encontrado."));
		return new ClienteResponseDTO(cliente);
	}

	@Transactional
	public ClienteResponseDTO salvar(ClienteRequestDTO clienteRequestDTO) throws ClienteException, EnderecoException {
		if (clienteRepository.findByCpf(clienteRequestDTO.getCpf()).isPresent()) {
			throw new ClienteException("CPF " + clienteRequestDTO.getCpf() + " já cadastrado.");
		}
		if (clienteRepository.findByEmail(clienteRequestDTO.getEmail()).isPresent()) {
			throw new ClienteException("Email " + clienteRequestDTO.getEmail() + " já cadastrado.");
		}

		Cliente cliente = new Cliente();
		cliente.setNome(clienteRequestDTO.getNome());
		cliente.setEmail(clienteRequestDTO.getEmail());
		cliente.setCpf(clienteRequestDTO.getCpf());
		cliente.setTelefone(clienteRequestDTO.getTelefone());
		cliente.setSenha(passwordEncoder.encode(clienteRequestDTO.getSenha()));

		EnderecoRequestDTO enderecoDto = clienteRequestDTO.getEndereco();
		if (enderecoDto != null) {
			Endereco endereco = enderecoService.obterOuCriarEnderecoPorCep(enderecoDto.getCep(),
					enderecoDto.getNumero(), enderecoDto.getComplemento());
			cliente.setEndereco(endereco);
		} else {
			throw new ClienteException("Dados de endereço são obrigatórios para o cadastro.");
		}

		Long idPerfil = clienteRequestDTO.getIdClientePerfil() != null ? clienteRequestDTO.getIdClientePerfil()
				: ID_PERFIL_CLIENTE_PADRAO;
		ClientePerfil perfil = clientePerfilRepository.findById(idPerfil)
				.orElseThrow(() -> new ClienteException("Perfil de cliente com ID " + idPerfil + " não encontrado."));
		cliente.setClientePerfil(perfil);

		Cliente clienteSalvo = clienteRepository.save(cliente);

		try {
			String mensagemEmail = "Olá " + clienteSalvo.getNome()
					+ ",\n\nSeu cadastro foi realizado com sucesso em nossa plataforma!\n\n" + "Login: "
					+ clienteSalvo.getEmail() + "\n\nAtenciosamente,\nEquipe ECommerce";
			mailConfig.enviarEmail(clienteSalvo.getEmail(), "Cadastro Realizado - ECommerce", mensagemEmail);
		} catch (Exception e) {
			System.err.println("Erro ao enviar email de confirmação de cadastro: " + e.getMessage());
		}

		return new ClienteResponseDTO(clienteSalvo);
	}

	@Transactional
	public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO clienteRequestDTO)
			throws ClienteException, EnderecoException {
		Cliente clienteExistente = clienteRepository.findById(id)
				.orElseThrow(() -> new ClienteException("Cliente com ID " + id + " não encontrado para atualização."));

		if (!clienteExistente.getCpf().equals(clienteRequestDTO.getCpf())) {
			if (clienteRepository.findByCpf(clienteRequestDTO.getCpf()).filter(c -> !c.getId().equals(id))
					.isPresent()) {
				throw new ClienteException("CPF " + clienteRequestDTO.getCpf() + " já cadastrado para outro cliente.");
			}
		}
		if (!clienteExistente.getEmail().equals(clienteRequestDTO.getEmail())) {
			if (clienteRepository.findByEmail(clienteRequestDTO.getEmail()).filter(c -> !c.getId().equals(id))
					.isPresent()) {
				throw new ClienteException(
						"Email " + clienteRequestDTO.getEmail() + " já cadastrado para outro cliente.");
			}
		}

		clienteExistente.setNome(clienteRequestDTO.getNome());
		clienteExistente.setEmail(clienteRequestDTO.getEmail());
		clienteExistente.setCpf(clienteRequestDTO.getCpf());
		clienteExistente.setTelefone(clienteRequestDTO.getTelefone());

		if (clienteRequestDTO.getSenha() != null && !clienteRequestDTO.getSenha().isBlank()) {
			clienteExistente.setSenha(passwordEncoder.encode(clienteRequestDTO.getSenha()));
		}

		EnderecoRequestDTO enderecoDto = clienteRequestDTO.getEndereco();
		if (enderecoDto != null) {
			Endereco endereco = enderecoService.obterOuCriarEnderecoPorCep(enderecoDto.getCep(),
					enderecoDto.getNumero(), enderecoDto.getComplemento());
			clienteExistente.setEndereco(endereco);
		}

		if (clienteRequestDTO.getIdClientePerfil() != null && (clienteExistente.getClientePerfil() == null
				|| !clienteExistente.getClientePerfil().getId().equals(clienteRequestDTO.getIdClientePerfil()))) {
			ClientePerfil novoPerfil = clientePerfilRepository.findById(clienteRequestDTO.getIdClientePerfil())
					.orElseThrow(() -> new ClienteException(
							"Perfil de cliente com ID " + clienteRequestDTO.getIdClientePerfil() + " não encontrado."));
			clienteExistente.setClientePerfil(novoPerfil);
		}

		Cliente clienteAtualizado = clienteRepository.save(clienteExistente);

		try {
			String mensagemEmail = "Olá " + clienteAtualizado.getNome()
					+ ",\n\nSeus dados cadastrais foram atualizados em nossa plataforma.\n\n"
					+ "Atenciosamente,\nEquipe ECommerce";
			mailConfig.enviarEmail(clienteAtualizado.getEmail(), "Dados Cadastrais Atualizados - ECommerce", mensagemEmail);
		} catch (Exception e) {
			System.err.println("Erro ao enviar email de confirmação de atualização: " + e.getMessage());
		}
		return new ClienteResponseDTO(clienteAtualizado);
	}

	@Transactional
	public void deletar(Long id) throws ClienteException {
		if (!clienteRepository.existsById(id)) {
			throw new ClienteException("Cliente com ID " + id + " não encontrado para exclusão.");
		}
		clienteRepository.deleteById(id);
	}
}