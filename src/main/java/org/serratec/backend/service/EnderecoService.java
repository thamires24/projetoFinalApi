package org.serratec.backend.service;

import java.util.Objects;
import java.util.Optional;

import org.serratec.backend.dto.EnderecoResponseDTO;
import org.serratec.backend.entity.Endereco;
import org.serratec.backend.exception.EnderecoException;
import org.serratec.backend.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    @Transactional
    public Endereco obterOuCriarEnderecoPorCep(String cepOriginal, String numero, String complemento) throws EnderecoException {
        String cepTratado = cepOriginal.replaceAll("[^0-9]", "");
        if (cepTratado.length() != 8) {
            throw new EnderecoException("Formato de CEP inválido: " + cepOriginal + ". Deve conter 8 dígitos.");
        }

        Optional<Endereco> enderecoExistente = Optional.ofNullable(enderecoRepository.findByCep(cepTratado));
        
        if (enderecoExistente.isPresent() &&
            Objects.equals(enderecoExistente.get().getNumero(), numero) &&
            Objects.equals(enderecoExistente.get().getComplemento(), complemento)) {
            return enderecoExistente.get();
        }

        String url = "https://viacep.com.br/ws/" + cepTratado + "/json/";
        Endereco enderecoViaCep;
        try {
            enderecoViaCep = restTemplate.getForObject(url, Endereco.class);
        } catch (HttpClientErrorException e) {
            throw new EnderecoException("CEP " + cepTratado + " não encontrado no serviço ViaCEP. Status: " + e.getStatusCode());
        } catch (RestClientException e) {
            throw new EnderecoException("Erro ao comunicar com o serviço ViaCEP: " + e.getMessage());
        }

        if (enderecoViaCep == null || enderecoViaCep.getCep() == null) {
            throw new EnderecoException("CEP " + cepTratado + " não encontrado (ViaCEP retornou dados inválidos ou erro).");
        }
        
        enderecoViaCep.setCep(enderecoViaCep.getCep().replaceAll("-", ""));
        
        enderecoViaCep.setNumero(numero);
        enderecoViaCep.setComplemento(complemento);
        enderecoViaCep.setId(null);

        return enderecoRepository.save(enderecoViaCep);
    }


    @Transactional(readOnly = true)
    public EnderecoResponseDTO buscarDtoPorCep(String cep) throws EnderecoException {
        String cepTratado = cep.replaceAll("[^0-9]", "");
         if (cepTratado.length() != 8) {
            throw new EnderecoException("Formato de CEP inválido: " + cep + ". Deve conter 8 dígitos.");
        }
        Endereco endereco = enderecoRepository.findByCep(cepTratado);
        if (endereco == null) {
            throw new EnderecoException("Endereço não encontrado para o CEP: " + cepTratado);
        }
        return new EnderecoResponseDTO(endereco);
    }
}