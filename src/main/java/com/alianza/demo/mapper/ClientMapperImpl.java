package com.alianza.demo.mapper;

import com.alianza.demo.persistence.domain.Client;
import com.alianza.demo.rest.domain.ClientDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClientMapperImpl implements ClientMapper {

    @Override
    public Client toEntity(ClientDTO dto) {

        if (dto != null) {

            Client client = new Client();

            client.setId(dto.getId());
            client.setSharedKey(dto.getSharedKey());
            client.setBusinessId(dto.getBusinessId());
            client.setEmail(dto.getEmail());
            client.setPhone(dto.getPhone());
            client.setDataAdded(dto.getDataAdded());

            return client;

        }

        return null;
    }

    @Override
    public ClientDTO toDTO(Client entity) {

        if (entity != null) {

            ClientDTO dto = new ClientDTO();

            dto.setId(entity.getId());
            dto.setSharedKey(entity.getSharedKey());
            dto.setBusinessId(entity.getBusinessId());
            dto.setEmail(entity.getEmail());
            dto.setPhone(entity.getPhone());
            dto.setDataAdded(entity.getDataAdded());

            return dto;
        }

        return null;
    }

    @Override
    public List<ClientDTO> toDTO(List<Client> entities) {

        if (entities != null) {
            List<ClientDTO> list = new ArrayList<ClientDTO>( entities.size() );
            for (Client client: entities) {
                list.add( toDTO(client) );
            }

            return list;

        }

        return null;
    }
}
