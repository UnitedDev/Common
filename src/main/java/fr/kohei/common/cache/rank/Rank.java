
package fr.kohei.common.cache.rank;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Rank implements Serializable {
    private String token;
    private int permissionPower;
    private String chatPrefix;
    private String tabPrefix;

    public Rank(String token, int permissionPower, String chatPrefix, String tabPrefix) {
        this.token = token;
        this.permissionPower = permissionPower;
        this.chatPrefix = chatPrefix;
        this.tabPrefix = tabPrefix;
    }

    public String token() {
        return token;
    }

    public int permissionPower() {
        return permissionPower;
    }
}
