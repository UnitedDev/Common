package fr.kohei.common.api;

import fr.kohei.common.cache.data.ProfileData;

import java.util.UUID;

public interface ProfileAPI {
	ProfileData getProfile(UUID uuid);

	void saveProfile(UUID uuid, ProfileData data);
}
