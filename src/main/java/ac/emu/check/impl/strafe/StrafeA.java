package ac.emu.check.impl.strafe;

import ac.emu.check.Check;
import ac.emu.check.CheckInfo;
import ac.emu.data.PlayerData;
import ac.emu.data.impl.MovementData;
import ac.emu.exempt.ExemptType;
import ac.emu.packet.Packet;

@CheckInfo(name = "Strafe", description = "Moved wrong in air", type = "A")
public class StrafeA extends Check {

    public StrafeA(PlayerData data) {
        super(data);
    }

    @Override
    public void processPacket(Packet packet) {
        if(packet.isMovement()) {
            MovementData processor = data.getMovementData();

            int airTicks = data.getMovementData().getAirTicks();

            double deltaX = processor.getDeltaX();
            double deltaZ = processor.getDeltaZ();
            double lastDeltaX = processor.getLastDeltaX();
            double lastDeltaZ = processor.getLastDeltaZ();

            double predDeltaX = lastDeltaX * 0.91; // https://bugs.mojang.com/browse/MC-12832
            double predDeltaZ = lastDeltaZ * 0.91;

            boolean exempt = isExempt(
                ExemptType.ALLOWED_FLIGHT,
                ExemptType.SPAWNED,
                ExemptType.VELOCITY,
                ExemptType.EXPLOSION,
                ExemptType.UNDER_BLOCK,
                ExemptType.IN_VEHICLE,
                ExemptType.ON_CLIMBABLE,
                ExemptType.IN_LIQUID,
                ExemptType.NEAR_WALL
            ) || airTicks < 3;

            double differenceX = Math.abs(deltaX - predDeltaX);
            double differenceZ = Math.abs(deltaZ - predDeltaZ);

            boolean invalid = (differenceX > 0.031 || differenceZ > 0.031) && data.getMovementData().getSpeed() > 0.3;

            if(invalid && !exempt) {
                if(thriveBuffer() > 5) {
                    this.fail(String.format("tick=%d, predX=%.5f, predZ=%.5f, deltaX=%.5f, deltaZ=%.5f, diffX=%.5f, diffZ=%.5f", airTicks, predDeltaX, predDeltaZ, deltaX, deltaZ, differenceX, differenceZ));
                }
                decayBuffer(0.025);
            }
        }
    }

}
