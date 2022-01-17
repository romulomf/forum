package br.com.alura.forum.controller.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

@Getter
@ApiModel("Token")
public class TokenDto {

	private String type;

	private String token;

	public TokenDto(String type, String token) {
		this.type = type;
		this.token = token;
	}
}