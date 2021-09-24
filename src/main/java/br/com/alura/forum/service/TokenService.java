package br.com.alura.forum.service;

import java.security.KeyPair;
import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.alura.forum.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class TokenService {

	@Value("${jjwt.expiration}")
	private Long expiration;

	public String generate(Authentication authentication) {
		/*
		 * O principal é o objeto que gerencia as credenciais de autenticação.
		 * Neste caso, sabemos que ele é um User (e é feito um cast explícito)
		 * para este tipo, pois na configuração do spring, determinamos que é
		 * ele quem representa um usuário na aplicação.
		 * 
		 * Como o framework não tem como determinar em tempo de compilação, o
		 * objeto que configuramos para representar um usuário, o método tem
		 * como retorno um Object e por isso a necesside que façamos a conversão
		 * explícita para o objeto de domínio que representa o usuário.
		 */
		User user = (User) authentication.getPrincipal();

		Date issued = Date.from(Instant.now());
		Date active = new Date(issued.getTime() + expiration);
		
		KeyPair pair = Keys.keyPairFor(SignatureAlgorithm.RS256);

		return Jwts.builder()
			// serve para identificar qual é a aplicação que criou esse token
			.setIssuer("API do fórum")
			.setSubject(user.getId().toString())
			.setIssuedAt(issued)
			.setExpiration(active)
			.signWith(pair.getPrivate())
			.compact();
	}
}