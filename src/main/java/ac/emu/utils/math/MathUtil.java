package ac.emu.utils.math;

import ac.emu.utils.type.LimitedList;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class MathUtil {

    public static double hypot(double a, double b) {
        return Math.sqrt((a * a) + (b * b));
    }

    public static float hypot(float a, float b) {
        return (float) Math.sqrt((a * a) + (b * b));
    }

    // For a 3-dimensional vector, V = (a, b, c) the magnitude is given by √(a2 + b2 + c2).
    public static double magnitude(double... values) {
        double magnitude = 0;

        for(double value : values) {
            magnitude += value * value;
        }

        return Math.sqrt(magnitude);
    }

    // By PhoenixHaven
    public static double getAngleRotation(Location loc1, Location loc2) {
        if (loc1 == null || loc2 == null) return -1;

        Vector playerRotation = new Vector(loc1.getYaw(), loc1.getPitch(), 0.0f);
        loc1.setY(0);
        loc2.setY(0);

        float[] rot = getRotations(loc1, loc2);
        Vector expectedRotation = new Vector(rot[0], rot[1], 0);
        return ((playerRotation.getX() - expectedRotation.getX()) % 180);
    }

    // By PhoenixHaven
    public static float[] getRotations(Location one, Location two) {
        double diffX = two.getX() - one.getX();
        double diffZ = two.getZ() - one.getZ();
        double diffY = two.getY() + 2.0 - 0.4 - (one.getY() + 2.0);
        double dist = MathUtil.hypot(diffX, diffZ);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float) (-Math.atan2(diffY, dist) * 180.0 / Math.PI);
        return new float[] {yaw, pitch};
    }

    public static double getVarianceSq(LimitedList<? extends Number> samples) {
        return Math.sqrt(getVariance(samples));
    }

    public static double getVariance(LimitedList<? extends Number> samples) {
        double variance = 0;
        double sum = 0;

        double average;

        int i = 0;
        for (Number n : samples) {
            sum += n.doubleValue();
            ++i;
        }

        average = sum / i;

        for (Number n : samples) {
            variance += Math.pow(n.doubleValue() - average, 2.0);
        }

        return variance;
    }

    public static double getAverage(LimitedList<? extends Number> samples) {
        return samples.stream().mapToDouble(Number::doubleValue).average().orElse(0);
    }

}
