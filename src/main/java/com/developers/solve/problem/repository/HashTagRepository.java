package com.developers.solve.problem.repository;

import com.developers.solve.problem.entity.ProblemHashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashTagRepository extends JpaRepository<ProblemHashtag,Long> {
}
