package fr.uniteduhc.common.utils.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import fr.uniteduhc.common.CommonProvider;
import fr.uniteduhc.common.cache.data.ProfileData;
import fr.uniteduhc.common.cache.data.PunishmentData;
import fr.uniteduhc.common.cache.data.Report;
import fr.uniteduhc.common.cache.data.Warn;
import fr.uniteduhc.common.cache.rank.Grant;
import fr.uniteduhc.common.cache.rank.Rank;
import fr.uniteduhc.common.utils.gson.GsonProvider;
import lombok.Getter;
import org.bson.Document;

import java.util.UUID;

@Getter
public class MongoManager {
    private final MongoClient mongoClient;
    private final MongoDatabase database;

    private final MongoCollection<Document> grantsCollection;
    private final MongoCollection<Document> profileCollection;
    private final MongoCollection<Document> ranksCollection;
    private final MongoCollection<Document> reportsCollection;
    private final MongoCollection<Document> punishmentsCollection;
    private final MongoCollection<Document> warnsCollection;

    public MongoManager(CommonProvider provider) {
        MongoShared mongoShard = new MongoShared("localhost", 27017);

            this.mongoClient = new MongoClient(new MongoClientURI(mongoShard.getURI()));
        this.database = mongoClient.getDatabase("united");

        this.grantsCollection = database.getCollection("grants");
        this.profileCollection = database.getCollection("profile");
        this.ranksCollection = database.getCollection("ranks");
        this.reportsCollection = database.getCollection("reports");
        this.punishmentsCollection = database.getCollection("punishments");
        this.warnsCollection = database.getCollection("warns");

        this.loadData(provider);
    }

    private void loadData(CommonProvider provider) {

        for (Document document : this.getGrantsCollection().find()) {
            provider.getGrants().add(
                    GsonProvider.GSON.fromJson(document.getString("data"), Grant.class)
            );
        }

        for (Document document : this.getProfileCollection().find()) {
            ProfileData profile = GsonProvider.GSON.fromJson(document.getString("data"), ProfileData.class);
            UUID uuid = UUID.fromString(document.getString("_id"));
//            if (System.currentTimeMillis() - profile.getLastLogin().getTime() <= 86400000L) {
                provider.getPlayers().put(uuid, profile);
//            }
        }

        for (Document document : this.getRanksCollection().find()) {
            provider.getRanks().add(
                    GsonProvider.GSON.fromJson(document.getString("data"), Rank.class)
            );
        }

        for (Document document : this.getReportsCollection().find()) {
            provider.getReports().add(
                    GsonProvider.GSON.fromJson(document.getString("data"), Report.class)
            );
        }

        for (Document document : this.getPunishmentsCollection().find()) {
            provider.getPunishments().add(
                    GsonProvider.GSON.fromJson(document.getString("data"), PunishmentData.class)
            );
        }

        for (Document document : this.getWarnsCollection().find()) {
            provider.getWarns().add(
                    GsonProvider.GSON.fromJson(document.getString("data"), Warn.class)
            );
        }
    }

}
