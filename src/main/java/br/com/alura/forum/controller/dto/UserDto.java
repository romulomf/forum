package br.com.alura.forum.controller.dto;

import br.com.alura.forum.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Dados de autenticação do usuário")
public class UserDto {

	@Schema(description = "identificador exclusivo")
	private Long id;

	@Schema(description = "nome do usuário")
	private String username;

	@Schema(description = "senha de acesso")
	private String password;

	public UserDto(User user) {
		if (user == null) {
			throw new IllegalArgumentException("User cannot be null.");
		}
		this.id = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();
	}
}