package com.hoopladigital.resource;

import java.util.List;
import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.hoopladigital.bean.Person;
import com.hoopladigital.bean.Pet;
import com.hoopladigital.service.PersonService;
import com.hoopladigital.service.PetService;

@Path("/pets")
@Produces("application/json")
@Consumes("application/json")
public class PetResource {

	private final PetService petService;

	@Inject
	public PetResource(final PetService petService) {
		this.petService = petService;
	}

	@GET
	@Path("{id}")
	public Pet get(@PathParam("id") long id) {
		Pet pet = petService.get(id);
		if (pet == null) throw new NotFoundException("Could not find pet with id " + id);
		return pet;
	}

	@POST
	public Pet create(Pet pet) {
		if (petService.create(pet)) return pet;
		throw new NotFoundException("Could not create pet " + pet);
	}

	@PUT
	@Path("{id}")
	public Pet update(@PathParam("id") long id, Pet pet) {
		if(petService.update(pet)) return pet;
		throw new NotFoundException("Could not pet person " + pet);
	}

	@DELETE
	@Path("{id}")
	public void delete(@PathParam("id") long id) {
		if (!petService.delete(id)) throw new NotFoundException("Could not delete pet with id " + id);
	}
}
