package com.psl.forum.service;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.psl.forum.dto.PostRequest;
import com.psl.forum.dto.PostResponse;
import com.psl.forum.exception.CommunityNotFoundException;
import com.psl.forum.exception.PostNotFoundException;
import com.psl.forum.mapper.PostMapper;
import com.psl.forum.model.Community;
import com.psl.forum.model.Post;
import com.psl.forum.model.User;
import com.psl.forum.repository.ForumRepository;
import com.psl.forum.repository.PostRepository;
import com.psl.forum.repository.UserRepository;

import lombok.AllArgsConstructor;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Transactional
public class PostService {

	private final ForumRepository forumRepository;
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final AuthService authService;
	private final PostMapper postMapper;
	
	public void save(PostRequest postRequest) throws CommunityNotFoundException {
		Community community = forumRepository.findByName(postRequest.getCommunityName())
		.orElseThrow(() -> new CommunityNotFoundException("No Community Found as "+postRequest.getCommunityName()));
		User currentUser = authService.getCurrentUser();
		postRepository.save(postMapper.map(postRequest, community, currentUser));
	}

	public List<PostResponse> getAllPosts() {
		
		return postRepository.findAll().stream().map(postMapper::mapToDto).collect(toList());
	}
	
	public PostResponse getPost(Long id) throws PostNotFoundException {
		Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id.toString()));
		return postMapper.mapToDto(post);
	}
	
	public List<PostResponse> getPostsByCommunity(Long communityId) throws CommunityNotFoundException {
		Community community = forumRepository.findById(communityId).orElseThrow(() -> new CommunityNotFoundException(communityId.toString()));
		List<Post> posts = postRepository.findAllByCommunity(community);
		return posts.stream().map(postMapper::mapToDto).collect(toList());
	}

	public List<PostResponse> getPostsByUsername(String username){
		User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(username));
		return postRepository.findByUser(user).stream().map(postMapper::mapToDto).collect(toList());
	}

}
