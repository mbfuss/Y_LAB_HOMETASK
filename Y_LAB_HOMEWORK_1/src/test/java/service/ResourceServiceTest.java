package service;

import org.example.model.*;
import org.example.service.ResourceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ResourceServiceTest {

    private ResourceService resourceService;

    @BeforeEach
    void setUp() {
        resourceService = new ResourceService();
    }

    @Test
    void addResource_ShouldAddNewResource() {
        resourceService.addResource("1", "Desk 1", false);
        Resource resource = resourceService.getResource("1");

        assertThat(resource).isNotNull();
        assertThat(resource.getName()).isEqualTo("Desk 1");
    }

    @Test
    void addResource_ShouldThrowException_WhenResourceIdAlreadyExists() {
        resourceService.addResource("1", "Desk 1", false);

        assertThatThrownBy(() -> resourceService.addResource("1", "Desk 2", false))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Resource with this ID already exists");
    }

    @Test
    void getResource_ShouldReturnResource_WhenIdIsValid() {
        resourceService.addResource("1", "Desk 1", false);
        Resource resource = resourceService.getResource("1");

        assertThat(resource).isNotNull();
        assertThat(resource.getName()).isEqualTo("Desk 1");
    }

    @Test
    void getResource_ShouldReturnNull_WhenIdIsInvalid() {
        Resource resource = resourceService.getResource("999");

        assertThat(resource).isNull();
    }
}
