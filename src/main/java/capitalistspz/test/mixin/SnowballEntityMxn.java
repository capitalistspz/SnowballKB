package capitalistspz.test.mixin;

import capitalistspz.test.commands.Commands;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

// Allows the knockback to apply to players
@Mixin(SnowballEntity.class)
public abstract class SnowballEntityMxn extends ThrownItemEntity {
    public SnowballEntityMxn(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }
    @Inject(at = @At("TAIL"),
            method="onEntityHit",
            locals = LocalCapture.CAPTURE_FAILEXCEPTION
    )
    protected void onHitPlayer(EntityHitResult entityHitResult, CallbackInfo ci, Entity entity) {

        if (entity instanceof PlayerEntity) // Hopefully deliver knockback to player
        {
            entity.setVelocity(entity.getVelocity().add(this.getVelocity().normalize().multiply(Commands.snowKbMultiplier)));
            entity.velocityModified = true;
            entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 0.001f);
        }

    }
}
