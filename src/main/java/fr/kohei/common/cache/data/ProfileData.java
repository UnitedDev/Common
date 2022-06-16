package fr.kohei.common.cache.data;

import fr.kohei.common.CommonProvider;
import fr.kohei.common.cache.rank.Grant;
import fr.kohei.common.cache.rank.Rank;
import fr.kohei.common.utils.GrantComparator;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class ProfileData implements Serializable {
    UUID uuid;
    private String displayName;
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
    private Date lastLogin;
    private long playTime;
    private final List<String> ips;
    private String link;

    private final HashMap<String, Object> serversData;

    public ProfileData(UUID uuid, int coins, int hosts, String language) {
        this.uuid = uuid;
        this.displayName = "";
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
        this.link = "";
        this.lastLogin = new Date();
    }

    public Rank getRank() {
        return getGrants().stream().filter(Grant::isActive).min(new GrantComparator()).orElse(CommonProvider.getInstance().newDefaultGrant(uuid)).getRank();
    }

    public List<Grant> getGrants() {
        return CommonProvider.getInstance().getGrants(this.uuid);
    }

    public List<Grant> getActiveGrants() {
        return getGrants().stream().filter(Grant::isActive).collect(Collectors.toList());
    }
}
