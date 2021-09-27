package br.com.alura.forum.config.security.filter;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.alura.forum.model.User;
import br.com.alura.forum.service.TokenService;

public class AuthenticationTokenFilter extends OncePerRequestFilter {

	private final TokenService tokenService;

	public AuthenticationTokenFilter(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		Optional<String> token = retrieveToken(request);
		if (token.isPresent() && tokenService.isValid(token.get())) {
			Optional<User> user = tokenService.getUser(token.get());
			user.ifPresent(u -> {
				Authentication auth = new UsernamePasswordAuthenticationToken(u, u.getPassword(), u.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
			});
		}
		filterChain.doFilter(request, response);
	}

	private Optional<String> retrieveToken(HttpServletRequest request) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION); 
		if (StringUtils.startsWith(token, "Bearer ")) {
			return Optional.of(StringUtils.substring(token, 7));
		}
		return Optional.empty();
	}
}