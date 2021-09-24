package br.com.alura.forum.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.alura.forum.model.User;
import br.com.alura.forum.repository.UserRepository;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class ForumAuthenticationService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUsername(username);
		if (user.isPresent()) {
			return user.get();
		}
		throw new UsernameNotFoundException("credenciais não encontradas");
	}
}