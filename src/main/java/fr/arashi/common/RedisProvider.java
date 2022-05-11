package fr.arashi.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.arashi.common.api.CommonAPI;
import fr.arashi.common.cache.BucketServerCache;
import fr.arashi.common.cache.ProfileData;
import fr.arashi.common.cache.Rank;
import lombok.Getter;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class RedisProvider implements CommonAPI {

	public static RedisProvider redisProvider;

	public final Gson GSON = new GsonBuilder().create();

	public final RMap<UUID, String> playersGson;
	private final Set<Rank> ranks;
	private final RSet<String> ranksGson;
	private final RedissonClient client;

	private final ProfileData defaultProfile;

	public RedisProvider() {

		redisProvider = this;
		this.client = Redisson.create();

		this.playersGson = client.getMap("players");
		this.ranksGson = client.getSet("ranks");
		this.ranks = new HashSet<>();

		ranksGson.readAll().forEach(s -> ranks.add(GSON.fromJson(s, Rank.class)));

		Optional<Rank> defaultRank = ranks.stream().filter(tk -> tk.token().equalsIgnoreCase("default")).findFirst();

		if (!defaultRank.isPresent()) {
			System.out.println("Couldn't find the default rank creating it...");
			Rank rank = new Rank("default", 0, "&7", "&7");
			addRank(rank);
			this.defaultProfile = new ProfileData(rank, 0, 0, "fr-FR");
		} else {
			this.defaultProfile = new ProfileData(defaultRank.get(), 0, 0, "fr-FR");
		}

	}

	@Override
	public <T> BucketServerCache<T> createBucketServerCache(String token) {
		return new BucketServerCache<>(client.getBucket(token));
	}

	@Override
	public void deleteAllDataFromAPlayer(UUID uuid) {
		playersGson.remove(uuid);
	}

	@Override
	public Set<Rank> getRanks() {
		return ranks;
	}

	@Override
	public Optional<Rank> getRank(String token){
		return ranks.stream().filter(rk -> rk.token().equalsIgnoreCase(token)).findFirst();
	}

	@Override
	public Set<Rank> getRankUp(int minimum){
		return ranks.stream().filter(rk -> rk.permissionPower() >= minimum).collect(Collectors.toSet());
	}

	@Override
	public void addRank(Rank rank) {
		ranks.add(rank);
		ranksGson.add(GSON.toJson(rank));
	}

	@Override
	public void removeRank(String token) {
		ranks.removeIf(rk -> (rk.token().equalsIgnoreCase(token)));
		ranksGson.removeIf(rk -> (GSON.fromJson(rk, Rank.class).token().equalsIgnoreCase(token)));
	}

	@Override
	public ProfileData getProfile(UUID uuid) {
		if (!playersGson.containsKeyAsync(uuid).getNow()) {
			playersGson.putAsync(uuid, GSON.toJson(defaultProfile));
		}

		return GSON.fromJson(playersGson.getAsync(uuid).getNow(), ProfileData.class);
	}

	@Override
	public void saveProfile(UUID uuid, ProfileData data){
		playersGson.putAsync(uuid, GSON.toJson(data));
	}
}