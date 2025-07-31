package capitalistspz.test.mixin;

import capitalistspz.test.SnowballKB;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;


@Mixin(EggEntity.class)
public abstract class EggEntityMxn extends ThrownItemEntity {
    public EggEntityMxn(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;serverDamage(Lnet/minecraft/entity/damage/DamageSource;F)V"),
            method = "onEntityHit"
    )
    protected void onHitPlayer(Entity entity, DamageSource source, float amount, Operation<Void> original) {
        if (SnowballKB.appliesToEntity(entity))
        {
            var livingEntity = (LivingEntity)entity;
            if (SnowballKB.config.eggTraditionalKb){
                var yaw = -this.getBodyYaw();
                livingEntity.takeKnockback(SnowballKB.config.eggKbMultiplier, MathHelper.sin(yaw * SnowballKB.DegToRad), -MathHelper.cos(yaw * SnowballKB.DegToRad));
            }
            else {
                livingEntity.setVelocity(livingEntity.getVelocity().add(this.getVelocity().normalize().multiply(SnowballKB.config.eggKbMultiplier)));
                livingEntity.velocityModified = true;
            }

            original.call(entity, source, SnowballKB.config.eggDamage);
        }
        else
            original.call(entity, source, amount);

    }
}
