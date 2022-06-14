package fr.kohei.common.utils.messaging.list.packets;

import fr.kohei.common.cache.server.impl.CTFServer;
import fr.kohei.common.utils.messaging.pigdin.Packet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CTFUpdatePacket implements Packet {

    private final CTFServer ctfServer;

}
