package fr.uniteduhc.common.utils.messaging.list.packets;

import fr.uniteduhc.common.cache.rank.Rank;
import fr.uniteduhc.common.utils.messaging.pigdin.Packet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RankUpdatePacket implements Packet {

    private final Rank rank;
    private final boolean delete;

}
