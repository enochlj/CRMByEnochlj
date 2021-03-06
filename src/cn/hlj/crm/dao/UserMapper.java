package cn.hlj.crm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cn.hlj.crm.entity.User;

public interface UserMapper {

//	@Select("SELECT u.id,u.name,password,u.enabled,salt,r.name as \"role.name\" "
//			+ "FROM users u LEFT OUTER JOIN roles r ON u.role_id=r.id WHERE u.name=#{name}")
	User getByName(@Param("name") String name);

	// 获取总的记录数
	@Select("SELECT count(id) FROM users")
	long getTotalElements();

	// 获取当前页面的内容
	@Select("SELECT * FROM" + " (SELECT rownum rn,id,name,enabled FROM users) t "
			+ "WHERE t.rn>=#{firstIndex} AND t.rn<#{endIndex}")
	List<User> getContent(@Param("firstIndex") int fromIndex, @Param("endIndex") int endIndex);

	@Insert("INSERT INTO users(id,name,password,role_id,enabled) "
			+ "VALUES(crm_seq.nextval,#{name},#{password},#{role.id},#{enabled})")
	void save(User user);

	@Delete("DELETE FROM users WHERE id=#{id}")
	void delete(@Param("id") Long id);

	@Select("SELECT u.id,u.name,password,role_id \"role.id\",u.enabled FROM users u "
			+ "LEFT OUTER JOIN roles r ON u.role_id=r.id " + "WHERE u.id=#{id}")
	User get(@Param("id") Long id);

	@Update("UPDATE users " + "SET name=#{name},password=#{password},role_id=#{role.id},enabled=#{enabled} "
			+ "WHERE id=#{id}")
	void update(User user);

	// ------------------------------------------带查询条件------------------------------------------------
	long getTotalElementsWithConditions(Map<String, Object> params);

	List<User> getContentWithConditions(Map<String, Object> params);
	// ------------------------------------------带查询条件------------------------------------------------

	// 获取所有用户
	@Select("SELECT id,name,password,enabled " + "FROM users")
	List<User> getAll();

}
