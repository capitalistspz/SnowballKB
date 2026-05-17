package capitalistspz.snowballkb.mixin;

import capitalistspz.snowballkb.SnowballKB;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.Projectile;

import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(FishingHook.class)
public abstract class FishingHookMxn extends Projectile {
    private FishingHookMxn(EntityType<? extends @NotNull FishingHookMxn> entityType, Level world) {
        super(entityType, world);
    }

    @ModifyArg(method="pullEntity", at=@At(value="INVOKE",target="Lnet/minecraft/world/phys/Vec3;scale(D)Lnet/minecraft/world/phys/Vec3;"))
    public double changePullMultiplier(double multiplier) {
        return SnowballKB.config.fishingRodPullMultiplier;
    }
}
