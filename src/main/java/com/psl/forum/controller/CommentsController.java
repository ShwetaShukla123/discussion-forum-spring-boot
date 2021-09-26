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

import com.psl.forum.dto.CommentDto;
import com.psl.forum.exception.PostNotFoundException;
import com.psl.forum.service.CommentsService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentsController {
	
	private final CommentsService commentsService;

	@PostMapping()
	public ResponseEntity<Void> createComment(@RequestBody CommentDto commentDto) throws PostNotFoundException {
		commentsService.save(commentDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping(path = "/by-post/{postId}")
	public ResponseEntity<List<CommentDto>> getAllCommentsForPost(@PathVariable Long postId) throws PostNotFoundException {
		return ResponseEntity.status(HttpStatus.OK).body(commentsService.getAllCommentsForPost(postId));
	}
	
	@GetMapping(path = "/by-user/{username}")
	public ResponseEntity<List<CommentDto>> getAllCommentsForUser(@PathVariable String username){
		return ResponseEntity.status(HttpStatus.OK).body(commentsService.getAllCommentsForUser(username));
	}
}
