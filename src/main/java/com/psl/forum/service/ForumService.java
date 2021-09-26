package com.psl.forum.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.psl.forum.dto.CommunityDto;
import com.psl.forum.exception.SpringForumException;
import com.psl.forum.mapper.CommunityMappper;
import com.psl.forum.model.Community;
import com.psl.forum.model.User;
import com.psl.forum.repository.ForumRepository;
import static java.util.stream.Collectors.toList;

import java.util.List;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ForumService {
	
	private final ForumRepository forumRepository;
	
	private final CommunityMappper communityMappper;
	
	private final AuthService authService;
	
	@Transactional
	public CommunityDto save(CommunityDto communityDto) {
		User currentUser = authService.getCurrentUser();
		Community community = forumRepository.save(communityMappper.mapDtoToCommunity(communityDto, currentUser));
		communityDto.setId(community.getId());
		return communityDto;
	}

	
	
	@Transactional
	public List<CommunityDto> getAll() {
		return forumRepository.findAll().stream().map(communityMappper::mapCommunityToDto).collect(toList());
	}



	public CommunityDto getCommunity(Long id) throws SpringForumException {
		Community community = forumRepository.findById(id).orElseThrow(() -> new SpringForumException("No Community found with ID: "+id));
		return communityMappper.mapCommunityToDto(community);
	}
	
	
}
