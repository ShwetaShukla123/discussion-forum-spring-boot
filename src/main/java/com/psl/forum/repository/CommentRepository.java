package com.psl.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.forum.model.Comment;
import com.psl.forum.model.Post;
import com.psl.forum.model.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findByPost(Post post);
	
	List<Comment> findAllByUser(User user);
}
