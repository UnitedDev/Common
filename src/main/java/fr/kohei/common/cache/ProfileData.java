package fr.kohei.common.cache;

import fr.kohei.common.RedisProvider;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class ProfileData implements Serializable {
    private String displayName;
    private String rankName;
    private int coins, hosts;
    private String language;
    private List<String> silentPlayer;
    private List<String> friends;
    private boolean friendRequests;
    private boolean privateMessages;
    private boolean notifications;
    private final HashMap<String, Integer> checkViolations;
    private Division division;
    private int experience;
    private long playTime;
    private final List<String> ips;
    private String link;

    private final HashMap<String, Object> serversData;

    public ProfileData(Rank rank, int coins, int hosts, String language) {
        this.rankName = rank.token();
        this.coins = coins;
        this.hosts = hosts;
        this.language = language;
        this.silentPlayer = new ArrayList<>();
        this.friends = new ArrayList<>();
        this.friendRequests = true;
        this.privateMessages = true;
        this.notifications = true;
        this.division = Division.AUCUNE;
        this.serversData = new HashMap<>();
        this.checkViolations = new HashMap<>();
        this.playTime = 1;
        this.ips = new ArrayList<>();
        this.link = null;
    }

    public Rank getRank() {
        return RedisProvider.redisProvider.getRanks().stream().filter(rank -> rank.token().equalsIgnoreCase(rankName)).findFirst().orElse(RedisProvider.redisProvider.getRank("default").get());
    }
}
