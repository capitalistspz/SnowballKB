package capitalistspz.test.mixin;

import capitalistspz.test.SnowballKB;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Allows the knockback to apply to players
@Mixin(SnowballEntity.class)
public abstract class SnowballEntityMxn extends ThrownItemEntity {
    public SnowballEntityMxn(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }
    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;serverDamage(Lnet/minecraft/entity/damage/DamageSource;F)V"),
            method = "onEntityHit"
    )
    protected void onHitPlayer(Entity entity, DamageSource source, float amount, Operation<Void> original) {
        if (SnowballKB.appliesToEntity(entity))
        {
            var livingEntity = (LivingEntity)entity;
            if (SnowballKB.config.snowTraditionalKb){
                var yaw = -this.getBodyYaw();
                livingEntity.takeKnockback(SnowballKB.config.snowKbMultiplier, MathHelper.sin(yaw * SnowballKB.DegToRad), -MathHelper.cos(yaw * SnowballKB.DegToRad));
            }
            else {
                livingEntity.setVelocity(livingEntity.getVelocity().add(this.getVelocity().normalize().multiply(SnowballKB.config.snowKbMultiplier)));
                livingEntity.velocityDirty = true;
            }

            original.call(livingEntity, source, SnowballKB.config.snowDamage);
        }
        else
            original.call(entity, source, amount);

    }

}
