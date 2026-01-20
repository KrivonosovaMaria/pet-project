package com.app.race_comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceCommentRepository extends JpaRepository<RaceComment, Long> {
}
