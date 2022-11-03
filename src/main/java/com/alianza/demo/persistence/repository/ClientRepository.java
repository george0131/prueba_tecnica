package com.alianza.demo.persistence.repository;

import com.alianza.demo.persistence.domain.Client;
import com.alianza.demo.persistence.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends BaseRepository<Client, Long> {
}
