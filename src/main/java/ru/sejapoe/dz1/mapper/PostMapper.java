package ru.sejapoe.dz1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.sejapoe.dz1.dto.post.CreatePostDto;
import ru.sejapoe.dz1.dto.post.PostDto;
import ru.sejapoe.dz1.model.Post;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostMapper {
    Post toEntity(CreatePostDto postDto);

    PostDto toDto(Post post);
}