package fr.arashi.common.api;

import fr.arashi.common.cache.Rank;

import java.util.Optional;
import java.util.Set;

public interface RankAPI {

	/**
	 * @return all the ranks who are stored on the database
	 */
	Set<Rank> getRanks();

	/**
	 * @param token is the token rank
	 * @return the first rank who have the token specified below. Empty optional if the rank doesn't exist
	 */
	Optional<Rank> getRank(String token);

	/**
	 * @param power is the permission power required
	 * @return all the rank who have as minimum this permission power
	 */
	Set<Rank> getRankUp(int power);

	/**
	 * @param rank the rank who'll go on the database
	 */
	void addRank(Rank rank);

	/**
	 * @param token is the token that
	 */
	void removeRank(String token);

}
