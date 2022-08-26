package fr.uniteduhc.common.api;

import fr.uniteduhc.common.CommonProvider;
import fr.uniteduhc.common.cache.server.ServerCache;
import fr.uniteduhc.common.utils.messaging.Pidgin;

import java.util.UUID;

public interface CommonAPI extends ProfileAPI, RankAPI, PunishmentAPI, ReportAPI, WarnAPI, GrantAPI {
	void deleteAllDataFromAPlayer(UUID uuid);

	ServerCache getServerCache();

	Pidgin getMessaging();

	static CommonAPI create() {
		return new CommonProvider();
	}
}
