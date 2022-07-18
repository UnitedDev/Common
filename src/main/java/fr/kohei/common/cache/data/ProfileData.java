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
    private String displayName;
    private int coins, hosts;
    private String language;
    private List<String> silentPlayer;
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
    private boolean staff;
    private boolean vanish;
    private int box;

    private final HashMap<String, Object> serversData;

    public ProfileData() {
        this.displayName = "";
        this.coins = 0;
        this.hosts = 0;
        this.language = "fr";
        this.silentPlayer = new ArrayList<>();
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
        this.box = 0;
    }

    public Rank getRank() {
        return getGrants().stream().filter(Grant::isActive).max(new GrantComparator()).orElseGet(() -> CommonProvider.getInstance().newDefaultGrant(getUniqueId())).getRank();
    }

    public List<Grant> getGrants() {
        return CommonProvider.getInstance().getGrants(this.getUniqueId());
    }

    public List<Grant> getActiveGrants() {
        return getGrants().stream().filter(Grant::isActive).collect(Collectors.toList());
    }

    public UUID getUniqueId() {
        Map<UUID, ProfileData> profiles = new HashMap<>(CommonProvider.getInstance().getPlayers());
        return profiles.keySet().stream().filter(uuid -> profiles.get(uuid).getDisplayName().equalsIgnoreCase(displayName))
                .findFirst().orElse(null);
    }
}
