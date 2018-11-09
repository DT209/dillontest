package com.hoopladigital.mapper;

import java.util.List;
import javax.inject.Inject;

import com.hoopladigital.bean.Person;
import com.hoopladigital.test.AbstractMapperTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class PersonMapperTest extends AbstractMapperTest {

	@Inject
	private PersonMapper personMapper;

	@Test
	public void should_get_person_list() throws Exception {
		// run test
		final List<Person> personList = personMapper.getList();

		// verify mocks / capture values

		// assert results
		assertEquals(10, personList.size());
		beanTestHelper.diffBeans(makeGeorge(), personList.get(0));

	}

	@Test
	public void should_get_person() throws Exception {
		// run test
		final Person person = personMapper.get(1L);

		// verify mocks / capture values

		// assert results
		assertNotNull(person);
		beanTestHelper.diffBeans(makeGeorge(), person);
	}

	@Test
	public void should_not_find_person() {
		// run test
		final Person person = personMapper.get(Long.MAX_VALUE);

		// verify mocks / capture values

		// assert results
		assertNull(person);
	}

	@Test
	public void should_create_person() {
		// setup
		Person james = makeJames();

		// run test
		int insertedRecords = personMapper.create(james);

		// verify mocks / capture values

		// assert results
		assertEquals(1, insertedRecords);
		assertNotEquals(Long.valueOf(0L), james.getId());
		assertTrue(personMapper.getList().contains(james));
	}

	@Test
	public void should_update_person() {
		// setup
		final Person james = makeJames();
		personMapper.create(james);

		james.setMiddleName("K");

		// run test

		int numberOfUpdatedRecords = personMapper.update(james);

		// verify mocks / capture values

		// assert results
		assertEquals(1, numberOfUpdatedRecords);
		assertEquals(james, personMapper.get(james.getId()));
	}

	@Test
	public void should_delete_person() {
		// setup
		final Person james = makeJames();
		personMapper.create(james);

		// run test
		// verify mocks / capture values
		// assert results
		assertEquals(1, personMapper.delete(james.getId()));
		assertEquals(0, personMapper.delete(james.getId()));
		assertFalse(personMapper.getList().contains(james));
	}

	private Person makeJames() {
		Person james = new Person();
		james.setFirstName("James");
		james.setMiddleName("Knox");
		james.setLastName("Polk");
		return james;
	}

	private Person makeGeorge() {
		Person george = new Person();
		george.setId(1L);
		george.setFirstName("George");
		george.setLastName("Washington");
		return george;
	}

}
