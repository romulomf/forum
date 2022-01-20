package br.com.alura.forum.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import br.com.alura.forum.model.Course;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
class CourseRepositoryTest {

	@Autowired
	private CourseRepository repository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	@DisplayName("encontra um curso existente pelo nome")
	void findByNameTest() {
		entityManager.persist(new Course("Node", "Backend"));

		final String courseName = "Node";
		List<Course> courses = repository.findByName(courseName);
		Assertions.assertNotNull(courses);
		Optional<Course> course = courses.stream().findFirst();
		Assertions.assertTrue(course.isPresent());
		Assertions.assertEquals(courseName, course.get().getName());
	}

	@Test
	@DisplayName("procura um curso inexistente pelo nome e não deve encontrar")
	void findByNameNotFoundTest() {
		final String courseName = "HTML 5";
		List<Course> courses = repository.findByName(courseName);
		Assertions.assertTrue(courses.isEmpty());
	}
}