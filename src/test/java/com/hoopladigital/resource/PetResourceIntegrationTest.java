package com.hoopladigital.resource;

import java.io.IOException;
import java.net.URISyntaxException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoopladigital.bean.Pet;
import com.hoopladigital.service.PetService;
import com.hoopladigital.test.AbstractTest;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.plugins.server.resourcefactory.SingletonResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static com.hoopladigital.test.MockHelper.allDeclaredMocks;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class PetResourceIntegrationTest extends AbstractTest {

	public static final String PETS = "/pets/";

	@Mock
	private static PetService petService;

	private static PetResource petResource;
	private Dispatcher dispatcher;
	private ObjectMapper mapper = new ObjectMapper();
	private Pet pet;

	@Before
	public void beforePetResourceTest() {
		petResource = new PetResource(petService);
		dispatcher = MockDispatcherFactory.createDispatcher();
		dispatcher.getRegistry().addResourceFactory(new SingletonResource(petResource));
		pet = getPet();
	}

	@After
	public void afterPetResourceTest(){ verifyNoMoreInteractions(allDeclaredMocks(this)); }

	@Test
	public void should_get_pet() throws URISyntaxException, IOException {

		// setup
		Long id = pet.getId();
		when(petService.get(id)).thenReturn(pet);

		// run test
		MockHttpRequest request = MockHttpRequest.get(PETS + id);
		MockHttpResponse response = new MockHttpResponse();

		dispatcher.invoke(request, response);

		// verify mocks / capture values
		verify(petService).get(id);

		// assert results
		assertEquals(200, response.getStatus());
		Pet actual = mapper.readValue(response.getContentAsString(), Pet.class);
		assertEquals(pet, actual);
	}

	@Test
	public void should_not_find_pet() throws URISyntaxException {

		// setup
		Long id = pet.getId();
		when(petService.get(id)).thenReturn(null);

		// run test
		MockHttpRequest request = MockHttpRequest.get(PETS + id);
		MockHttpResponse response = new MockHttpResponse();

		dispatcher.invoke(request, response);

		// verify mocks / capture values
		verify(petService).get(id);

		// assert results
		assertEquals(404, response.getStatus());

	}

	@Test
	public void should_create_pet() throws URISyntaxException, IOException {

		// setup
		when(petService.create(pet)).thenReturn(Boolean.TRUE);

		// run test
		MockHttpRequest request = MockHttpRequest.post(PETS);
		request.content(mapper.writeValueAsBytes(pet));
		request.header("Content-Type","application/json");
		MockHttpResponse response = new MockHttpResponse();
		dispatcher.invoke(request, response);

		// verify mocks / capture values
		verify(petService).create(pet);

		// assert results
		assertEquals(200, response.getStatus());
		Pet actual = mapper.readValue(response.getContentAsString(), Pet.class);
		assertEquals(pet, actual);

	}

	@Test
	public void should_fail_to_create_pet() throws IOException, URISyntaxException {

		pet.setId(null);
		// setup
		when(petService.create(pet)).thenReturn(Boolean.FALSE);

		// run test
		MockHttpRequest request = MockHttpRequest.post(PETS);
		request.content(mapper.writeValueAsBytes(pet));
		request.header("Content-Type","application/json");
		MockHttpResponse response = new MockHttpResponse();
		dispatcher.invoke(request, response);

		// verify mocks / capture values
		verify(petService).create(pet);

		// assert results
		assertEquals(404, response.getStatus());
	}

	@Test
	public void should_update_pet() throws IOException, URISyntaxException {

		// setup
		when(petService.update(pet)).thenReturn(Boolean.TRUE);

		// run test
		MockHttpRequest request = MockHttpRequest.put(PETS + pet.getId());
		request.content(mapper.writeValueAsBytes(pet));
		request.header("Content-Type","application/json");
		MockHttpResponse response = new MockHttpResponse();
		dispatcher.invoke(request, response);


		// verify mocks / capture values
		verify(petService).update(pet);

		// assert results
		assertEquals(200, response.getStatus());
		Pet actual = mapper.readValue(response.getContentAsString(), Pet.class);
		assertEquals(pet, actual);

	}

	@Test
	public void should_fail_to_update_pet() throws IOException, URISyntaxException {

		// setup
		when(petService.update(pet)).thenReturn(Boolean.FALSE);

		// run test
		MockHttpRequest request = MockHttpRequest.put(PETS + pet.getId());
		request.content(mapper.writeValueAsBytes(pet));
		request.header("Content-Type","application/json");
		MockHttpResponse response = new MockHttpResponse();
		dispatcher.invoke(request, response);


		// verify mocks / capture values
		verify(petService).update(pet);

		// assert results
		assertEquals(404, response.getStatus());
	}

	@Test
	public void should_delete_pet() throws URISyntaxException {

		// setup
		Long id = pet.getId();
		when(petService.delete(id)).thenReturn(Boolean.TRUE);
		// run test
		MockHttpRequest request = MockHttpRequest.delete(PETS + id);
		MockHttpResponse response = new MockHttpResponse();
		dispatcher.invoke(request, response);

		// verify mocks / capture values
		verify(petService).delete(id);

		// assert results
		assertEquals(204, response.getStatus());

	}

	@Test
	public void should_fail_to_delete_pet() throws URISyntaxException {

		// setup
		Long id = pet.getId();
		when(petService.delete(id)).thenReturn(Boolean.FALSE);

		// run test
		MockHttpRequest request = MockHttpRequest.delete(PETS + id);
		MockHttpResponse response = new MockHttpResponse();
		dispatcher.invoke(request, response);

		// verify mocks / capture values
		verify(petService).delete(id);

		// assert results
		assertEquals(404, response.getStatus());

	}

	private Pet getPet(){
		Pet pet = new Pet();
		pet.setId(System.currentTimeMillis());
		pet.setName("Integration Test Pet");
		pet.setPersonId(1L);
		return pet;
	}

}
