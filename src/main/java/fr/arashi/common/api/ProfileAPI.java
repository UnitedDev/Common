package fr.arashi.common.api;

import fr.arashi.common.cache.ProfileData;

import java.util.UUID;

public interface ProfileAPI {

	/**
	 * @param uuid of the player
	 * @return the players data
	 */
	ProfileData getProfile(UUID uuid);

	/**
	 * Save the data on the redis's database
	 *
	 * @param uuid of the player
	 * @param data of the player
	 */
	void saveProfile(UUID uuid, ProfileData data);
}
