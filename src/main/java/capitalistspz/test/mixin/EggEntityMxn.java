package capitalistspz.test.mixin;

import capitalistspz.test.SnowballKB;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(EggEntity.class)
public abstract class EggEntityMxn extends ThrownItemEntity {
    public EggEntityMxn(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"),
            method="onEntityHit"
    )
    protected void onHitPlayer(EntityHitResult entityHitResult, CallbackInfo ci) {
        Entity entity = entityHitResult.getEntity();
        if (entity instanceof PlayerEntity player && !player.getAbilities().invulnerable)
        {
            if (SnowballKB.config.eggTraditionalKb){
                var yaw = -this.getBodyYaw();
                player.takeKnockback(SnowballKB.config.eggKbMultiplier, MathHelper.sin(yaw * SnowballKB.DegToRad), -MathHelper.cos(yaw * SnowballKB.DegToRad));
            }
            else {
                player.setVelocity(player.getVelocity().add(this.getVelocity().normalize().multiply(SnowballKB.config.eggKbMultiplier)));
                player.velocityModified = true;
            }

            if (entity.getWorld() instanceof ServerWorld world) {
                entity.damage(world, this.getDamageSources().thrown(this, this.getOwner()),
                        SnowballKB.config.eggDamage);
            }
        }

    }
}
