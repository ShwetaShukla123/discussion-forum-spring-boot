package com.psl.forum.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.psl.forum.dto.CommunityDto;
import com.psl.forum.model.Community;
import com.psl.forum.model.Post;
import com.psl.forum.model.User;

@Mapper(componentModel = "spring")
public interface CommunityMappper {
	
	@Mapping(target = "numberOfPosts", expression="java(mapPosts(community.getPosts()))")
	CommunityDto mapCommunityToDto(Community community);
	
	default Integer mapPosts(List<Post> posts) {
		return posts.size();
	}
	
	@InheritInverseConfiguration
	@Mapping(target = "posts", ignore = true)
	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target = "user", source = "user")
	Community mapDtoToCommunity(CommunityDto communityDto, User user);
}
