package fr.kohei.common.utils.messaging.list.packets;

import fr.kohei.common.cache.server.impl.UHCServer;
import fr.kohei.common.utils.messaging.pigdin.Packet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UHCUpdatePacket implements Packet {

    private final UHCServer uhcServer;

}
