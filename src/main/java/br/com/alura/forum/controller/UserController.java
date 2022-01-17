package br.com.alura.forum.controller;

import javax.validation.Valid;

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
import io.swagger.annotations.Api;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/user")
@Profile("prd")
@NoArgsConstructor
@Api(tags = "Usuários")
public class UserController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenService tokenService;

	@PostMapping
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