package com.alianza.demo.service;

import com.alianza.demo.rest.ClientQuery;
import com.alianza.demo.rest.domain.ClientDTO;
import org.springframework.data.domain.Page;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface ClientService {

    Page<ClientDTO> getAll(ClientQuery query);
    List<ClientDTO> findAll();
    ClientDTO upsertClient(ClientDTO dto);
    ClientDTO findById(Long id);
    ByteArrayInputStream downloadClients();
}
