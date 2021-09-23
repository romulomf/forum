package br.com.alura.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.forum.model.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {

	List<Topic> findByTitle(String title);

	List<Topic> findByCourseName(String name);
}