package br.com.alura.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.NoArgsConstructor;

@Configuration
@EnableWebSecurity
@NoArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private ForumAuthenticationService authenticationService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// realiza as configurações de autenticação
		auth.userDetailsService(authenticationService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// realiza as configurações de autorização
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/topic").permitAll()
			.antMatchers(HttpMethod.GET, "/topic/*").permitAll()
			// para quaisquer outras requisições, é necessário estar autenticado
			.anyRequest().authenticated()
			.and().formLogin();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// realiza as configurações de recursos estáticos (assets)
	}
}