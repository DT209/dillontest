package com.hoopladigital.service;

import com.hoopladigital.bean.Person;
import com.hoopladigital.mapper.PersonMapper;

import javax.inject.Inject;
import java.util.List;

public class PersonService {

	private final PersonMapper personMapper;

	@Inject
	public PersonService(final PersonMapper personMapper) {
		this.personMapper = personMapper;
	}

	/**
	 * @return empty list if not found
	 */
	public List<Person> getList() {
		return personMapper.getList();
	}

	/**
	 * @param id
	 * @return null if not found
	 */
	public Person get(long id) {
		return personMapper.get(id);
	}

	/**
	 * SIDE EFFECT: Person's ID is set at the end of this operation
	 * @param person
	 * @return true of create was successful, false otherwise
	 */
	public boolean create(Person person) {
		return 1 == personMapper.create(person);
	}

	/**
	 * @return true if update was successful, false otherwise
	 */
	public boolean update(Person person) {
		return 1 == personMapper.update(person);
	}

	/**
	 * @return true if delete was successful, false otherwise
	 */
	public boolean delete(long id) {
		return 1 == personMapper.delete(id);
	}
}
