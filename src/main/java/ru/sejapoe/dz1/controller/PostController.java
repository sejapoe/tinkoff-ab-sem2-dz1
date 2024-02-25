package ru.sejapoe.dz1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.sejapoe.dz1.service.PostService;
import ru.sejapoe.dz1.dto.post.CreatePostDto;
import ru.sejapoe.dz1.dto.post.PostDto;
import ru.sejapoe.dz1.mapper.PostMapper;
import ru.sejapoe.dz1.model.Post;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;

    @GetMapping
    public List<PostDto> recentPosts(@RequestParam(defaultValue = "10") int count, @RequestParam(defaultValue = "0") int offset) {
        return postService.getRecentPosts(count, offset).stream().map(postMapper::toDto).toList();
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Post createPost(@ModelAttribute CreatePostDto createPostDto) {
        return postService.createPost(postMapper.toEntity(createPostDto), createPostDto.files());
    }
}
