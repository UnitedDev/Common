package fr.uniteduhc.common;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import fr.uniteduhc.common.api.CommonAPI;
import fr.uniteduhc.common.cache.data.ProfileData;
import fr.uniteduhc.common.cache.data.PunishmentData;
import fr.uniteduhc.common.cache.data.Report;
import fr.uniteduhc.common.cache.data.Warn;
import fr.uniteduhc.common.cache.rank.Grant;
import fr.uniteduhc.common.cache.rank.Rank;
import fr.uniteduhc.common.cache.rank.Ranks;
import fr.uniteduhc.common.cache.server.ServerCache;
import fr.uniteduhc.common.utils.TimeUtil;
import fr.uniteduhc.common.utils.gson.GsonProvider;
import fr.uniteduhc.common.utils.messaging.Pidgin;
import fr.uniteduhc.common.utils.messaging.list.packets.*;
import fr.uniteduhc.common.utils.mongo.MongoManager;
import lombok.Getter;
import org.bson.Document;

import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Getter
public class CommonProvider implements CommonAPI {
    @Getter
    public static CommonProvider instance;

    private final Pidgin messaging;
    private final ProfileData defaultProfile;
    private final ServerCache serverCache;
    private final MongoManager mongoManager;

    public final ConcurrentMap<UUID, ProfileData> players;
    public final Queue<PunishmentData> punishments;
    public final Queue<Rank> ranks;
    public final Queue<Report> reports;
    public final Queue<Warn> warns;
    public final Queue<Grant> grants;

    public CommonProvider() {
        instance = this;

        this.players = new ConcurrentHashMap<>();
        this.punishments = new ConcurrentLinkedQueue<>();
        this.ranks = new ConcurrentLinkedQueue<>();
        this.reports = new ConcurrentLinkedQueue<>();
        this.warns = new ConcurrentLinkedQueue<>();
        this.grants = new ConcurrentLinkedQueue<>();

        this.mongoManager = new MongoManager(this);
        this.messaging = new Pidgin("united-dev");
        this.serverCache = new ServerCache();

        if (Ranks.values().length > ranks.size()) {
            for (Ranks value : Ranks.values()) {
                Rank rank = new Rank(value.name().toLowerCase(), value.getPower(), value.getPrefix(), value.getPrefix());

                addRank(rank);
            }
        }

        this.defaultProfile = new ProfileData();
    }

    @Override
    public void deleteAllDataFromAPlayer(UUID uuid) {
        this.getPlayers().remove(uuid);
        this.getMongoManager().getProfileCollection().deleteOne(new Document("_id", uuid.toString()));
    }

    @Override
    public Rank getRank(String token) {
        return ranks.stream().filter(rk -> rk.token().equalsIgnoreCase(token)).findFirst().orElse(null);
    }

    @Override
    public void addRank(Rank rank) {
        ranks.add(rank);
        this.getMongoManager().getRanksCollection().insertOne(
                new Document("data", GsonProvider.GSON.toJson(rank))
                        .append("_id", rank.token())
        );

        getMessaging().sendPacket(new RankUpdatePacket(rank, false));
    }

    @Override
    public void removeRank(String token) {
        Rank rank = getRank(token);

        ranks.removeIf(rk -> (rk.token().equalsIgnoreCase(token)));
        this.getMongoManager().getRanksCollection().deleteOne(new Document("_id", token));

        getMessaging().sendPacket(new RankUpdatePacket(rank, true));
    }

    @Override
    public ProfileData getProfile(UUID uuid) {
        if (!players.containsKey(uuid)) {
            Document document = getMongoManager().getProfileCollection().find(Filters.eq("_id", uuid.toString())).first();
            if (document == null) {
                saveProfile(uuid, defaultProfile);
            } else {
                ProfileData profile = GsonProvider.GSON.fromJson(document.getString("data"), ProfileData.class);
                saveProfile(uuid, profile);
            }
        }

        return players.get(uuid);
    }


    @Override
    public void saveProfile(UUID uuid, ProfileData data) {
        players.put(uuid, data);
        this.getMongoManager().getProfileCollection().replaceOne(
                Filters.eq("_id", uuid.toString()),
                new Document("data", GsonProvider.GSON.toJson(data))
                        .append("_id", uuid.toString()),
                new ReplaceOptions().upsert(true)
        );

        getMessaging().sendPacket(new ProfileUpdatePacket(uuid, data));
    }

    @Override
    public List<PunishmentData> getPunishments(UUID uuid) {
        return getPunishments().stream().filter(punishmentData -> punishmentData.getPunished().equals(uuid)).collect(Collectors.toList());
    }

    @Override
    public void updatePunishment(PunishmentData punishmentData) {
        punishments.removeIf(pd -> pd.getPunishmentId().equals(punishmentData.getPunishmentId()));
        punishments.add(punishmentData);

        this.getMongoManager().getPunishmentsCollection().replaceOne(
                Filters.eq("_id", punishmentData.getPunishmentId()),
                new Document("data", GsonProvider.GSON.toJson(punishmentData))
                        .append("_id", punishmentData.getPunishmentId()),
                new ReplaceOptions().upsert(true)
        );

        getMessaging().sendPacket(new PunishmentUpdatePacket(punishmentData, false));
    }

    @Override
    public void deletePunishment(PunishmentData punishmentData) {
        punishments.removeIf(pd -> pd.getPunishmentId().equals(punishmentData.getPunishmentId()));
        this.getMongoManager().getPunishmentsCollection().deleteOne(new Document("_id", punishmentData.getPunishmentId()));

        getMessaging().sendPacket(new PunishmentUpdatePacket(punishmentData, true));
    }

    @Override
    public void addReport(Report report) {
        reports.add(report);
        this.getMongoManager().getReportsCollection().insertOne(
                new Document("data", GsonProvider.GSON.toJson(report))
                        .append("_id", report.getReportId())
        );

        getMessaging().sendPacket(new ReportUpdatePacket(report));
    }

    @Override
    public void updateReport(Report report) {
        reports.removeIf(r -> r.getReportId().equals(report.getReportId()));
        reports.add(report);

        CompletableFuture.runAsync(() -> this.getMongoManager().getReportsCollection().replaceOne(
                Filters.eq("_id", report.getReportId()),
                new Document("data", GsonProvider.GSON.toJson(report))
                        .append("_id", report.getReportId()),
                new ReplaceOptions().upsert(true)
        ));

        getMessaging().sendPacket(new ReportUpdatePacket(report));
    }

    @Override
    public List<Warn> getWarns(UUID uuid) {
        return this.getWarns().stream().filter(warn -> warn.getPlayerId().equals(uuid)).collect(Collectors.toList());
    }

    @Override
    public void addWarn(Warn warn) {
        warns.add(warn);

        CompletableFuture.runAsync(() ->
                this.getMongoManager().getWarnsCollection().insertOne(
                        new Document("data", GsonProvider.GSON.toJson(warn))
                                .append("_id", warn.getWarnId()))
        );
    }

    @Override
    public void removeWarn(Warn warn) {
        warns.removeIf(w -> w.getWarnId().equals(warn.getWarnId()));

        CompletableFuture.runAsync(() ->
                this.getMongoManager().getWarnsCollection().deleteOne(new Document("_id", warn.getWarnId()))
        );
    }

    @Override
    public List<Grant> getGrants(UUID uuid) {
        return getGrants().stream().filter(grant -> grant.getUser().equals(uuid)).collect(Collectors.toList());
    }

    @Override
    public Grant getGrantFromId(UUID grantId) {
        return getGrants().stream().filter(o -> o.getGrantId().equals(grantId)).findFirst().orElse(null);
    }

    @Override
    public Grant newDefaultGrant(UUID player) {
        Grant grant = new Grant(player,
                UUID.fromString("00000000-0000-0000-0000-000000000000"),
                getRank("default"),
                TimeUtil.PERMANENT
        );
        addGrant(grant);
        return grant;
    }

    @Override
    public void refreshGrants() {
        for (Grant o : getGrants()) {
            if (o.expired()) {
                o.setActive(false);
                updateGrant(o);
            }
        }
    }

    @Override
    public void addGrant(Grant grant) {
        grants.add(grant);

        getMessaging().sendPacket(new GrantUpdatePacket(grant, false));
        this.getMongoManager().getGrantsCollection().insertOne(
                new Document("data", GsonProvider.GSON.toJson(grant))
                        .append("_id", grant.getGrantId()));
    }

    @Override
    public void updateGrant(Grant grant) {
        grants.removeIf(o -> o.getGrantId().equals(grant.getGrantId()));
        grants.add(grant);

        getMessaging().sendPacket(new GrantUpdatePacket(grant, false));
        this.getMongoManager().getGrantsCollection().replaceOne(
                Filters.eq("_id", grant.getGrantId()),
                new Document("data", GsonProvider.GSON.toJson(grant))
                        .append("_id", grant.getGrantId()),
                new ReplaceOptions().upsert(true)
        );
    }

    @Override
    public void removeGrant(Grant grant) {
        grants.removeIf(o -> o.getGrantId().equals(grant.getGrantId()));

        getMessaging().sendPacket(new GrantUpdatePacket(grant, true));
        this.getMongoManager().getGrantsCollection().deleteOne(new Document("_id", grant.getGrantId()));
    }
}