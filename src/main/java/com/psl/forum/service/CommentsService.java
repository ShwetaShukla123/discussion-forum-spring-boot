package com.psl.forum.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.psl.forum.dto.CommentDto;
import com.psl.forum.exception.PostNotFoundException;
import com.psl.forum.mapper.CommentMapper;
import com.psl.forum.model.Comment;
import com.psl.forum.model.Post;
import com.psl.forum.model.User;
import com.psl.forum.repository.CommentRepository;
import com.psl.forum.repository.PostRepository;
import com.psl.forum.repository.UserRepository;
import static java.util.stream.Collectors.toList;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentsService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final AuthService authService;
	private final CommentMapper commentMapper;
	private final CommentRepository commentRepository;
	
	public void save(CommentDto commentDto) throws PostNotFoundException {
		Post post = postRepository.findById(commentDto.getPostId()).orElseThrow(() -> new PostNotFoundException(commentDto.getPostId().toString()));
		Comment comment = commentMapper.map(commentDto, post, authService.getCurrentUser());
		commentRepository.save(comment);
	}
	
	public List<CommentDto> getAllCommentsForPost(Long postId) throws PostNotFoundException{
		Post post = postRepository.findById(postId).orElseThrow(()-> new PostNotFoundException(postId.toString()));
		return commentRepository.findByPost(post).stream().map(commentMapper::mapToDto).collect(toList());
	}
	
	public List<CommentDto> getAllCommentsForUser(String username){
		User user = userRepository.findByUsername(username).get();
		return commentRepository.findAllByUser(user).stream().map(commentMapper::mapToDto).collect(toList());
	}
}
