package com.developers.solve.problem.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAttached is a Querydsl query type for Attached
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAttached extends EntityPathBase<Attached> {

    private static final long serialVersionUID = -348720647L;

    public static final QAttached attached = new QAttached("attached");

    public final NumberPath<Long> attachId = createNumber("attachId", Long.class);

    public final StringPath pathName = createString("pathName");

    public final NumberPath<Long> problemId = createNumber("problemId", Long.class);

    public QAttached(String variable) {
        super(Attached.class, forVariable(variable));
    }

    public QAttached(Path<? extends Attached> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAttached(PathMetadata metadata) {
        super(Attached.class, metadata);
    }

}

