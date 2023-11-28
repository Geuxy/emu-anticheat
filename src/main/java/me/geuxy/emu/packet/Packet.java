package me.geuxy.emu.packet;

import io.github.retrooper.packetevents.packettype.PacketType.Play;
import io.github.retrooper.packetevents.packetwrappers.NMSPacket;

import lombok.Getter;

@Getter
public class Packet {

    protected final NMSPacket raw;
    protected final byte packetId;

    public Packet(NMSPacket raw, byte packetId) {
        this.raw = raw;
        this.packetId = packetId;
    }

    public boolean isFlying() {
        return Play.Client.Util.isInstanceOfFlying(packetId);
    }

    public boolean isVelocity() {
        return packetId == Play.Server.ENTITY_VELOCITY;
    }

    public boolean isExplosion() {
        return packetId == Play.Server.EXPLOSION;
    }

    public boolean isUseEntity() {
        return packetId == Play.Client.USE_ENTITY;
    }

    public boolean isTransaction() {
        return packetId == Play.Client.TRANSACTION;
    }

    public boolean isClientCommand() {
        return packetId == Play.Client.CLIENT_COMMAND;
    }

    public boolean isSteerVehicle() {
        return packetId == Play.Client.STEER_VEHICLE;
    }

    public boolean isBlockDig() {
        return packetId == Play.Client.BLOCK_DIG;
    }

    public boolean isBlockPlace() {
        return packetId == Play.Client.BLOCK_PLACE;
    }

    public boolean isHeldItemSlot() {
        return packetId == Play.Client.HELD_ITEM_SLOT;
    }

}
