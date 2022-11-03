package com.alianza.demo.persistence.repository.base;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QuerydslJpaPredicateExecutor;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Transactional
public class QueryableReadRepositoryImpl<Entity, ID> extends QuerydslJpaPredicateExecutor<Entity>
        implements QueryableReadRepository<Entity,ID> {

    private static final EntityPathResolver resolver = SimpleEntityPathResolver.INSTANCE;
    private final PathBuilder<Entity> builder;
    private final EntityManager entityManager;

    public QueryableReadRepositoryImpl(JpaEntityInformation<Entity, ?> entityInformation,
                                       EntityManager entityManager) {
        super(entityInformation, entityManager, resolver, null);
        EntityPath<Entity> path = resolver.createPath(entityInformation.getJavaType());
        this.entityManager = entityManager;
        this.builder = new PathBuilder<>(path.getType(), path.getMetadata());
    }


    @Override
    public <C> Page<C> query(JPAQuery<C> jpaQuery, Pageable pageable) {

        if (pageable == null) {
            return new PageImpl<>(new ArrayList<>());
        }

        jpaQuery = jpaQuery.clone(this.entityManager);

        long count = jpaQuery.fetch().size();

        jpaQuery.offset(pageable.getOffset()).limit(pageable.getPageSize());

        Sort sort = pageable.getSort();

        for (Sort.Order sort1 : sort) {

            Order order = sort1.getDirection().isAscending() ? Order.ASC : Order.DESC;

            Expression<Object> expression = builder.get(sort1.getProperty());

            OrderSpecifier orderSpecifier = new OrderSpecifier(order, expression);

            jpaQuery.orderBy(orderSpecifier);
        }

        List<C> content = jpaQuery.fetch();

        return new PageImpl<>(content, pageable, count);
    }

    @Override
    public <C> List<C> query(JPAQuery<C> jpaQuery) {

        jpaQuery = jpaQuery.clone(this.entityManager);

        return jpaQuery.fetch();
    }
}
