package fr.kohei.common.api;

import fr.kohei.common.cache.rank.Rank;

import java.util.List;
import java.util.Optional;
import java.util.Queue;

public interface RankAPI {
	Queue<Rank> getRanks();

	Rank getRank(String token);

	void addRank(Rank rank);

	void removeRank(String token);
}
