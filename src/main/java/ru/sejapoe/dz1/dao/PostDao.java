package ru.sejapoe.dz1.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.sejapoe.dz1.model.Post;
import ru.sejapoe.dz1.repo.PostRepository;
import ru.sejapoe.dz1.utils.ChunkRequest;

import java.util.List;


@Component
public class PostDao {

    private final PostRepository postRepository;

    public PostDao(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getRecentPosts(int count, int offset) {
        return postRepository.findAll(new ChunkRequest(offset, count, Sort.by("id"))).toList();
    }

    public Post createPost(String title, String text) {
        Post post = new Post(null, title, text);
        return postRepository.save(post);
    }
}
