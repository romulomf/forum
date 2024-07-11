package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.TopicDto;
import br.com.alura.forum.model.Course;
import br.com.alura.forum.model.Topic;
import br.com.alura.forum.model.TopicStatus;
import br.com.alura.forum.model.User;
import br.com.alura.forum.repository.CourseRepository;
import br.com.alura.forum.repository.TopicRepository;
import br.com.alura.forum.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/topic")
@Schema(name = "Tópicos")
public class TopicController {

	private final CourseRepository courseRepository;

	private final UserRepository userRepository;

	private final TopicRepository topicRepository;

	public TopicController(@Autowired CourseRepository courseRepository, @Autowired UserRepository userRepository, @Autowired TopicRepository topicRepository) {
		this.courseRepository = courseRepository;
		this.userRepository = userRepository;
		this.topicRepository = topicRepository;
	}

	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "topicListCache", allEntries = true)
	@Operation(summary = "Remover", description = "Remover um tópico")
	public ResponseEntity<Void> drop(@Parameter(name = "id", description = "identificador único") @PathVariable("id") Long id) {
		topicRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "topicListCache", allEntries = true)
	@Operation(summary = "Alterar", description = "Alterar um tópico")
	public ResponseEntity<TopicDto> edit(@Parameter(name = "id", description = "identificador único") @PathVariable("id") Long id, @RequestBody @Valid TopicDto dto, UriComponentsBuilder uriBuilder) {
		Optional<Topic> entity = topicRepository.findById(id);
		if (entity.isPresent()) {
			Topic topic = entity.get();
			topic.setTitle(dto.getTitle());
			topic.setMessage(dto.getMessage());
			topic.setStatus(Enum.valueOf(TopicStatus.class, dto.getStatus()));
			Optional<Course> course = courseRepository.findById(dto.getCourseId());
			Optional<User> user = userRepository.findById(dto.getAuthorId());
			if (!course.isPresent() || !user.isPresent()) {
				return ResponseEntity.badRequest().build();
			}
			topic.setCourse(course.get());
			topic.setAuthor(user.get());
			return ResponseEntity.ok(new TopicDto(topic));
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Buscar", description = "Buscar um tópico")
	public ResponseEntity<TopicDto> find(@Parameter(name = "id", description = "identificador único") @PathVariable("id") Long id) {
		Optional<Topic> entity = topicRepository.findById(id);
		if (entity.isPresent()) {
			return ResponseEntity.ok(new TopicDto(entity.get()));
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping
	@Cacheable("topicListCache")
	@Operation(summary = "Listar", description = "Listar todos os tópicos")
	public List<TopicDto> list(@Parameter(hidden = true) @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable) {
		Page<Topic> topics = topicRepository.findAll(pageable);
		return topics.stream().map(TopicDto::new).toList();
	}

	@PostMapping
	@Transactional
	@CacheEvict(value = "topicListCache", allEntries = true)
	@Operation(summary = "Adicionar", description = "Adicionar um tópico")
	public ResponseEntity<TopicDto> save(@Parameter(name = "dto", description = "Token JWT") @RequestBody @Valid TopicDto dto, UriComponentsBuilder uriBuilder) {
		Optional<Course> course = courseRepository.findById(dto.getCourseId());
		Optional<User> user = userRepository.findById(dto.getAuthorId());
		if (!course.isPresent() || !user.isPresent()) {
			return ResponseEntity.badRequest().build();
		}
		Topic entity = dto.toEntity(course.get(), user.get());
		topicRepository.save(entity);
		URI resource = uriBuilder.path("/topic/{id}").buildAndExpand(entity.getId()).toUri();
		return ResponseEntity.created(resource).body(new TopicDto(entity));
	}
}