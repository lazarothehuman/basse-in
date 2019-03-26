package mz.co.basse.accesscontrol.core.dao;

import java.util.List;

import mz.co.basse.accesscontrol.core.model.Profile;

public interface ProfileDao {

	void create(Profile profile);

	List<Profile> findProfiles(Boolean active);

	void update(Profile profile);

	Profile findProfile(String name);

}
