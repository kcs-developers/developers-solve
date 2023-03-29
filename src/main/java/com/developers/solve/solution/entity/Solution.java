package com.developers.solve.solution.entity;

import com.developers.solve.common.entity.BaseTimeEntity;
import com.developers.solve.problem.entity.Problem;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted_at is NULL")
@SQLDelete(sql = "update solution set deleted_at = CURRENT_TIMESTAMP where solutionid = ?")
@Table(name="solution")
@ToString(exclude = "problemId")

public class Solution extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solutionId")
    private Long solutionId;

    @Column(name = "userId", nullable = false)
    private Long userId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problemId") // 가져온 외래키 명을 설정해주는 애노테이션
    private Problem problemId;

    @Builder
    // 외래키는 값이 바뀌지 않고 PK도 바뀌지 않으므로 solver, solved만 빌갖
    public Solution(Long userId, Problem problemId) {
        this.userId = userId;
        this.problemId = problemId;

    }
}
