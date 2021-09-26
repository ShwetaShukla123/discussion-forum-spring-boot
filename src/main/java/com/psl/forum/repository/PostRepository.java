package com.psl.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.forum.model.Community;
import com.psl.forum.model.Post;
import com.psl.forum.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findAllByCommunity(Community community);
	
	List<Post> findByUser(User user);
}
