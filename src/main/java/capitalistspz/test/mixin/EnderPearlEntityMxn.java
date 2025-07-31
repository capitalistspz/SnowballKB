package capitalistspz.test.mixin;

import capitalistspz.test.SnowballKB;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnderPearlEntity.class)
public abstract class EnderPearlEntityMxn extends ThrownItemEntity {

    public EnderPearlEntityMxn(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;serverDamage(Lnet/minecraft/entity/damage/DamageSource;F)V"),
            method = "onEntityHit"
    )
    protected void onHitPlayer(Entity entity, DamageSource source, float amount, Operation<Void> original) {
        if (SnowballKB.appliesToEntity(entity))
        {
            var livingEntity = (LivingEntity)entity;
            if (SnowballKB.config.pearlTraditionalKb){
                var yaw = -this.getBodyYaw();
                livingEntity.takeKnockback(SnowballKB.config.pearlKbMultiplier, MathHelper.sin(yaw * SnowballKB.DegToRad), -MathHelper.cos(yaw * SnowballKB.DegToRad));
            }
            else {
                livingEntity.setVelocity(livingEntity.getVelocity().add(this.getVelocity().normalize().multiply(SnowballKB.config.pearlKbMultiplier)));
                livingEntity.velocityModified = true;
            }

            original.call(livingEntity, source, SnowballKB.config.pearlDamage);
        }
        else
            original.call(entity, source, amount);

    }

}
