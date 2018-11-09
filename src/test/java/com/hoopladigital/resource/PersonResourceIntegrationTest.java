package com.hoopladigital.resource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoopladigital.bean.Person;
import com.hoopladigital.bean.Pet;
import com.hoopladigital.service.PersonService;
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

public class PersonResourceIntegrationTest extends AbstractTest {

	public static final String PEOPLE = "people/";
	@Mock
	private PersonService personService;
	@Mock
	private PetService petService;

	private PersonResource personResource;
	private Dispatcher dispatcher;
	private ObjectMapper mapper = new ObjectMapper();
	private Person person;

	@Before
	public void beforePersonResourceTest() {
		personResource = new PersonResource(personService, petService);
		dispatcher = MockDispatcherFactory.createDispatcher();
		dispatcher.getRegistry().addResourceFactory(new SingletonResource(personResource));
		person = getPerson();
	}

	@After
	public void afterPersonResourceTest(){ verifyNoMoreInteractions(allDeclaredMocks(this)); }

	@Test
	public void should_get_person_list() throws URISyntaxException, IOException {
		// setup
		final List<Person> expected = Collections.singletonList(person);
		when(personService.getList()).thenReturn(expected);

		// run test
		MockHttpRequest request = MockHttpRequest.get(PEOPLE);
		MockHttpResponse response = new MockHttpResponse();
		dispatcher.invoke(request, response);

		// verify mocks / capture values
		verify(personService).getList();
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(200, response.getStatus());
		Person[] people = mapper.readValue(response.getContentAsString(), Person[].class);
		assertEquals(person, people[0]);
	}

	@Test
	public void should_get_person_pet_list() throws URISyntaxException, IOException {
		// setup
		Pet pet = getPet();
		final List<Pet> expected = Collections.singletonList(pet);
		when(petService.getList(1L)).thenReturn(expected);

		// run test
		MockHttpRequest request = MockHttpRequest.get(PEOPLE+1+"/pets");
		MockHttpResponse response = new MockHttpResponse();
		dispatcher.invoke(request, response);

		// verify mocks / capture values
		verify(petService).getList(1L);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(200, response.getStatus());
		Pet[] pets = mapper.readValue(response.getContentAsString(), Pet[].class);
		assertEquals(pet, pets[0]);
	}
	@Test
	public void should_get_person() throws URISyntaxException, IOException {

		// setup
		Long id = person.getId();
		when(personService.get(id)).thenReturn(person);

		// run test
		MockHttpRequest request = MockHttpRequest.get(PEOPLE + id);
		MockHttpResponse response = new MockHttpResponse();

		dispatcher.invoke(request, response);

		// verify mocks / capture values
		verify(personService).get(id);

		// assert results
		assertEquals(200, response.getStatus());
		Person actual = mapper.readValue(response.getContentAsString(), Person.class);
		assertEquals(person, actual);

	}

	@Test
	public void should_not_find_person() throws URISyntaxException {

		// setup
		Long id = person.getId();
		when(personService.get(id)).thenReturn(null);

		// run test
		MockHttpRequest request = MockHttpRequest.get(PEOPLE + id);
		MockHttpResponse response = new MockHttpResponse();

		dispatcher.invoke(request, response);

		// verify mocks / capture values
		verify(personService).get(id);

		// assert results
		assertEquals(404, response.getStatus());

	}

	@Test
	public void should_create_person() throws IOException, URISyntaxException {

		// setup
		when(personService.create(person)).thenReturn(Boolean.TRUE);

		// run test
		MockHttpRequest request = MockHttpRequest.post(PEOPLE);
		request.content(mapper.writeValueAsBytes(person));
		request.header("Content-Type","application/json");
		MockHttpResponse response = new MockHttpResponse();
		dispatcher.invoke(request, response);

		// verify mocks / capture values
		verify(personService).create(person);

		// assert results
		assertEquals(200, response.getStatus());
		Person actual = mapper.readValue(response.getContentAsString(), Person.class);
		assertEquals(person, actual);

	}

	@Test
	public void should_fail_to_create_person() throws IOException, URISyntaxException {

		person.setId(null);
		// setup
		when(personService.create(person)).thenReturn(Boolean.FALSE);

		// run test
		MockHttpRequest request = MockHttpRequest.post(PEOPLE);
		request.content(mapper.writeValueAsBytes(person));
		request.header("Content-Type","application/json");
		MockHttpResponse response = new MockHttpResponse();
		dispatcher.invoke(request, response);

		// verify mocks / capture values
		verify(personService).create(person);

		// assert results
		assertEquals(404, response.getStatus());
	}

	@Test
	public void should_update_person() throws IOException, URISyntaxException {

		// setup
		when(personService.update(person)).thenReturn(Boolean.TRUE);

		// run test
		MockHttpRequest request = MockHttpRequest.put(PEOPLE + person.getId());
		request.content(mapper.writeValueAsBytes(person));
		request.header("Content-Type","application/json");
		MockHttpResponse response = new MockHttpResponse();
		dispatcher.invoke(request, response);


		// verify mocks / capture values
		verify(personService).update(person);

		// assert results
		assertEquals(200, response.getStatus());
		Person actual = mapper.readValue(response.getContentAsString(), Person.class);
		assertEquals(person, actual);

	}

	@Test
	public void should_fail_to_update_person() throws IOException, URISyntaxException {

		// setup
		when(personService.update(person)).thenReturn(Boolean.FALSE);

		// run test
		MockHttpRequest request = MockHttpRequest.put(PEOPLE + person.getId());
		request.content(mapper.writeValueAsBytes(person));
		request.header("Content-Type","application/json");
		MockHttpResponse response = new MockHttpResponse();
		dispatcher.invoke(request, response);


		// verify mocks / capture values
		verify(personService).update(person);

		// assert results
		assertEquals(404, response.getStatus());
	}

	@Test
	public void should_delete_person() throws URISyntaxException {

		// setup
		Long id = person.getId();
		when(personService.delete(id)).thenReturn(Boolean.TRUE);
		// run test
		MockHttpRequest request = MockHttpRequest.delete(PEOPLE + id);
		MockHttpResponse response = new MockHttpResponse();
		dispatcher.invoke(request, response);

		// verify mocks / capture values
		assertEquals(204, response.getStatus());
		verify(personService).delete(id);

	}

	@Test
	public void should_fail_to_delete_person() throws URISyntaxException {

		// setup
		Long id = person.getId();
		when(personService.delete(id)).thenReturn(Boolean.FALSE);

		// run test
		MockHttpRequest request = MockHttpRequest.delete(PEOPLE + id);
		MockHttpResponse response = new MockHttpResponse();
		dispatcher.invoke(request, response);

		// verify mocks / capture values
		assertEquals(404, response.getStatus());
		verify(personService).delete(id);

	}

	private Person getPerson(){
		Person person = new Person();
		person.setId(System.currentTimeMillis());
		person.setFirstName("Integration");
		person.setMiddleName("Test");
		person.setLastName("PoJo");
		return person;
	}

	private Pet getPet(){
		Pet pet = new Pet();
		pet.setId(System.currentTimeMillis());
		pet.setName("Integration Test Pet");
		pet.setPersonId(1L);
		return pet;
	}

}
