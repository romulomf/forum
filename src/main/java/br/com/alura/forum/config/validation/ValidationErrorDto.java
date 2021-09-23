package br.com.alura.forum.config.validation;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class ValidationErrorDto {

	@JsonProperty("field")
	private String field;

	@JsonProperty("error")
	private String error;

	public ValidationErrorDto(String field, String error) {
		this.field = field;
		this.error = error;
	}
}