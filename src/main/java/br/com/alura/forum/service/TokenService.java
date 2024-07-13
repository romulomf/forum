package br.com.alura.forum.service;

import java.security.KeyPair;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.alura.forum.model.User;
import br.com.alura.forum.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;

@Service
public class TokenService {

	private static final String ISSUER = "API do fórum";

	@Value("${jjwt.expiration}")
	private Long expiration;

	private final KeyPair key;

	private JwtParser tokenParser;

	private final UserRepository userRepository;

	public TokenService(@Autowired UserRepository userRepository) {
		this.userRepository = userRepository;
		key = Jwts.SIG.RS256.keyPair().build();
	}

	@PostConstruct
	public void configure() {
		tokenParser = Jwts.parser().requireIssuer(ISSUER).verifyWith(key.getPublic()).build();
	}

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

		return Jwts.builder()
			// serve para identificar qual é a aplicação que criou esse token
			.issuer(ISSUER)
			.issuedAt(issued)
			.subject(user.getId().toString())
			.expiration(active)
			.signWith(key.getPrivate())
			.compact();
	}

	public boolean isValid(String token) {
		try {
			tokenParser.parseSignedClaims(token);
			return true;
		}
		catch (SignatureException | ExpiredJwtException | MalformedJwtException | IllegalArgumentException e) {
			return false;
		}
	}

	public Optional<User> getUser(String token) {
		try {
			Jws<Claims> jsonWebTokenClaims = tokenParser.parseSignedClaims(token);
			if (jsonWebTokenClaims != null) {
				Claims claims = jsonWebTokenClaims.getPayload();
				Long id = Long.valueOf(claims.getSubject());
				return userRepository.findById(id);
			}
			return Optional.empty();
		}
		catch (SignatureException | ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
			return Optional.empty();
		}
	}
}