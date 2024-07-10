package org.example.mapper;

import org.example.dto.ResourceDTO;
import org.example.model.Resource;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ResourceMapper {
    ResourceMapper INSTANCE = Mappers.getMapper(ResourceMapper.class);

    Resource toEntity(ResourceDTO resourceDTO);

    ResourceDTO toDTO(Resource resource);
}
