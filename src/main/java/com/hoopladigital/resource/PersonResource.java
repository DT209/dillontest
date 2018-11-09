package com.hoopladigital.resource;

import java.util.List;
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

@Path("/people")
@Produces("application/json")
@Consumes("application/json")
public class PersonResource {

	private final PersonService personService;
	private final PetService petService;

	@Inject
	public PersonResource(PersonService personService, PetService petService) {
		this.personService = personService;
		this.petService = petService;
	}

	@GET
	public List<Person> getPersonList() {
		return personService.getList();
	}

	@GET
	@Path("{id}")
	public Person get(@PathParam("id") long id) {
		Person person = personService.get(id);
		if (person == null) throw new NotFoundException("Could not find person with id " + id);
		return person;
	}

	@GET
	@Path("{id}/pets")
	public List<Pet> getPetList(@PathParam("id") long id) {
		return petService.getList(id);
	}

	@POST
	public Person create(Person person) {
		if (personService.create(person)) return person;
		throw new NotFoundException("Could not create person " + person);
	}

	@PUT
	@Path("{id}")
	public Person update(@PathParam("id") long id, Person person) {
		if(personService.update(person)) return person;
		throw new NotFoundException("Could not update person " + person);
	}

	@DELETE
	@Path("{id}")
	public void delete(@PathParam("id") long id) {
		if (!personService.delete(id)) throw new NotFoundException("Could not delete person with id " + id);
	}
}
