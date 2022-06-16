package fr.kohei.common.cache.rank;


import fr.kohei.common.CommonProvider;
import fr.kohei.common.utils.TimeUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter @Setter
public class Grant implements Serializable {

    private final UUID grantId;
    private String rank;

    private UUID user, issuer;
    private boolean active;
    private long duration, executedAt;

    public Grant(UUID user, UUID issuer, Rank rank, long duration) {
        this.grantId = UUID.randomUUID();
        this.user = user;
        this.issuer = issuer;
        this.rank = rank.getToken();
        this.duration = duration;
        this.active = true;
        this.executedAt = System.currentTimeMillis();
    }

    public Rank getRank() {
        return CommonProvider.getInstance().getRank(rank);
    }

    public int getPriority() {
        return rank == null ? 0 : getRank().getPermissionPower();
    }

    public boolean expired() {
        if(duration == TimeUtil.PERMANENT) return false;
        return (executedAt + duration) <= System.currentTimeMillis();
    }

    public boolean isActive() {
        if (expired()) setActive(false);
        return !expired() && active;
    }

    public long getRemainingTime() {
        if (duration == TimeUtil.PERMANENT) return TimeUtil.PERMANENT;
        else return (duration + executedAt) - System.currentTimeMillis();
    }

    public String getRemainingTimeString() {
        return TimeUtil.millisToRoundedTime(getRemainingTime());
    }

}
