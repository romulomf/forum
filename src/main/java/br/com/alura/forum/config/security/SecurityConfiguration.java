package br.com.alura.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.NoArgsConstructor;

@Configuration
@EnableWebSecurity
@NoArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private ForumAuthenticationService authenticationService;

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		/*
		 * Por alguma razão, é necessário criar esse produtor para poder criar
		 * uma instância deste tipo, pois o spring não faz isso por padrão. Para
		 * fazer isso, felizmente é tão simples como está aqui, basta sobrescrever
		 * esse método herdado e retornar a implementação da classe superior.
		 */
		return super.authenticationManager();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Realiza as configurações de autenticação.
		auth.userDetailsService(authenticationService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Realiza as configurações de autorização
		http.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/user").permitAll()
			.antMatchers(HttpMethod.GET, "/topic").permitAll()
			.antMatchers(HttpMethod.GET, "/topic/*").permitAll()
			// Para quaisquer outras requisições, é necessário estar autenticado.
			.anyRequest().authenticated()
			.and().csrf().disable()
			// Determina que a autenticação vai ser stateless e não deve ser criado uma sessão no servidor.
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//			.and().formLogin();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// realiza as configurações de recursos estáticos (assets)
	}
}