package com.hoopladigital.service;

import java.util.List;
import javax.inject.Inject;

import com.hoopladigital.bean.Person;
import com.hoopladigital.bean.Pet;
import com.hoopladigital.mapper.PetMapper;

public class PetService {

	private final PetMapper petMapper;

	@Inject
	public PetService(final PetMapper petMapper) {
		this.petMapper = petMapper;
	}

	/**
	 * @return empty list if not found
	 */
	public List<Pet> getList(long id) {
		return petMapper.getList(id);
	}

	/**
	 * @param id
	 * @return null if not found
	 */
	public Pet get(long id) {
		return petMapper.get(id);
	}

	/**
	 * SIDE EFFECT: Pet's ID is set at the end of this operation
	 * @param pet
	 * @return true of create was successful, false otherwise
	 */
	public boolean create(Pet pet) {
		return 1 == petMapper.create(pet);
	}

	/**
	 * @return true if update was successful, false otherwise
	 */
	public boolean update(Pet pet) {
		return 1 == petMapper.update(pet);
	}

	/**
	 * @return true if delete was successful, false otherwise
	 */
	public boolean delete(long id) {
		return 1 == petMapper.delete(id);
	}
}
