package net.tomweiland.better_transportation.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.Leashable;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.entity.vehicle.AbstractBoatEntity.Location;
import net.minecraft.entity.vehicle.VehicleEntity;
import net.minecraft.world.World;

@Mixin(AbstractBoatEntity.class)
public abstract class AbstractBoatEntityMixin extends VehicleEntity implements Leashable {
    public AbstractBoatEntityMixin(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    private static final double maxBlocksPerSecond = 8; // How many blocks per second if slipperiness is 1 (blue ice is 0.989)
    private static final double maxBlocksPerTick = maxBlocksPerSecond / 20;

    @Shadow
    private Location location;

    @Redirect(
        method = "updateVelocity",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/AbstractBoatEntity;setVelocity(DDD)V", ordinal = 0),
        slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/AbstractBoatEntity;getVelocity()V", ordinal = 0))
    )
    private void clampVelocityOnLand(AbstractBoatEntity boat, double x, double y, double z, @Local(ordinal = 0) float slipperiness) {
        double xAbs = Math.abs(x);
        double zAbs = Math.abs(z);
        if (this.location == net.minecraft.entity.vehicle.AbstractBoatEntity.Location.ON_LAND && (xAbs > 0.01 || zAbs > 0.01)) {
            // Clamp the boat velocity when on land (based on slipperiness)
            double mag = Math.sqrt(x * x + z * z);
            double multiplier = maxBlocksPerTick * slipperiness;
            double xMax = xAbs / mag * multiplier;
            double zMax = zAbs / mag * multiplier;
            x = Math.clamp(x, -xMax, xMax);
            z = Math.clamp(z, -zMax, zMax);
        }
        boat.setVelocity(x, y, z);
    }
}
