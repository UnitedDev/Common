package fr.kohei.common.utils.messaging.list.packets;

import fr.kohei.common.cache.rank.Rank;
import fr.kohei.common.utils.messaging.pigdin.Packet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RankUpdatePacket implements Packet {

    private final Rank rank;
    private final boolean delete;

}
