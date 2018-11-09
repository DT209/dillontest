package com.hoopladigital.mapper;

import com.hoopladigital.bean.Person;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface PersonMapper {

	@Select("SELECT id, firstName, middleName, lastName FROM Person ORDER BY id")
	List<Person> getList();

	/**
	 * @param id of Person object to get
	 */
	@Select("SELECT id, firstName, middleName, lastName FROM Person WHERE id = #{id}")
	Person get(long id);

	/**
	 * <b>SIDE EFFECT</b>: "setId" will be called with the new ID of the Person.
	 * @param person
	 * @return number of records inserted
	 */
	@Insert("INSERT into Person(firstName, middleName, lastName) VALUES(#{firstName}, #{middleName}, #{lastName})")
	@Options(useGeneratedKeys = true)
	int create(Person person);

	/**
	 * @param person
	 * @return number of records updated
	 */
	@Update("UPDATE Person SET firstName=#{firstName}, middleName =#{middleName}, lastName =#{lastName} WHERE id =#{id}")
	int update(Person person);

	/**
	 * @param id of Person object to delete
	 * @return number of records deleted
	 */
	@Delete("DELETE FROM Person WHERE id =#{id}")
	int delete(long id);
}
