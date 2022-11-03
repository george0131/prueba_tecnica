package com.alianza.demo.mapper;

import com.alianza.demo.persistence.domain.Client;
import com.alianza.demo.rest.domain.ClientDTO;

import java.util.List;

public interface ClientMapper {

    Client toEntity(ClientDTO dto);
    ClientDTO toDTO(Client entity);
    List<ClientDTO> toDTO(List<Client> entities);
}
