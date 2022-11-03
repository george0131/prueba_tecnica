package com.alianza.demo.persistence.repository.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<Entity, ID extends Serializable> extends JpaRepository<Entity, ID>, QueryableReadRepository<Entity, ID> {
}
