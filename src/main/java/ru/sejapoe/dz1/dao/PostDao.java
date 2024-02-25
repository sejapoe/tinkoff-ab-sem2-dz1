package ru.sejapoe.dz1.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.sejapoe.dz1.model.File;
import ru.sejapoe.dz1.model.Post;
import ru.sejapoe.dz1.repo.PostRepository;
import ru.sejapoe.dz1.storage.S3StorageService;
import ru.sejapoe.dz1.storage.StorageService;
import ru.sejapoe.dz1.utils.ChunkRequest;

import java.util.List;


@Component
public class PostDao {

    private final PostRepository postRepository;
    private final StorageService storageService;

    public PostDao(PostRepository postRepository, S3StorageService storageService) {
        this.postRepository = postRepository;
        this.storageService = storageService;
    }

    public List<Post> getRecentPosts(int count, int offset) {
        return postRepository.findAll(new ChunkRequest(offset, count, Sort.by("id"))).toList();
    }

    public Post createPost(Post post, List<MultipartFile> files) {
        List<File> images = files.stream().map(storageService::store).toList();
        post.setImages(images);
        return postRepository.save(post);
    }
}
