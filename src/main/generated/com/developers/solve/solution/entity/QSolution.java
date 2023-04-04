package com.developers.solve.solution.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSolution is a Querydsl query type for Solution
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSolution extends EntityPathBase<Solution> {

    private static final long serialVersionUID = 1680716822L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSolution solution = new QSolution("solution");

    public final com.developers.solve.common.entity.QBaseTimeEntity _super = new com.developers.solve.common.entity.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final com.developers.solve.problem.entity.QProblem problemId;

    public final NumberPath<Long> solutionId = createNumber("solutionId", Long.class);

    public final StringPath solver = createString("solver");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QSolution(String variable) {
        this(Solution.class, forVariable(variable), INITS);
    }

    public QSolution(Path<? extends Solution> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSolution(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSolution(PathMetadata metadata, PathInits inits) {
        this(Solution.class, metadata, inits);
    }

    public QSolution(Class<? extends Solution> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.problemId = inits.isInitialized("problemId") ? new com.developers.solve.problem.entity.QProblem(forProperty("problemId")) : null;
    }

}

