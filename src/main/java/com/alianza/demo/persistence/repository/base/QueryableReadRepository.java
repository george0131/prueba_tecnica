package com.alianza.demo.persistence.repository.base;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;

@NoRepositoryBean
public interface QueryableReadRepository<Entity, ID> extends Repository<Entity, ID> {

    <C> Page<C> query(JPAQuery<C> jpaQuery, Pageable pageable);

    <C> List<C> query(JPAQuery<C> jpaQuery);
}
