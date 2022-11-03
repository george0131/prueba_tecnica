package com.alianza.demo.service;

import com.alianza.demo.mapper.ClientMapper;
import com.alianza.demo.persistence.domain.Client;
import com.alianza.demo.persistence.domain.QClient;
import com.alianza.demo.persistence.repository.ClientRepository;
import com.alianza.demo.rest.ClientQuery;
import com.alianza.demo.rest.domain.ClientDTO;
import com.alianza.demo.utils.ExcelHelper;
import com.google.common.base.Strings;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.text.Normalizer;
import java.util.List;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {
    final ClientRepository repository;
    final ClientMapper mapper;

    @Autowired
    public ClientServiceImpl(ClientRepository repository, ClientMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientDTO> getAll(ClientQuery clientQuery) {

        JPAQuery<Client> jpaQuery = new JPAQuery<>();

        jpaQuery.from(QClient.client);

        if (clientQuery.getPage() < 0) clientQuery.setPage(0);

        if (clientQuery.getSize() <= 0) clientQuery.setSize(10);

        if (!Strings.isNullOrEmpty(clientQuery.getSharedKey()))
            jpaQuery.where(QClient.client.sharedKey.containsIgnoreCase(clientQuery.getSharedKey()));

        if (!Strings.isNullOrEmpty(clientQuery.getName()))
            jpaQuery.where(QClient.client.businessId.containsIgnoreCase(clientQuery.getName()));

        if (!Strings.isNullOrEmpty(clientQuery.getEmail()))
            jpaQuery.where(QClient.client.email.containsIgnoreCase(clientQuery.getEmail()));

        if (clientQuery.getPhone() != null)
            jpaQuery.where(QClient.client.phone.stringValue().containsIgnoreCase(clientQuery.getPhone().toString()));

        if (clientQuery.getStartDate() != null && clientQuery.getEndDate() != null)
            jpaQuery.where(QClient.client.dataAdded.between(clientQuery.getStartDate(), clientQuery.getEndDate()));

        jpaQuery.orderBy(QClient.client.businessId.asc());

        PageRequest pageRequest = PageRequest.of(clientQuery.getPage(), clientQuery.getSize());

        Page<Client> page = repository.query(jpaQuery, pageRequest);

        List<ClientDTO> dtos = mapper.toDTO(page.getContent());

        return new PageImpl<>(dtos, pageRequest, page.getTotalElements());

    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientDTO> findAll() {

        List<Client> all = repository.findAll();

        return mapper.toDTO(all);
    }

    @Override
    public ClientDTO upsertClient(ClientDTO dto) {

        String noAccents = stripAccents(dto.getBusinessId()).toLowerCase();
        String sharedKey = noAccents.charAt(0) + noAccents.split(" ")[1];
        dto.setSharedKey(sharedKey);
        Client saved = repository.save(mapper.toEntity(dto));

        return mapper.toDTO(saved);
    }

    @Override
    public ClientDTO findById(Long id) {

        Client found = repository.findById(id).orElse(null);

        return mapper.toDTO(found);
    }

    @Override
    public ByteArrayInputStream downloadClients() {

        List<Client> tutorials = repository.findAll();

        ByteArrayInputStream in = ExcelHelper.clientsToExcel(tutorials);

        return in;
    }

    private String stripAccents(String s)
    {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }
}
