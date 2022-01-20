package br.com.alura.forum.config.validation;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(name = "Validação", description = "Descritor dos erros de validação")
public class ValidationErrorDto {

	@JsonProperty("field")
	@Schema(name = "atributo", description = "propriedade que contém erro")
	private String field;

	@JsonProperty("error")
	@Schema(name = "mensagem", description = "mensagem descritiva do erro")
	private String error;

	public ValidationErrorDto(String field, String error) {
		this.field = field;
		this.error = error;
	}
}