package fr.kohei.common.utils.messaging.list.packets;

import fr.kohei.common.cache.data.Report;
import fr.kohei.common.utils.messaging.pigdin.Packet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReportUpdatePacket implements Packet {

    private final Report report;

}
