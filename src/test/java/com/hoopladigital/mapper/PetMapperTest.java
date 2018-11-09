package com.hoopladigital.mapper;

import java.util.List;
import javax.inject.Inject;

import com.hoopladigital.bean.Pet;
import com.hoopladigital.test.AbstractMapperTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class PetMapperTest extends AbstractMapperTest {

	@Inject
	private PetMapper petMapper;
	@Inject
	private PersonMapper personMapper;


	@Test
	public void should_get_empty_pet_list() {
		// run test
		final List<Pet> petList = petMapper.getList(1L);

		// verify mocks / capture values

		// assert results
		assertEquals(0, petList.size());
	}

	@Test
	public void should_get_pet() {
		// run test
		final Pet pet = petMapper.get(1L);

		// verify mocks / capture values

		// assert results
		assertNull(pet);
	}

	@Test
	public void should_create_pet() {
		// setup
		Pet dog = makeDog(1L);

		// run test
		int insertedRecords = petMapper.create(dog);

		// verify mocks / capture values

		// assert results
		assertEquals(1, insertedRecords);
		assertNotEquals(Long.valueOf(0L), dog.getId());
		assertEquals(Long.valueOf(1L), dog.getPersonId());
		assertTrue(petMapper.getList(1L).toString(), petMapper.getList(1L).contains(dog));
	}

	@Test
	public void should_get_none_empty_pet_list() {
		// setup
		Pet dog = makeDog(1L);
		petMapper.create(dog);
		Pet cat = makeCat(1L);
		petMapper.create(cat);

		// run test
		List<Pet> list = petMapper.getList(1L);

		// verify mocks / capture values
		// assert results
		assertEquals(2,list.size());
		assertTrue(list.toString(), list.contains(dog));
		assertTrue(list.toString(), list.contains(cat));
	}

	@Test
	public void should_update_pet() {
		String name = Long.toString(System.currentTimeMillis());
		// setup
		Pet dog = makeDog(1L);
		petMapper.create(dog);
		dog.setName(name);

		// run test
		int numberOfUpdatedRecords = petMapper.update(dog);

		// verify mocks / capture values

		// assert results
		assertEquals(1, numberOfUpdatedRecords);
		assertEquals(dog.getName(), petMapper.get(dog.getId()).getName());
	}


	@Test
	public void should_delete_pet() {
		// setup
		Pet dog = makeDog(1L);
		petMapper.create(dog);

		// run test
		// verify mocks / capture values
		// assert results
		assertEquals(1, petMapper.delete(dog.getId()));
		assertEquals(0, petMapper.delete(dog.getId()));
		assertFalse(petMapper.getList(1L).contains(dog));
	}

	private Pet makeDog(long personId) {
		Pet dog = new Pet();
		dog.setName("Dog");
		dog.setPersonId(personId);
		return dog;
	}

	private Pet makeCat(long personId) {
		Pet cat = new Pet();
		cat.setName("Cat");
		cat.setPersonId(personId);
		return cat;
	}
}
