package com.alianza.demo;

import com.alianza.demo.rest.ClientQuery;
import com.alianza.demo.rest.domain.ClientDTO;
import com.alianza.demo.service.ClientService;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.time.LocalDate;

@SpringBootTest
//@Transactional
//@Rollback
class DemoApplicationTests {

	@Autowired
	ClientService service;

	@Test
	void contextLoads() {
	}

	@Test
	void createClient() {

		ClientDTO clientDTO = new ClientDTO();
		clientDTO.setBusinessId("Andrés Lopez");
		clientDTO.setPhone(3008299542L);
		clientDTO.setEmail("alopez@mail.com");
		clientDTO.setDataAdded(LocalDate.now());

		ClientDTO created = service.upsertClient(clientDTO);

		Assertions.assertNotEquals(created.getId(), clientDTO.getId());

	}

	@Test
	void upsertClient() {

		ClientDTO clientDTO = new ClientDTO();
		clientDTO.setBusinessId("Andrés Lopez");
		clientDTO.setPhone(3008299542L);
		clientDTO.setEmail("alopez@mail.com");
		clientDTO.setDataAdded(LocalDate.now());

		ClientDTO created = service.upsertClient(clientDTO);

		Assertions.assertNotEquals(created.getId(), clientDTO.getId());

		created.setBusinessId("Álvaro Gomez");
		created.setEmail("agomez@mail.com");

		ClientDTO updated = service.upsertClient(created);

		Assertions.assertEquals(created.getSharedKey(), updated.getSharedKey());

	}

	@Test
	public void getAll() {

		ClientDTO clientDTO = new ClientDTO();
		clientDTO.setBusinessId("Andrés Lopez");
		clientDTO.setPhone(3008299542L);
		clientDTO.setEmail("alopez@mail.com");
		clientDTO.setDataAdded(LocalDate.now());

		service.upsertClient(clientDTO);

		clientDTO = new ClientDTO();
		clientDTO.setBusinessId("Alvaro Gomez");
		clientDTO.setPhone(3008299542L);
		clientDTO.setEmail("agomez@mail.com");
		clientDTO.setDataAdded(LocalDate.now());

		service.upsertClient(clientDTO);

		clientDTO = new ClientDTO();
		clientDTO.setBusinessId("Andrea Jimenez");
		clientDTO.setPhone(3008299542L);
		clientDTO.setEmail("ajimenez@mail.com");
		clientDTO.setDataAdded(LocalDate.now());

		service.upsertClient(clientDTO);

		ClientQuery query = new ClientQuery();

		Page<ClientDTO> all = service.getAll(query);

		Assertions.assertEquals(all.getTotalElements(), 3L);

	}

	@Test
	public void filter() {

		ClientDTO clientDTO = new ClientDTO();
		clientDTO.setBusinessId("Andrés Lopez");
		clientDTO.setPhone(3008299542L);
		clientDTO.setEmail("alopez@mail.com");
		clientDTO.setDataAdded(LocalDate.now());

		service.upsertClient(clientDTO);

		clientDTO = new ClientDTO();
		clientDTO.setBusinessId("Alvaro Gomez");
		clientDTO.setPhone(3008299542L);
		clientDTO.setEmail("agomez@mail.com");
		clientDTO.setDataAdded(LocalDate.now());

		service.upsertClient(clientDTO);

		clientDTO = new ClientDTO();
		clientDTO.setBusinessId("Andrea Jimenez");
		clientDTO.setPhone(3008299542L);
		clientDTO.setEmail("ajimenez@mail.com");
		clientDTO.setDataAdded(LocalDate.now());

		service.upsertClient(clientDTO);

		ClientQuery query = new ClientQuery();

		query.setSharedKey("ajimenez");
		query.setName("ANDREA");

		Page<ClientDTO> all = service.getAll(query);

		Assertions.assertEquals(all.getTotalElements(), 1L);

	}

}
