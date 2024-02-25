package ru.sejapoe.dz1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.sejapoe.dz1.dao.PostDao;
import ru.sejapoe.dz1.dto.post.CreatePostDto;
import ru.sejapoe.dz1.dto.post.PostDto;
import ru.sejapoe.dz1.mapper.PostMapper;
import ru.sejapoe.dz1.model.Post;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostDao postDao;
    private final PostMapper postMapper;

    @GetMapping
    public List<PostDto> recentPosts(@RequestParam(defaultValue = "10") int count, @RequestParam(defaultValue = "0") int offset) {
        return postDao.getRecentPosts(count, offset).stream().map(postMapper::toDto).toList();
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Post createPost(@ModelAttribute CreatePostDto createPostDto) {
        return postDao.createPost(postMapper.toEntity(createPostDto), createPostDto.files());
    }
}
