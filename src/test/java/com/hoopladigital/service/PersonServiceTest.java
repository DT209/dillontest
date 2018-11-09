package com.hoopladigital.service;

import com.hoopladigital.bean.Person;
import com.hoopladigital.mapper.PersonMapper;
import com.hoopladigital.test.AbstractTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;

import static com.hoopladigital.test.MockHelper.allDeclaredMocks;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class PersonServiceTest extends AbstractTest {

	@Mock
	private PersonMapper personMapper;
	private PersonService personService;

	@Before
	public void beforePersonServiceTest() {
		personService = new PersonService(personMapper);
	}

	@After public void afterPersonServiceTest() { verifyNoMoreInteractions(allDeclaredMocks(this)); }

	@Test
	public void should_get_person_list() {

		// setup
		final List<Person> expected = Collections.emptyList();
		when(personMapper.getList()).thenReturn(expected);

		// run test
		final List<Person> actual = personService.getList();

		// verify mocks / capture values
		verify(personMapper).getList();

		// assert results
		assertEquals(expected, actual);

	}

	@Test
	public void should_get_person() {

		// setup
		final Person expected = new Person();
		when(personMapper.get(1L)).thenReturn(expected);

		// run test
		final Person actual = personService.get(1L);

		// verify mocks / capture values
		verify(personMapper).get(1L);

		// assert results
		assertEquals(expected, actual);

	}
	@Test
	public void should_not_get_person() {

		// setup

		when(personMapper.get(1L)).thenReturn(null);

		// run test
		final Person actual = personService.get(1L);

		// verify mocks / capture values
		verify(personMapper).get(1L);

		// assert results
		assertNull(actual);

	}

	@Test
	public void should_create_person() {

		// setup
		final Person expected = new Person();
		when(personMapper.create(expected)).thenReturn(1);

		// run test
		boolean actual = personService.create(expected);

		// verify mocks / capture values
		verify(personMapper).create(expected);

		// assert results
		assertTrue(actual);

	}

	@Test
	public void should_not_create_person() {

		// setup
		final Person expected = new Person();
		when(personMapper.create(expected)).thenReturn(0);

		// run test
		boolean actual = personService.create(expected);

		// verify mocks / capture values
		verify(personMapper).create(expected);

		// assert results
		assertFalse(actual);

	}

	@Test
	public void should_update_person() {

		// setup
		final Person expected = new Person();
		when(personMapper.update(expected)).thenReturn(1);

		// run test
		boolean actual = personService.update(expected);

		// verify mocks / capture values
		verify(personMapper).update(expected);

		// assert results
		assertTrue(actual);

	}

	@Test
	public void should_not_update_person() {

		// setup
		final Person expected = new Person();
		when(personMapper.update(expected)).thenReturn(0);

		// run test
		boolean actual = personService.update(expected);

		// verify mocks / capture values
		verify(personMapper).update(expected);

		// assert results
		assertFalse(actual);

	}

	@Test
	public void should_delete_person() {

		// setup
		when(personMapper.delete(Long.MAX_VALUE)).thenReturn(1);
		// run test
		boolean actual = personService.delete(Long.MAX_VALUE);

		// verify mocks / capture values
		verify(personMapper).delete(Long.MAX_VALUE);

		// assert results
		assertTrue(actual);

	}

	@Test
	public void should_not_delete_person() {

		// setup
		when(personMapper.delete(Long.MAX_VALUE)).thenReturn(0);
		// run test
		boolean actual = personService.delete(Long.MAX_VALUE);

		// verify mocks / capture values
		verify(personMapper).delete(Long.MAX_VALUE);

		// assert results
		assertFalse(actual);

	}
}
