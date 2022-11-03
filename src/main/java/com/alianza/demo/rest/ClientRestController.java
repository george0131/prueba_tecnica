package com.alianza.demo.rest;

import com.alianza.demo.rest.domain.ClientDTO;
import com.alianza.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ClientRestController {

    final ClientService service;

    @Autowired
    public ClientRestController(ClientService service) {
        this.service = service;
    }

    @PostMapping("/client")
    public ResponseEntity<ClientDTO> upsertClient(ClientDTO dto) {

        ClientDTO created = service.upsertClient(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/clients")
    public ResponseEntity<Page<ClientDTO>> getClients(ClientQuery clientQuery) {

        Page<ClientDTO> all = service.getAll(clientQuery);

        return ResponseEntity.ok(all);
    }

    @GetMapping("/download-clients")
    public ResponseEntity<Resource> downloadClients() {
        String filename = "clients.xlsx";
        InputStreamResource file = new InputStreamResource(service.downloadClients());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(file);
    }
}
