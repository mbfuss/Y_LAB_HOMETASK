package org.example.dto.mapper;

public class ResourceMapper {
    ResourceMapper INSTANCE = Mappers.getMapper(ResourceMapper.class);

    ResourceDTO toDTO(Resource resource);
    Resource toEntity(ResourceDTO resourceDTO);
}
