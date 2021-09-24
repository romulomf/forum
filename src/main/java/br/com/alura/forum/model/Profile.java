package br.com.alura.forum.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

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