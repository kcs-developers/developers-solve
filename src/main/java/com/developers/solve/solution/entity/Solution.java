package com.developers.solve.solution.entity;

import com.developers.solve.common.entity.BaseTimeEntity;
import com.developers.solve.problem.entity.Problem;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Where(clause = "deleted_at is NULL")
@SQLDelete(sql = "update solution set deleted_at = CURRENT_TIMESTAMP where solution_id = ?")
@Table(name="solution")
@ToString(exclude = "solutionId")
@Entity
public class Solution extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="solutionId")
    private Long solutionId;

    @Column(name="solver", nullable = false)
    private Long solver;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name="problemid", nullable = false) //problemId -> problemid로 수정했음 확인 필요
    private Problem problem;

}
