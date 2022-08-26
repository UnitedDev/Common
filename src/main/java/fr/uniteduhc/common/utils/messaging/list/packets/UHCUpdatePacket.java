package fr.uniteduhc.common.utils.messaging.list.packets;

import fr.uniteduhc.common.cache.server.impl.UHCServer;
import fr.uniteduhc.common.utils.messaging.pigdin.Packet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UHCUpdatePacket implements Packet {

    private final UHCServer uhcServer;

}
