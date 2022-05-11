
package fr.arashi.common.cache;

import java.io.Serializable;

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

    public void setToken(String token) {
        this.token = token;
    }

    public int permissionPower() {
        return permissionPower;
    }

    public void setPermissionPower(int permissionPower) {
        this.permissionPower = permissionPower;
    }

    public String chatPrefix() {
        return chatPrefix;
    }

    public void setChatPrefix(String chatPrefix) {
        this.chatPrefix = chatPrefix;
    }

    public String tabPrefix() {
        return tabPrefix;
    }

    public void setTabPrefix(String tabPrefix) {
        this.tabPrefix = tabPrefix;
    }

}
