package com.psl.forum.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psl.forum.dto.PostRequest;
import com.psl.forum.dto.PostResponse;
import com.psl.forum.exception.CommunityNotFoundException;
import com.psl.forum.exception.PostNotFoundException;
import com.psl.forum.service.PostService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

	private final PostService postService;
	
	@PostMapping
	public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) throws CommunityNotFoundException {
		postService.save(postRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<PostResponse>> getAllPosts(){
		return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PostResponse> getPost(@PathVariable Long id) throws PostNotFoundException {
		return ResponseEntity.status(HttpStatus.OK).body(postService.getPost(id));
	}
	
	@GetMapping("/by-community/{id}")
	public ResponseEntity<List<PostResponse>> getPostsByCommunity(@PathVariable Long id) throws CommunityNotFoundException{
		return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByCommunity(id));
	}
	
	@GetMapping("/by-user/{name}")
	public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String name){
		return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByUsername(name));
	}
}
