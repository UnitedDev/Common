package fr.kohei.common.api;

import fr.kohei.common.cache.Rank;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RankAPI {

	/**
	 * @return all the ranks who are stored on the database
	 */
	List<Rank> getRanks();

	/**
	 * @param token is the token rank
	 * @return the first rank who have the token specified below. Empty optional if the rank doesn't exist
	 */
	Optional<Rank> getRank(String token);

	/**
	 * @param rank the rank who'll go on the database
	 */
	void addRank(Rank rank);

	/**
	 * @param token is the token that
	 */
	void removeRank(String token);

}
