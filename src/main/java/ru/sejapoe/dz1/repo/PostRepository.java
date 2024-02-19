package ru.sejapoe.dz1.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sejapoe.dz1.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}