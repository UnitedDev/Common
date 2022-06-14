package fr.kohei.common.utils.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import fr.kohei.common.CommonProvider;
import fr.kohei.common.cache.data.ProfileData;
import fr.kohei.common.cache.data.PunishmentData;
import fr.kohei.common.cache.data.Report;
import fr.kohei.common.cache.rank.Rank;
import fr.kohei.common.utils.gson.GsonProvider;
import lombok.Getter;
import org.bson.Document;

import java.util.UUID;

@Getter
public class MongoManager {
    private final MongoClient mongoClient;
    private final MongoDatabase database;

    private final MongoCollection<Document> profileCollection;
    private final MongoCollection<Document> ranksCollection;
    private final MongoCollection<Document> reportsCollection;
    private final MongoCollection<Document> punishmentsCollection;

    public MongoManager(CommonProvider provider) {
        this.mongoClient = new MongoClient(new MongoClientURI(new MongoShard("localhost", 27017).getURI()));
        this.database = mongoClient.getDatabase("kohei");

        this.profileCollection = database.getCollection("profile");
        this.ranksCollection = database.getCollection("ranks");
        this.reportsCollection = database.getCollection("reports");
        this.punishmentsCollection = database.getCollection("punishments");

        this.loadData(provider);
    }

    private void loadData(CommonProvider provider) {

        for (Document document : this.getProfileCollection().find()) {
            provider.getPlayers().put(
                    UUID.fromString(document.getString("_id")),
                    GsonProvider.GSON.fromJson(document.getString("data"), ProfileData.class)
            );
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
    }

}
