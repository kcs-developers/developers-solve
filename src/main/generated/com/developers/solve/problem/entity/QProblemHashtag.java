package com.developers.solve.problem.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProblemHashtag is a Querydsl query type for ProblemHashtag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProblemHashtag extends EntityPathBase<ProblemHashtag> {

    private static final long serialVersionUID = 629231810L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProblemHashtag problemHashtag = new QProblemHashtag("problemHashtag");

    public final com.developers.solve.common.entity.QBaseTimeEntity _super = new com.developers.solve.common.entity.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> hashtagId = createNumber("hashtagId", Long.class);

    public final StringPath hashtagName = createString("hashtagName");

    public final QProblem problemId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QProblemHashtag(String variable) {
        this(ProblemHashtag.class, forVariable(variable), INITS);
    }

    public QProblemHashtag(Path<? extends ProblemHashtag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProblemHashtag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProblemHashtag(PathMetadata metadata, PathInits inits) {
        this(ProblemHashtag.class, metadata, inits);
    }

    public QProblemHashtag(Class<? extends ProblemHashtag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.problemId = inits.isInitialized("problemId") ? new QProblem(forProperty("problemId")) : null;
    }

}

