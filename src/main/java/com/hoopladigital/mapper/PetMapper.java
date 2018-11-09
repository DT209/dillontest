package com.hoopladigital.mapper;

import java.util.List;

import com.hoopladigital.bean.Person;
import com.hoopladigital.bean.Pet;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PetMapper {

	@Select("SELECT id, personId, name FROM Pet WHERE personId = #{id} ORDER BY id")
	List<Pet> getList(long id);

	/**
	 * @param id of Pet object to get
	 */
	@Select("SELECT id, personId, name FROM Pet WHERE id = #{id}")
	Pet get(long id);

	/**
	 * <b>SIDE EFFECT</b>: "setId" will be called with the new ID of the Pet.
	 * @return number of records inserted
	 */
	@Insert("INSERT into Pet(personId, name) VALUES(#{personId}, #{name})")
	@Options(useGeneratedKeys = true)
	int create(Pet pet);

	/**
	 * @return number of records updated
	 */
	@Update("UPDATE Pet SET personId=#{personId}, name =#{name} WHERE id =#{id}")
	int update(Pet pet);

	/**
	 * @param id of Pet object to delete
	 * @return number of records deleted
	 */
	@Delete("DELETE FROM Pet WHERE id =#{id}")
	int delete(long id);
}
