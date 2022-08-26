package fr.uniteduhc.common.utils.messaging.list.packets;

import fr.uniteduhc.common.cache.data.Report;
import fr.uniteduhc.common.utils.messaging.pigdin.Packet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReportUpdatePacket implements Packet {

    private final Report report;

}
