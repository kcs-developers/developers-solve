package com.developers.solve.problem.repository;

import com.developers.solve.problem.entity.Attached;
import org.springframework.data.jpa.repository.JpaRepository;

import java.text.AttributedCharacterIterator;

public interface AttachedRepository extends JpaRepository <Attached,Long> {
}
