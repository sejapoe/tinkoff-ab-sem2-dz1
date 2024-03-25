package ru.sejapoe.dz1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.sejapoe.dz1.model.File;
import ru.sejapoe.dz1.model.Operation;
import ru.sejapoe.dz1.model.Post;
import ru.sejapoe.dz1.repo.PostRepository;
import ru.sejapoe.dz1.storage.StorageService;
import ru.sejapoe.dz1.utils.ChunkRequest;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {
    private final OperationService operationService;
    private final PostRepository postRepository;
    private final StorageService storageService;

    @Cacheable(value = "paged_posts", key = "{ #count, #offset }")
    public List<Post> getRecentPosts(int count, int offset) {
        operationService.logOperation(
                new Operation.ChunkEntitiesReference("post", count, offset),
                Operation.OperationType.READ
        );
        return postRepository.findAll(new ChunkRequest(offset, count, Sort.by("id"))).toList();
    }

    @CacheEvict(value = "paged_posts", allEntries = true)
    public Post createPost(Post post, List<MultipartFile> files) {
        List<File> images = files.stream().map(storageService::store).toList();
        post.setImages(images);

        Post savedPost = postRepository.save(post);
        operationService.logOperation(
                new Operation.SingleEntityReference("post", savedPost.getId()),
                Operation.OperationType.WRITE
        );

        return savedPost;
    }
}
