package repository;

import org.example.model.Resource;
import org.example.repository.ResourceRepository;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DisplayName("Тесты для ResourceRepository")
class ResourceRepositoryTest {

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13.1")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    private ResourceRepository resourceRepository;

    @BeforeEach
    @DisplayName("Подготовка окружения")
    void setUp() {
        String url = postgreSQLContainer.getJdbcUrl();
        String username = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();

        resourceRepository = new ResourceRepository(url, username, password);

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE SEQUENCE resource_seq START WITH 1 INCREMENT BY 1");
            statement.execute("CREATE TABLE resources (id BIGINT PRIMARY KEY, name VARCHAR(255), is_conference_room BOOLEAN)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    @DisplayName("Очистка окружения")
    void tearDown() {
        try (Connection connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword());
             Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE resources");
            statement.execute("DROP SEQUENCE resource_seq");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Сохранение и поиск по ID")
    void testSaveAndFindById() {
        Resource resource = new Resource();
        resource.setName("Conference Room A");
        resource.setConferenceRoom(true);

        resourceRepository.save(resource);

        Resource savedResource = resourceRepository.findById(1L);
        assertThat(savedResource).isNotNull();
        assertThat(savedResource.getName()).isEqualTo("Conference Room A");
        assertThat(savedResource.isConferenceRoom()).isTrue();
    }

    @Test
    @DisplayName("Поиск всех записей")
    void testFindAll() {
        Resource resource1 = new Resource();
        resource1.setName("Conference Room A");
        resource1.setConferenceRoom(true);

        Resource resource2 = new Resource();
        resource2.setName("Conference Room B");
        resource2.setConferenceRoom(false);

        resourceRepository.save(resource1);
        resourceRepository.save(resource2);

        List<Resource> resources = resourceRepository.findAll();
        assertThat(resources).hasSize(2)
                .extracting(Resource::getName)
                .containsExactlyInAnyOrder("Conference Room A", "Conference Room B");
    }

    @Test
    @DisplayName("Удаление всех записей")
    void testDeleteAll() {
        Resource resource = new Resource();
        resource.setName("Conference Room A");
        resource.setConferenceRoom(true);

        resourceRepository.save(resource);
        resourceRepository.deleteAll();

        List<Resource> resources = resourceRepository.findAll();
        assertThat(resources).isEmpty();
    }
}