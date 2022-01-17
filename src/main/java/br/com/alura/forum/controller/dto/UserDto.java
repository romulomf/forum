package br.com.alura.forum.controller.dto;

import br.com.alura.forum.model.User;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("Usuário")
public class UserDto {

	private Long id;

	private String username;

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