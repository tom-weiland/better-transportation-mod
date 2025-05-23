package net.tomweiland.better_transportation.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.entity.vehicle.DefaultMinecartController;

@Mixin(DefaultMinecartController.class)
public class DefaultMinecartControllerMixin {
    @ModifyConstant(method = "getMaxSpeed", constant = @Constant(doubleValue = 0.4))
    public double changeMaxSpeed(double value) {
      return value;// * 2; // Doubling it leads to frequent & undesirable minecart behaviour: derailing, getting stuck where it shouldn't, etc.
    }
}
