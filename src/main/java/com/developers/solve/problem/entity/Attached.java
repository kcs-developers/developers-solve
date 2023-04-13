package com.developers.solve.problem.entity;

import com.developers.solve.common.entity.BaseTimeEntity;
import com.developers.solve.problem.requestDTO.UpdatedAttachedDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Entity
@Where(clause = "deleted_at is NULL")
@Table(name = "attached")
@SQLDelete(sql = "update attached set deleted_at = CURRENT_TIMESTAMP where problem_id = ?")
@DynamicUpdate
public class Attached extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attach_id")
    private Long attachId;
    @Column(name = "path_name")
    private String pathName;
    //메서드 2개 실행해서 조회, delete, update도 추가
    //상세조회시 따로 따로
    @Column(name = "problem_id")
    private Long problemId;

}
