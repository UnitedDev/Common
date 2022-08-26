package fr.uniteduhc.common.api;

import fr.uniteduhc.common.cache.data.ProfileData;

import java.util.UUID;

public interface ProfileAPI {
	ProfileData getProfile(UUID uuid);

	void saveProfile(UUID uuid, ProfileData data);
}
