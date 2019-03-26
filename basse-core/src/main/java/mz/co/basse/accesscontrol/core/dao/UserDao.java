package mz.co.basse.accesscontrol.core.dao;

import java.util.List;

import mz.co.basse.accesscontrol.core.model.User;

public interface UserDao {

	void create(User user);

	User findUser(String login);

	List<User> findUsers(Boolean active);

	void update(User user);

}
