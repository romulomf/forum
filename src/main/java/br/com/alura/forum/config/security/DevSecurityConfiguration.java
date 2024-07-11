package br.com.alura.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.alura.forum.config.security.filter.AuthenticationTokenFilter;
import br.com.alura.forum.service.TokenService;

@Configuration
@EnableWebSecurity
@Profile("dev")
public class DevSecurityConfiguration {

	private final ForumAuthenticationService authenticationService;

	private final TokenService tokenService;

	public DevSecurityConfiguration(@Autowired ForumAuthenticationService authenticationService, @Autowired TokenService tokenService) {
		this.authenticationService = authenticationService;
		this.tokenService = tokenService;
	}

	@Bean
	AuthenticationManager authManager(HttpSecurity http, @Autowired PasswordEncoder passwordEncoder) throws Exception {
		/*
		 * Por alguma razão, é necessário criar esse produtor para poder criar
		 * uma instância deste tipo, pois o spring não faz isso por padrão. Para
		 * fazer isso, felizmente é tão simples como está aqui, basta sobrescrever
		 * esse método herdado e retornar a implementação da classe superior.
		 */
		final AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class); 
		authManagerBuilder.userDetailsService(authenticationService).passwordEncoder(passwordEncoder);
		return authManagerBuilder.build();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(CsrfConfigurer::disable)
				.cors(CorsConfigurer::disable)
				.authorizeHttpRequests(req -> req.requestMatchers("/**").permitAll())
				.sessionManagement(sessionMagementConfigurer -> sessionMagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(new AuthenticationTokenFilter(tokenService), UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		// realiza as configurações de recursos estáticos (assets)
		return web -> web
					.ignoring()
					.requestMatchers("/**.html", "/v3/api-docs/**", "/webjars/**", "/configuration/**")
					.requestMatchers("/swagger-resources/**", "/swagger-ui/**", "/swagger-ui.html")
					.requestMatchers("/h2-console/**");
	}
}