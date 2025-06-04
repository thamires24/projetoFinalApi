package org.serratec.backend.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException; // Para erros de integridade do banco
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<String> erros = ex.getBindingResult().getFieldErrors().stream()
				.map(erro -> erro.getField() + ": " + erro.getDefaultMessage()).collect(Collectors.toList());

		ErroResposta erroResposta = new ErroResposta(status.value(),
				"Erro de validação. Verifique os campos da requisição.", LocalDateTime.now(), erros);

		return super.handleExceptionInternal(ex, erroResposta, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		String mensagemDetalhada = ex.getLocalizedMessage();
		if (ex.getCause() instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException ife) {
			if (ife.getTargetType() != null && ife.getTargetType().isEnum()) {
				mensagemDetalhada = String.format(
						"Valor '%s' inválido para o campo '%s'. Valores esperados para o tipo %s: %s.", ife.getValue(),
						ife.getPath().get(ife.getPath().size() - 1).getFieldName(), ife.getTargetType().getSimpleName(),
						java.util.Arrays.toString(ife.getTargetType().getEnumConstants()));
			}
		}

		ErroResposta erroResposta = new ErroResposta(status.value(),
				"Erro ao ler a requisição. Verifique o formato do JSON enviado.", LocalDateTime.now(),
				List.of(mensagemDetalhada));
		return super.handleExceptionInternal(ex, erroResposta, headers, status, request);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		ErroResposta erroResposta = new ErroResposta(HttpStatus.NOT_FOUND.value(), "Recurso Não Encontrado",
				LocalDateTime.now(), List.of(ex.getMessage()));
		return new ResponseEntity<>(erroResposta, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
		ErroResposta erroResposta = new ErroResposta(HttpStatus.NOT_FOUND.value(),
				"Recurso Não Encontrado no Banco de Dados", LocalDateTime.now(), List.of(ex.getMessage()));
		return new ResponseEntity<>(erroResposta, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
			WebRequest request) {
		String mensagem = "Erro de integridade dos dados. Pode ser um valor duplicado ou uma referência inválida.";
		if (ex.getMostSpecificCause() != null) {
			mensagem = ex.getMostSpecificCause().getMessage();
		}
		ErroResposta erroResposta = new ErroResposta(HttpStatus.CONFLICT.value(), // 409 Conflict é apropriado
				"Conflito de Dados", LocalDateTime.now(), List.of(mensagem));
		return new ResponseEntity<>(erroResposta, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(ClienteException.class)
	public ResponseEntity<Object> handleClienteException(ClienteException ex, WebRequest request) {
		ErroResposta erroResposta = new ErroResposta(HttpStatus.BAD_REQUEST.value(), "Erro na Operação com Cliente",
				LocalDateTime.now(), List.of(ex.getMessage()));
		return new ResponseEntity<>(erroResposta, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ProdutoException.class)
	public ResponseEntity<Object> handleProdutoException(ProdutoException ex, WebRequest request) {
		ErroResposta erroResposta = new ErroResposta(HttpStatus.BAD_REQUEST.value(), "Erro na Operação com Produto",
				LocalDateTime.now(), List.of(ex.getMessage()));
		return new ResponseEntity<>(erroResposta, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CategoriaException.class)
	public ResponseEntity<Object> handleCategoriaException(CategoriaException ex, WebRequest request) {
		ErroResposta erroResposta = new ErroResposta(HttpStatus.BAD_REQUEST.value(), "Erro na Operação com Categoria",
				LocalDateTime.now(), List.of(ex.getMessage()));
		return new ResponseEntity<>(erroResposta, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(PedidoException.class)
	public ResponseEntity<Object> handlePedidoException(PedidoException ex, WebRequest request) {
		ErroResposta erroResposta = new ErroResposta(HttpStatus.BAD_REQUEST.value(), "Erro na Operação com Pedido",
				LocalDateTime.now(), List.of(ex.getMessage()));
		return new ResponseEntity<>(erroResposta, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EnderecoException.class)
	public ResponseEntity<Object> handleEnderecoException(EnderecoException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		if (ex.getMessage() != null && ex.getMessage().toLowerCase().contains("não encontrado")) {
			status = HttpStatus.NOT_FOUND;
		}
		ErroResposta erroResposta = new ErroResposta(status.value(), "Erro na Operação com Endereço",
				LocalDateTime.now(), List.of(ex.getMessage()));
		return new ResponseEntity<>(erroResposta, status);
	}

	@ExceptionHandler(FotoException.class)
	public ResponseEntity<Object> handleFotoException(FotoException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		if (ex.getMessage() != null && ex.getMessage().toLowerCase().contains("não encontrada")) {
			status = HttpStatus.NOT_FOUND;
		}
		ErroResposta erroResposta = new ErroResposta(status.value(), "Erro na Operação com Foto", LocalDateTime.now(),
				List.of(ex.getMessage()));
		return new ResponseEntity<>(erroResposta, status);
	}

	@ExceptionHandler(EnumException.class)
	public ResponseEntity<Object> handleEnumException(EnumException ex, WebRequest request) {
		ErroResposta erroResposta = new ErroResposta(HttpStatus.BAD_REQUEST.value(), "Valor de Enum Inválido",
				LocalDateTime.now(), List.of(ex.getMessage()));
		return new ResponseEntity<>(erroResposta, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DevolucaoException.class)
	public ResponseEntity<Object> handleDevolucaoException(DevolucaoException ex, WebRequest request) {
		ErroResposta erroResposta = new ErroResposta(HttpStatus.BAD_REQUEST.value(), "Erro na Operação com Devolução",
				LocalDateTime.now(), List.of(ex.getMessage()));
		return new ResponseEntity<>(erroResposta, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(WishlistException.class)
	public ResponseEntity<Object> handleWishlistException(WishlistException ex, WebRequest request) {
		ErroResposta erroResposta = new ErroResposta(HttpStatus.BAD_REQUEST.value(), "Erro na Operação com Wishlist",
				LocalDateTime.now(), List.of(ex.getMessage()));
		return new ResponseEntity<>(erroResposta, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
		ex.printStackTrace();

		ErroResposta erroResposta = new ErroResposta(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"Ocorreu um erro inesperado no servidor.", LocalDateTime.now(),
				List.of("Por favor, tente novamente mais tarde ou contate o suporte."));
		return new ResponseEntity<>(erroResposta, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}