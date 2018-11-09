package com.hoopladigital.resource;

import com.hoopladigital.bean.Person;
import com.hoopladigital.bean.Pet;
import com.hoopladigital.service.PersonService;
import com.hoopladigital.service.PetService;
import com.hoopladigital.test.AbstractTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.NotFoundException;

import static com.hoopladigital.test.MockHelper.allDeclaredMocks;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class PersonResourceTest extends AbstractTest {

	@Mock
	private PersonService personService;
	@Mock
	private PetService petService;

	private PersonResource personResource;

	@Before
	public void beforePersonResourceTest() {
		personResource = new PersonResource(personService, petService);
	}

	@After
	public void afterPersonResourceTest(){ verifyNoMoreInteractions(allDeclaredMocks(this)); }

	@Test
	public void should_get_person_list() {

		// setup
		final List<Person> expected = Collections.emptyList();
		when(personService.getList()).thenReturn(expected);

		// run test
		final List<Person> actual = personResource.getPersonList();

		// verify mocks / capture values
		verify(personService).getList();
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(expected, actual);

	}

	@Test
	public void should_get_persons_pet_list() {

		// setup
		final List<Pet> expected = Collections.emptyList();
		when(petService.getList(1L)).thenReturn(expected);

		// run test
		final List<Pet> actual = personResource.getPetList(1L);

		// verify mocks / capture values
		verify(petService).getList(1L);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(expected, actual);

	}


	@Test
	public void should_get_person() {

		// setup
		final Person expected = mock(Person.class);
		when(personService.get(1L)).thenReturn(expected);

		// run test
		final Person actual = personResource.get(1L);

		// verify mocks / capture values
		verify(personService).get(1L);

		// assert results
		assertEquals(expected, actual);

	}

	@Test(expected = NotFoundException.class)
	public void should_not_get_person() {

		// setup
		final Person expected = mock(Person.class);
		when(personService.get(1L)).thenReturn(null);

		// run test
		try {
			personResource.get(1L);
		} finally {
			// verify mocks / capture values
			verify(personService).get(1L);
		}
	}

	@Test
	public void should_create_person() {

		// setup
		final Person expected = mock(Person.class);
		when(personService.create(expected)).thenReturn(true);

		// run test
		final Person actual = personResource.create(expected);

		// verify mocks / capture values
		verify(personService).create(expected);

		// assert results
		assertEquals(expected, actual);

	}

	@Test(expected = NotFoundException.class)
	public void should_not_create_person() {

		// setup
		final Person expected = mock(Person.class);
		when(personService.create(expected)).thenReturn(false);

		// run test
		try {
			personResource.create(expected);
		} finally {

			// verify mocks / capture values
			verify(personService).create(expected);
		}

	}

	@Test
	public void should_update_person() {

		// setup
		final Person expected = mock(Person.class);
		when(personService.update(expected)).thenReturn(true);

		// run test
		final Person actual = personResource.update(1L, expected);

		// verify mocks / capture values
		verify(personService).update(expected);

		// assert results
		assertEquals(expected, actual);

	}

	@Test (expected = NotFoundException.class)
	public void should_fail_to_update_person() {

		// setup
		final Person expected = mock(Person.class);
		when(personService.update(expected)).thenReturn(false);

		// run test
		try {
			personResource.update(1L, expected);
		} finally {
			// verify mocks / capture values
			verify(personService).update(expected);
		}

	}

	@Test
	public void should_delete_person() {

		// setup
		when(personService.delete(1L)).thenReturn(Boolean.TRUE);
		// run test
		personResource.delete(1L);

		// verify mocks / capture values
		verify(personService).delete(1L);

	}

	@Test(expected = NotFoundException.class)
	public void should_fail_to_delete_person() {

		// setup
		when(personService.delete(1L)).thenReturn(Boolean.FALSE);
		// run test
		try {
			personResource.delete(1L);
		} finally {
			// verify mocks / capture values
			verify(personService).delete(1L);
		}

	}

}
