package com.psl.forum.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.psl.forum.dto.VoteDto;
import com.psl.forum.exception.PostNotFoundException;
import com.psl.forum.exception.SpringForumException;
import com.psl.forum.model.Post;
import com.psl.forum.model.Vote;
import com.psl.forum.model.VoteType;
import com.psl.forum.repository.PostRepository;
import com.psl.forum.repository.VoteRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VoteService {
	
	private final VoteRepository voteRepository;
	private final PostRepository postRepository;
	private final AuthService authService;
	
	public void vote(VoteDto voteDto) throws PostNotFoundException, SpringForumException {
		Post post = postRepository.findById(voteDto.getPostId()).orElseThrow(() -> 
						new PostNotFoundException("Post Not Found with id "+voteDto.getPostId()));
		Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
		if(voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
			throw new SpringForumException("You have already "+voteDto.getVoteType()+"D for this post");
		}
		if (VoteType.UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
	}
	
	private Vote mapToVote(VoteDto voteDto, Post post) {
		return Vote.builder().voteType(voteDto.getVoteType()).post(post).user(authService.getCurrentUser()).build();
	}

}
