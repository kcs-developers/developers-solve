package com.developers.solve.problem.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProblem is a Querydsl query type for Problem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProblem extends EntityPathBase<Problem> {

    private static final long serialVersionUID = -338169942L;

    public static final QProblem problem = new QProblem("problem");

    public final com.developers.solve.common.entity.QBaseTimeEntity _super = new com.developers.solve.common.entity.QBaseTimeEntity(this);

    public final StringPath answer = createString("answer");

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final StringPath hashtag = createString("hashtag");

    public final StringPath level = createString("level");

    public final NumberPath<Long> likes = createNumber("likes", Long.class);

    public final NumberPath<Long> problemId = createNumber("problemId", Long.class);

    public final StringPath title = createString("title");

    public final StringPath type = createString("type");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> views = createNumber("views", Long.class);

    public final StringPath writer = createString("writer");

    public QProblem(String variable) {
        super(Problem.class, forVariable(variable));
    }

    public QProblem(Path<? extends Problem> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProblem(PathMetadata metadata) {
        super(Problem.class, metadata);
    }

}

