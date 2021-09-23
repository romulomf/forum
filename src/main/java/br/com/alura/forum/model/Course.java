package br.com.alura.forum.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa um curso no fórum.
 * 
 * @author Rômulo Machado Flores <romuloflores@gmail.com>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "COURSES")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "CATEGORY")
	private String category;

	public Course(String name, String category) {
		this();
		this.name = name;
		this.category = category;
	}
}