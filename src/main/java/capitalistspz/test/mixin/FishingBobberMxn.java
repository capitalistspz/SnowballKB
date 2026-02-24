package capitalistspz.test.mixin;

import capitalistspz.test.SnowballKB;
import capitalistspz.test.commands.Commands;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberMxn extends ProjectileEntity {
    @Shadow private Entity hookedEntity;

    private FishingBobberMxn(EntityType<? extends FishingBobberMxn> entityType, World world) {
        super(entityType, world);
    }

    @ModifyArg(method="pullHookedEntity", at=@At(value="INVOKE",target="Lnet/minecraft/util/math/Vec3d;multiply(D)Lnet/minecraft/util/math/Vec3d;"))
    public double changePullMultiplier(double multiplier) {
        return SnowballKB.config.fishingRodPullMultiplier;
    }

    @Inject(method="pullHookedEntity", at=@At("TAIL"))
    public void updateVelocity(CallbackInfo ci) {
        this.hookedEntity.velocityDirty = true;
    }
}
