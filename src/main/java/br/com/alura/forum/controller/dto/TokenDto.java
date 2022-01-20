package br.com.alura.forum.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(name = "Token", description = "Token JWT de autenticação")
public class TokenDto {

	private String type;

	private String token;

	public TokenDto(String type, String token) {
		this.type = type;
		this.token = token;
	}
}