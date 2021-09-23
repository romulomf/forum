package br.com.alura.forum.controller.dto;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.OptionalLong;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.alura.forum.model.Course;
import br.com.alura.forum.model.Topic;
import br.com.alura.forum.model.TopicStatus;
import br.com.alura.forum.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Representa um DTO de {@link Topic}
 * 
 * @author Rômulo Machado Flores
 *
 */
@Data
@AllArgsConstructor
public class TopicDto {

	@JsonProperty("id")
	private Long id;

	@NotEmpty
	@JsonProperty("title")
	private String title;

	@Length(min = 3)
	@JsonProperty("message")
	private String message;

	@NotNull
	@JsonProperty("courseId")
	private Long courseId;

	@NotNull
	@JsonProperty("status")
	private String status;

	@NotNull
	@JsonProperty("authorId")
	private Long authorId;

	@JsonProperty("created")
	private LocalDateTime created;

	private TopicDto() {
		this.status = TopicStatus.UNANSWERED.name();
		this.created = LocalDateTime.now();
	}

	private TopicDto(String title, String message) {
		this();
		this.title = title;
		this.message = message;
	}

	private TopicDto(String title, String message, String status) {
		this(title, message);
		this.status = status;
	}

	private TopicDto(String title, String message, LocalDateTime created) {
		this(title, message);
		this.created = created;
	}

	public TopicDto(String title, String message, String status, LocalDateTime created) {
		this(title, message, status);
		this.created = created;
	}

	public TopicDto(String title, String message, String status, Long courseId, Long authorId) {
		this(title, message, status);
		this.courseId = courseId;
		this.authorId = authorId;
	}

	public TopicDto(String title, String message, LocalDateTime created, Long courseId, Long authorId) {
		this(title, message, created);
		this.courseId = courseId;
		this.authorId = authorId;
	}

	public TopicDto(String title, String message, String status, LocalDateTime created, Long courseId, Long authorId) {
		this(title, message, status, courseId, authorId);
		this.created = created;
	}

	public TopicDto(Topic entity) {
		this(entity.getTitle(), entity.getMessage(), entity.getCreated());
		Optional<Course> course = Optional.of(entity.getCourse());
		Optional<User> author = Optional.of(entity.getAuthor());
		this.courseId = course.map(Course::getId).orElse(null);
		this.authorId = author.map(User::getId).orElse(null);
		OptionalLong.of(entity.getId()).ifPresent(n -> this.id = n);
		this.status = Optional.ofNullable(entity.getStatus()).map(TopicStatus::name).orElse(null);
	}

	public Topic toEntity(Course course, User author) {
		TopicStatus topicStatus = Enum.valueOf(TopicStatus.class, this.status);
		return new Topic(this.title, this.message, topicStatus, course, author);
	}
}