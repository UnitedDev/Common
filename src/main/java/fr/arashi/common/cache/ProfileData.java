package fr.arashi.common.cache;

import fr.arashi.common.RedisProvider;
import fr.arashi.common.api.CommonAPI;
import lombok.Getter;
import lombok.Setter;
import org.redisson.spring.support.RedissonGenericObjectDefinitionParser;

import java.io.Serializable;
import java.util.ArrayList;
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

	private Division division;
	private int experience;

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
	}

	public Rank getRank() {
		return RedisProvider.redisProvider.getRanks().stream().filter(rank -> rank.token().equalsIgnoreCase(rankName)).findFirst().orElse(RedisProvider.redisProvider.getRank("default").get());
	}
}
