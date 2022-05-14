package fr.kohei.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.kohei.common.api.CommonAPI;
import fr.kohei.common.cache.BucketServerCache;
import fr.kohei.common.cache.ProfileData;
import fr.kohei.common.cache.PunishmentData;
import fr.kohei.common.cache.Rank;
import fr.kohei.common.messaging.Pidgin;
import fr.kohei.common.messaging.list.packets.ProfileUpdatePacket;
import fr.kohei.common.messaging.list.packets.PunishmentUpdatePacket;
import fr.kohei.common.messaging.list.packets.RankUpdatePacket;
import lombok.Getter;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import redis.clients.jedis.JedisPool;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class RedisProvider implements CommonAPI {
	public static RedisProvider redisProvider;

	public final Gson GSON = new GsonBuilder().create();

	public final HashMap<UUID, ProfileData> players;
	public final List<PunishmentData> punishments;
	public final List<Rank> ranks;

	public final RMap<UUID, String> playersGson;
	public final RSet<String> punishmentsGson;
	private final RSet<String> ranksGson;
	private final RedissonClient client;

	private final Pidgin messaging;
	private final ProfileData defaultProfile;

	public RedisProvider() {
		redisProvider = this;
		this.client = Redisson.create();
		this.messaging = new Pidgin("kohei-dev");

		this.playersGson = client.getMap("players");
		this.ranksGson = client.getSet("ranks");
		this.punishmentsGson = client.getSet("punishments");

		this.players = new HashMap<>();
		this.punishments = new ArrayList<>();
		this.ranks = new ArrayList<>();

		ranksGson.readAll().forEach(s -> ranks.add(GSON.fromJson(s, Rank.class)));
		punishmentsGson.readAll().forEach(s -> punishments.add(GSON.fromJson(s, PunishmentData.class)));
		playersGson.readAllMap().forEach((uuid, s) -> players.put(uuid, GSON.fromJson(s, ProfileData.class)));

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
	public Optional<Rank> getRank(String token){
		return ranks.stream().filter(rk -> rk.token().equalsIgnoreCase(token)).findFirst();
	}

	@Override
	public void addRank(Rank rank) {
		ranks.add(rank);
		ranksGson.addAsync(GSON.toJson(rank));

		getMessaging().sendPacket(new RankUpdatePacket(rank, false));
	}

	@Override
	public void removeRank(String token) {
		Rank rank = getRank(token).orElse(null);

		ranks.removeIf(rk -> (rk.token().equalsIgnoreCase(token)));
		ranksGson.removeIf(rk -> (GSON.fromJson(rk, Rank.class).token().equalsIgnoreCase(token)));

		getMessaging().sendPacket(new RankUpdatePacket(rank, true));
	}

	@Override
	public ProfileData getProfile(UUID uuid) {
		if (!players.containsKey(uuid)) {
			saveProfile(uuid, defaultProfile);
		}

		return players.get(uuid);
	}

	@Override
	public void saveProfile(UUID uuid, ProfileData data) {
		players.put(uuid, data);
		playersGson.putAsync(uuid, GSON.toJson(data));

		getMessaging().sendPacket(new ProfileUpdatePacket(uuid, data));
	}

	@Override
	public List<PunishmentData> getPunishments(UUID uuid) {
		return getPunishments().stream().filter(punishmentData -> punishmentData.getPunished().equals(uuid)).collect(Collectors.toList());
	}

	@Override
	public void updatePunishment(PunishmentData punishmentData) {
		punishments.removeIf(pd -> pd.getPunishmentId().equals(punishmentData.getPunishmentId()));
		punishmentsGson.removeIf(s -> GSON.fromJson(s, PunishmentData.class).getPunishmentId().equals(punishmentData.getPunishmentId()));

		punishments.add(punishmentData);
		punishmentsGson.add(GSON.toJson(punishmentData));

		getMessaging().sendPacket(new PunishmentUpdatePacket(punishmentData, false));
	}

	@Override
	public void deletePunishment(PunishmentData punishmentData) {
		punishments.removeIf(pd -> pd.getPunishmentId().equals(punishmentData.getPunishmentId()));
		punishmentsGson.removeIf(s -> GSON.fromJson(s, PunishmentData.class).getPunishmentId().equals(punishmentData.getPunishmentId()));

		getMessaging().sendPacket(new PunishmentUpdatePacket(punishmentData, true));
	}
}