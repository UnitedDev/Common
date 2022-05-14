package fr.kohei.common.messaging;

import com.google.gson.Gson;
import fr.kohei.common.messaging.list.packets.ProfileUpdatePacket;
import fr.kohei.common.messaging.list.packets.PunishmentUpdatePacket;
import fr.kohei.common.messaging.list.packets.RankUpdatePacket;
import fr.kohei.common.messaging.list.subscribers.ProfileUpdateSubscriber;
import fr.kohei.common.messaging.list.subscribers.PunishmentUpdateSubscriber;
import fr.kohei.common.messaging.list.subscribers.RankUpdateSubscriber;
import fr.kohei.common.messaging.pigdin.IncomingPacketHandler;
import fr.kohei.common.messaging.pigdin.Packet;
import fr.kohei.common.messaging.pigdin.PacketListener;
import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Pidgin {

    private final RedissonClient client;
    private final RTopic topic;

    private final Gson gson = new Gson();
    private final HashMap<Class<? extends Packet>, PacketListener> adapters;
    private final HashMap<Class<? extends Packet>, String> types;
    private final HashMap<String, Class<? extends Packet>> cTypes;

    public Pidgin(String topic) {
        this.client = Redisson.create();
        this.topic = this.client.getTopic(topic);

        this.adapters = new HashMap<>();
        this.types = new HashMap<>();
        this.cTypes = new HashMap<>();
        this.topic.addListener(String.class, new MessagingListener());

        this.registerAdapter(ProfileUpdatePacket.class, new ProfileUpdateSubscriber());
        this.registerAdapter(PunishmentUpdatePacket.class, new PunishmentUpdateSubscriber());
        this.registerAdapter(RankUpdatePacket.class, new RankUpdateSubscriber());
    }

    public void registerAdapter(Class<? extends Packet> clazz, PacketListener listener) {
        this.adapters.put(clazz, listener);
        String uuid = UUID.randomUUID().toString();
        this.types.put(clazz, uuid);
        this.cTypes.put(uuid, clazz);
        System.out.println("[MESSAGING] Registered new packet " + clazz + " with listener " + listener);
    }

    public void sendPacket(Packet packet) {
        this.topic.publish(types.get(packet.getClass()) + ";" + gson.toJson(packet));
    }

    private class MessagingListener implements MessageListener<String> {
        @Override
        public void onMessage(CharSequence charSequence, String s) {
            try {
                String id = s.split(";")[0];
                Packet packet = gson.fromJson(s.split(";")[1], cTypes.get(id));

                Class<? extends Packet> clazz = null;
                for (Map.Entry<Class<? extends Packet>, String> entry : types.entrySet()) {
                    Class<? extends Packet> aClass = entry.getKey();
                    String s1 = entry.getValue();
                    if (s1.equalsIgnoreCase(id)) clazz = aClass;
                }

                PacketListener listener = adapters.get(clazz);

                for (Method m : listener.getClass().getDeclaredMethods()) {
                    if (m.getDeclaredAnnotation(IncomingPacketHandler.class) != null) {
                        try {
                            m.invoke(listener, packet);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception ignored) {
                System.out.println("Failed to receive packet " + s);
            }
        }
    }

}