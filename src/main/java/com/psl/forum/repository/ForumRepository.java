package com.psl.forum.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.forum.model.Community;

@Repository
public interface ForumRepository extends JpaRepository<Community, Long> {

	Optional<Community> findByName(String name);
}
