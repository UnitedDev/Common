package fr.kohei.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.kohei.common.api.CommonAPI;
import fr.kohei.common.cache.*;
import fr.kohei.common.messaging.Pidgin;
import fr.kohei.common.messaging.list.packets.ProfileUpdatePacket;
import fr.kohei.common.messaging.list.packets.PunishmentUpdatePacket;
import fr.kohei.common.messaging.list.packets.RankUpdatePacket;
import fr.kohei.common.messaging.list.packets.ReportUpdatePacket;
import lombok.Getter;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Getter
public class RedisProvider implements CommonAPI {
    public static RedisProvider redisProvider;

    public final Gson GSON = new GsonBuilder().create();

    public final ConcurrentMap<UUID, ProfileData> players;
    public final Queue<PunishmentData> punishments;
    public final Queue<Rank> ranks;
    public final Queue<Report> reports;

    public final RMap<UUID, String> playersGson;
    public final RSet<String> punishmentsGson;
    private final RSet<String> ranksGson;
    private final RSet<String> reportsGson;
    private final RedissonClient client;

    private final Pidgin messaging;
    private final ProfileData defaultProfile;
    private final Executor executor;
    private final ServerCache serverCache;

    public RedisProvider() {
        redisProvider = this;
        this.client = Redisson.create();
        this.messaging = new Pidgin("kohei-dev");

        this.playersGson = client.getMap("players");
        this.ranksGson = client.getSet("ranks");
        this.punishmentsGson = client.getSet("punishments");
        this.reportsGson = client.getSet("reports");
        this.serverCache = new ServerCache();

        this.players = new ConcurrentHashMap<>();
        this.punishments = new ConcurrentLinkedQueue<>();
        this.ranks = new ConcurrentLinkedQueue<>();
        this.reports = new ConcurrentLinkedQueue<>();
        this.executor = Executors.newScheduledThreadPool(3);

        ranksGson.readAll().forEach(s -> ranks.add(GSON.fromJson(s, Rank.class)));
        punishmentsGson.readAll().forEach(s -> punishments.add(GSON.fromJson(s, PunishmentData.class)));
        playersGson.readAllMap().forEach((uuid, s) -> players.put(uuid, GSON.fromJson(s, ProfileData.class)));
        reportsGson.readAll().forEach(s -> reports.add(GSON.fromJson(s, Report.class)));

        if (ranksGson.size() < Ranks.values().length) {
            ranksGson.clear();
            ranks.clear();

            for (Ranks ranks : Ranks.values()) {
                Rank rank = new Rank(ranks.name().toLowerCase(), ranks.getPower(), ranks.getPrefix(), ranks.getPrefix());
                ranksGson.add(GSON.toJson(rank));
                this.ranks.add(rank);
            }
        }

        defaultProfile = new ProfileData(getRank("default").get(), 0, 0, "fr");
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
    public Optional<Rank> getRank(String token) {
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
        punishmentsGson.addAsync(GSON.toJson(punishmentData));

        getMessaging().sendPacket(new PunishmentUpdatePacket(punishmentData, false));
    }

    @Override
    public void deletePunishment(PunishmentData punishmentData) {
        punishments.removeIf(pd -> pd.getPunishmentId().equals(punishmentData.getPunishmentId()));
        punishmentsGson.removeIf(s -> GSON.fromJson(s, PunishmentData.class).getPunishmentId().equals(punishmentData.getPunishmentId()));

        getMessaging().sendPacket(new PunishmentUpdatePacket(punishmentData, true));
    }

    public List<Rank> getRanks() {
        return new ArrayList<>(ranks);
    }

    public List<PunishmentData> getPunishments() {
        return new ArrayList<>(punishments);
    }

    @Override
    public void addReport(Report report) {
        reports.add(report);
        reportsGson.addAsync(GSON.toJson(report));

        getMessaging().sendPacket(new ReportUpdatePacket(report));
    }

    @Override
    public void updateReport(Report report) {
        reports.removeIf(r -> r.getReportId().equals(report.getReportId()));
        reportsGson.removeIf(s -> GSON.fromJson(s, Report.class).getReportId().equals(report.getReportId()));

        reports.add(report);
        reportsGson.addAsync(GSON.toJson(report));

        getMessaging().sendPacket(new ReportUpdatePacket(report));
    }

    public List<Report> getReports() {
        return new ArrayList<>(reports);
    }
}