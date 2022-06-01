package fr.kohei.common.api;

import fr.kohei.common.RedisProvider;
import fr.kohei.common.cache.BucketServerCache;
import fr.kohei.common.cache.ServerCache;
import fr.kohei.common.messaging.Pidgin;

import java.util.UUID;

public interface CommonAPI extends ProfileAPI, RankAPI, PunishmentAPI {
	/**
	 * Create a CacheMap with player uuid as key,
	 * Use it for your custom game or data
	 *
	 * Employ it as a singleton function
	 *
	 * @param token is a unique key that will be used to recognize your cache. The bucket's data will be deleted from the map if the whole player is deleted.
	 * @param <T> is the data type of the map, use your custom object.
	 * @return the cache map object.
	 */
	<T> BucketServerCache<T> createBucketServerCache(String token);

	/**
	 * Remove every data from a player in the common map (Profiles) and in the custom map (see function below)
	 *
	 * @param uuid is the player uuid
	 */
	void deleteAllDataFromAPlayer(UUID uuid);

	ServerCache getServerCache();

	Pidgin getMessaging();

	/**
	 * Use as singleton to make the link to the database
	 *
	 * @return the instance of the api
	 */
	static CommonAPI create() {
		return new RedisProvider();
	}
}
