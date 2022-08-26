package fr.uniteduhc.common.api;

import fr.uniteduhc.common.cache.rank.Rank;

import java.util.Queue;

public interface RankAPI {
	Queue<Rank> getRanks();

	Rank getRank(String token);

	void addRank(Rank rank);

	void removeRank(String token);
}
