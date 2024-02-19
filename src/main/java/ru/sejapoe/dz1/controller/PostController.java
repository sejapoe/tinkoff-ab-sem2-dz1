package ru.sejapoe.dz1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import ru.sejapoe.dz1.dao.PostDao;
import ru.sejapoe.dz1.model.Post;

import java.util.List;

@Controller
@RequiredArgsConstructor
//@RequestMapping("/api/v1/graphql")
public class PostController {
    private final PostDao postDao;

    @QueryMapping
    public List<Post> recentPosts(@Argument int count, @Argument int offset) {
        return postDao.getRecentPosts(count, offset);
    }

    @MutationMapping
    public Post createPost(@Argument String title, @Argument String text) {
        return postDao.createPost(title, text);
    }
}
