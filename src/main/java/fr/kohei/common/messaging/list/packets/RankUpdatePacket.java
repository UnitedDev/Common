package fr.kohei.common.messaging.list.packets;

import fr.kohei.common.cache.Rank;
import fr.kohei.common.messaging.pigdin.Packet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RankUpdatePacket implements Packet {

    private final Rank rank;
    private final boolean delete;

}
