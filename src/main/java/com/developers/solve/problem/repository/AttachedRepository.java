package com.developers.solve.problem.repository;

import com.developers.solve.problem.entity.Attached;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.text.AttributedCharacterIterator;
import java.util.List;

public interface AttachedRepository extends JpaRepository <Attached,Long> {

    @Query("select a.pathName from Attached a where a.problemId = :problemId")
    List<String> AttachedFile(Long problemId);
}
