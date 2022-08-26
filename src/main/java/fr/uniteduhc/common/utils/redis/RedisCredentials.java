package fr.uniteduhc.common.utils.redis;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RedisCredentials {
    private final String ip;
    private final String password;
    private final int port;

    public String getURL() {
        return "redis://" + ip + ":" + port;
    }
}
