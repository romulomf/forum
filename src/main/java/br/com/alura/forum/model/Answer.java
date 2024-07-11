package br.com.alura.forum.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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