package com.hoopladigital.resource;

import java.util.Collections;
import java.util.List;
import javax.ws.rs.NotFoundException;

import com.hoopladigital.bean.Pet;
import com.hoopladigital.service.PetService;
import com.hoopladigital.test.AbstractTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static com.hoopladigital.test.MockHelper.allDeclaredMocks;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class PetResourceTest extends AbstractTest {

	@Mock
	private PetService petService;

	private PetResource petResource;

	@Before
	public void beforePetResourceTest() {
		petResource = new PetResource(petService);
	}

	@After
	public void afterPetResourceTest(){ verifyNoMoreInteractions(allDeclaredMocks(this)); }

	@Test
	public void should_get_person() {

		// setup
		final Pet expected = mock(Pet.class);
		when(petService.get(1L)).thenReturn(expected);

		// run test
		final Pet actual = petResource.get(1L);

		// verify mocks / capture values
		verify(petService).get(1L);

		// assert results
		assertEquals(expected, actual);

	}

	@Test(expected = NotFoundException.class)
	public void should_not_get_person() {

		// setup
		final Pet expected = mock(Pet.class);
		when(petService.get(1L)).thenReturn(null);

		// run test
		try {
			petResource.get(1L);
		} finally {
			// verify mocks / capture values
			verify(petService).get(1L);
		}
	}

	@Test
	public void should_create_person() {

		// setup
		final Pet expected = mock(Pet.class);
		when(petService.create(expected)).thenReturn(true);

		// run test
		final Pet actual = petResource.create(expected);

		// verify mocks / capture values
		verify(petService).create(expected);

		// assert results
		assertEquals(expected, actual);

	}

	@Test(expected = NotFoundException.class)
	public void should_not_create_person() {

		// setup
		final Pet expected = mock(Pet.class);
		when(petService.create(expected)).thenReturn(false);

		// run test
		try {
			petResource.create(expected);
		} finally {

			// verify mocks / capture values
			verify(petService).create(expected);
		}

	}

	@Test
	public void should_update_person() {

		// setup
		final Pet expected = mock(Pet.class);
		when(petService.update(expected)).thenReturn(true);

		// run test
		final Pet actual = petResource.update(1L, expected);

		// verify mocks / capture values
		verify(petService).update(expected);

		// assert results
		assertEquals(expected, actual);

	}

	@Test (expected = NotFoundException.class)
	public void should_fail_to_update_person() {

		// setup
		final Pet expected = mock(Pet.class);
		when(petService.update(expected)).thenReturn(false);

		// run test
		try {
			petResource.update(1L, expected);
		} finally {
			// verify mocks / capture values
			verify(petService).update(expected);
		}

	}

	@Test
	public void should_delete_person() {

		// setup
		when(petService.delete(1L)).thenReturn(Boolean.TRUE);
		// run test
		petResource.delete(1L);

		// verify mocks / capture values
		verify(petService).delete(1L);

	}

	@Test(expected = NotFoundException.class)
	public void should_fail_to_delete_person() {

		// setup
		when(petService.delete(1L)).thenReturn(Boolean.FALSE);
		// run test
		try {
			petResource.delete(1L);
		} finally {
			// verify mocks / capture values
			verify(petService).delete(1L);
		}

	}

}
