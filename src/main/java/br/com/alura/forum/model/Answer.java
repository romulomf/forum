package br.com.alura.forum.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa a resposta de um {@link Topic} do fórum.
 * 
 * @author Rômulo Machado Flores <romuloflores@gmail.com>
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "ANSWERS")
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "MESSAGE", nullable = false)
	private String menssage;

	@ManyToOne(targetEntity = Topic.class)
	@JoinColumn(name = "ID_TOPIC", referencedColumnName = "ID")
	private Topic topic;

	@Column(name = "CREATED")
	private LocalDateTime created;

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "ID_USER", referencedColumnName = "ID")
	private User author;

	@Column(name = "SOLUTION")
	private boolean solution;

	public Answer(String message, Topic topic, User author) {
		this();
		this.menssage = message;
		this.topic = topic;
		this.author = author;
		this.created = LocalDateTime.now();
		this.solution = false;
	}

	public Answer(Long id, String message, Topic topic, User author) {
		this(message, topic, author);
		this.id = id;
	}
}