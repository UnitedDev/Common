package fr.kohei.common.api;

import fr.kohei.common.CommonProvider;
import fr.kohei.common.cache.server.ServerCache;
import fr.kohei.common.utils.messaging.Pidgin;

import java.util.UUID;

public interface CommonAPI extends ProfileAPI, RankAPI, PunishmentAPI, ReportAPI {
	void deleteAllDataFromAPlayer(UUID uuid);

	ServerCache getServerCache();

	Pidgin getMessaging();

	static CommonAPI create() {
		return new CommonProvider();
	}
}
