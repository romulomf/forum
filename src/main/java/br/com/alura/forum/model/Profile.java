package br.com.alura.forum.model;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "PROFILES")
@Data
@EqualsAndHashCode
public class Profile implements GrantedAuthority {

	private static final long serialVersionUID = 3300662874475724917L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME", nullable = false, unique = true)
	private String name;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "profiles")
	private List<User> users;

	@Override
	public String getAuthority() {
		return name;
	}
}