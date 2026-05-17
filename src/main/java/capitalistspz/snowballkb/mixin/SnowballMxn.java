package capitalistspz.snowballkb.mixin;

import capitalistspz.snowballkb.SnowballKB;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.throwableitemprojectile.*;

import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;


@Mixin(Snowball.class)
public abstract class SnowballMxn extends ThrowableItemProjectile {
    public SnowballMxn(EntityType<? extends @NotNull ThrowableItemProjectile> entityType, Level world) {
        super(entityType, world);
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)V"),
            method = "onHitEntity"
    )
    protected void onHitPlayer(Entity entity, DamageSource source, float damage, Operation<Void> original) {
        SnowballKB.OnHit(SnowballKB.GetThrownItemConfig(SnowballKB.ThrownItem.Snowball), this, entity, source, damage, original);
    }
}
