package com.developers.solve.problem.repository;

import com.developers.solve.problem.entity.Attached;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.text.AttributedCharacterIterator;
import java.util.List;

public interface AttachedRepository extends JpaRepository <Attached,Long> {

    @Query("select a.pathName from Attached a where a.problemId = :problemId")
    List<String> AttachedFile(Long problemId);
    @Modifying
    @Transactional
    @Query("update Attached a set a.deletedAt = current_timestamp where a.problemId = :problemId")
    void DeleteAttachedFile(Long problemId);

    @Modifying
    @Transactional
    @Query("select a.pathName from Attached a where a.problemId = :problemId")
    List<String> findByPathName(Long problemId);
}

