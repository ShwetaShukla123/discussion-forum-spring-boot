package com.psl.forum.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psl.forum.dto.VoteDto;
import com.psl.forum.exception.PostNotFoundException;
import com.psl.forum.exception.SpringForumException;
import com.psl.forum.service.VoteService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/votes")
@AllArgsConstructor
public class VoteController {

	private final VoteService voteService;
	
	@PostMapping
	public ResponseEntity<Void> vote(@RequestBody VoteDto voteDto) throws PostNotFoundException, SpringForumException{
		voteService.vote(voteDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
