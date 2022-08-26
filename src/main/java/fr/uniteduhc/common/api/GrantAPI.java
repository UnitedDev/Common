package fr.uniteduhc.common.api;

import fr.uniteduhc.common.cache.rank.Grant;

import java.util.List;
import java.util.UUID;

public interface GrantAPI {

    List<Grant> getGrants(UUID uuid);

    Grant getGrantFromId(UUID grantId);

    Grant newDefaultGrant(UUID player);

    void refreshGrants();

    void addGrant(Grant grant);

    void updateGrant(Grant grant);

    void removeGrant(Grant grant);

}
