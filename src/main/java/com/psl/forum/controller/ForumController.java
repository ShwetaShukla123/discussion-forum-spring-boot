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

import com.psl.forum.dto.CommunityDto;
import com.psl.forum.exception.SpringForumException;
import com.psl.forum.service.ForumService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/forum")
@AllArgsConstructor
public class ForumController {
	
	private final ForumService forumService;
	
	@PostMapping
	public ResponseEntity<CommunityDto> createCommunity(@RequestBody CommunityDto communityDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(forumService.save(communityDto));
	}
	
	@GetMapping
	public ResponseEntity<List<CommunityDto>> getAllCommunities(){
		return ResponseEntity.status(HttpStatus.OK).body(forumService.getAll());
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<CommunityDto> getCommunity(@PathVariable Long id) throws SpringForumException{
		return ResponseEntity.status(HttpStatus.OK).body(forumService.getCommunity(id));
	}
}
