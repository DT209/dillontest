package com.hoopladigital.service;

import java.util.Collections;
import java.util.List;

import com.hoopladigital.bean.Pet;
import com.hoopladigital.mapper.PetMapper;
import com.hoopladigital.test.AbstractTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static com.hoopladigital.test.MockHelper.allDeclaredMocks;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class PetServiceTest extends AbstractTest {

	@Mock
	private PetMapper petMapper;
	private PetService petService;

	@Before
	public void beforePersonServiceTest() {
		petService = new PetService(petMapper);
	}

	@After public void afterPersonServiceTest() { verifyNoMoreInteractions(allDeclaredMocks(this)); }

	@Test
	public void should_get_pet_list() {

		// setup
		final List<Pet> expected = Collections.emptyList();
		when(petMapper.getList(Long.MAX_VALUE)).thenReturn(expected);

		// run test
		final List<Pet> actual = petService.getList(Long.MAX_VALUE);

		// verify mocks / capture values
		verify(petMapper).getList(Long.MAX_VALUE);

		// assert results
		assertEquals(expected, actual);

	}

	@Test
	public void should_get_pet() {

		// setup
		final Pet expected = new Pet();
		when(petMapper.get(1L)).thenReturn(expected);

		// run test
		final Pet actual = petService.get(1L);

		// verify mocks / capture values
		verify(petMapper).get(1L);

		// assert results
		assertEquals(expected, actual);

	}

	@Test
	public void should_not_get_pet() {

		// setup
		when(petMapper.get(1L)).thenReturn(null);

		// run test
		final Pet actual = petService.get(1L);

		// verify mocks / capture values
		verify(petMapper).get(1L);

		// assert results
		assertNull(actual);

	}

	@Test
	public void should_create_pet() {

		// setup
		final Pet expected = new Pet();
		when(petMapper.create(expected)).thenReturn(1);

		// run test
		final boolean actual = petService.create(expected);

		// verify mocks / capture values
		verify(petMapper).create(expected);

		// assert results
		assertTrue(actual);

	}

	@Test
	public void should_not_create_pet() {

		// setup
		final Pet expected = new Pet();
		when(petMapper.create(expected)).thenReturn(0);

		// run test
		final boolean actual = petService.create(expected);

		// verify mocks / capture values
		verify(petMapper).create(expected);

		// assert results
		assertFalse(actual);

	}

	@Test
	public void should_update_pet() {

		// setup
		final Pet expected = new Pet();
		when(petMapper.update(expected)).thenReturn(1);

		// run test
		final boolean actual = petService.update(expected);

		// verify mocks / capture values
		verify(petMapper).update(expected);

		// assert results
		assertTrue(actual);

	}
	@Test
	public void should_not_update_pet() {

		// setup
		final Pet expected = new Pet();
		when(petMapper.update(expected)).thenReturn(0);

		// run test
		final boolean actual = petService.update(expected);

		// verify mocks / capture values
		verify(petMapper).update(expected);

		// assert results
		assertFalse(actual);

	}

	@Test
	public void should_delete_pet() {

		// setup
		when(petMapper.delete(Long.MAX_VALUE)).thenReturn(1);
		// run test
		final boolean actual = petService.delete(Long.MAX_VALUE);

		// verify mocks / capture values
		verify(petMapper).delete(Long.MAX_VALUE);

		assertTrue(actual);
	}

	@Test
	public void should_not_delete_pet() {

		// setup
		when(petMapper.delete(Long.MAX_VALUE)).thenReturn(0);
		// run test
		final boolean actual = petService.delete(Long.MAX_VALUE);

		// verify mocks / capture values
		verify(petMapper).delete(Long.MAX_VALUE);

		assertFalse(actual);
	}
}
