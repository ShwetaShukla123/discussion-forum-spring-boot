package com.psl.forum.model;
import java.util.Arrays;

import com.psl.forum.exception.SpringForumException;

public enum VoteType {

	UPVOTE(1),
	DOWNVOTE(-1);
	
	private int direction;
	
	VoteType(int direction) {
	}
	
	public Integer getDirection() {
        return direction;
    }
	
	public static VoteType lookup(Integer direction) throws SpringForumException {
		return Arrays.stream(VoteType.values())
				.filter(value -> value.getDirection().equals(direction))
				.findAny()
				.orElseThrow(() -> new SpringForumException("Vote not found"));
	}
}
