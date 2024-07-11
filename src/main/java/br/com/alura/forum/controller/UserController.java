package br.com.alura.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.controller.dto.TokenDto;
import br.com.alura.forum.controller.dto.UserDto;
import br.com.alura.forum.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@Profile(value = {"prd", "test"})
@Schema(name = "Usuários")
public class UserController {

	private AuthenticationManager authenticationManager;

	private TokenService tokenService;

	public UserController(@Autowired AuthenticationManager authenticationManager, @Autowired TokenService tokenService) {
		this.authenticationManager = authenticationManager;
		this.tokenService = tokenService;
	}

	@PostMapping
	@Operation(summary = "Autenticar", description = "Autenticar um usuário")
	public ResponseEntity<TokenDto> auth(@RequestBody @Valid UserDto dto) {
		Authentication auth = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
		try {
			auth = authenticationManager.authenticate(auth);
			String token = tokenService.generate(auth);
			return ResponseEntity.ok(new TokenDto("Bearer", token));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
}