package br.com.alura.forum.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Representa um tópico no fórum.
 * 
 * @author Rômulo Machado Flores
 */
@Data
@AllArgsConstructor
@Entity
@Table(name = "TOPICS")
public class Topic {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "MESSAGE")
	private String message;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_COURSE", referencedColumnName = "ID")
	private Course course;

	@Column(name = "CREATED")
	private LocalDateTime created;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private TopicStatus status;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_AUTHOR", referencedColumnName = "ID")
	private User author;

	@OneToMany(mappedBy = "topic")
	private List<Answer> answers;

	public Topic() {
		this.status = TopicStatus.UNANSWERED;
		this.created = LocalDateTime.now();
	}

	public Topic(String title, String message, TopicStatus status, Course course, User author) {
		this();
		this.title = title;
		this.message = message;
		this.status = status;
		this.course = course;
		this.author = author;
	}
}