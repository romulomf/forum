package br.com.alura.forum.controller.dto;

import lombok.Getter;

@Getter
public class TokenDto {

	private String type;

	private String token;

	public TokenDto(String type, String token) {
		this.type = type;
		this.token = token;
	}
}