package capitalistspz.snowballkb;

import capitalistspz.snowballkb.config.Config;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.LevelResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;

public class SnowballKB implements ModInitializer {
    public static Config config = new Config();
    public static Logger logger = LogManager.getLogger();
    public static final float DegToRad = (float)Math.PI / 180;
    public static final LevelResource CONFIG_RESOURCE = new LevelResource("snowballkb.json");

    @Override
    public void onInitialize() {

    }
    public static boolean appliesToEntity(Entity entity) {
        if (entity instanceof LivingEntity livingEntity)
        {
            if (livingEntity.isInvulnerable())
                return false;
            if (livingEntity instanceof Player)
            {
                return SnowballKB.config.applyToPlayers;
            }
            else if (livingEntity instanceof Blaze)
            {
                return SnowballKB.config.applyToBlazes;
            }
            else return SnowballKB.config.applyToOtherEntities;
        }
        return false;
    }

    public static void OnHit(Config.ThrownItemConfig config, Entity self, Entity targetEntity, DamageSource source, float damage, Operation<Void> original)
    {
        if (SnowballKB.appliesToEntity(targetEntity))
        {
            var livingEntity = (LivingEntity) targetEntity;
            if (config.vanillaStyleKnockback){
                var yaw = -self.getYRot();
                livingEntity.knockback(config.knockbackMultiplier, Mth.sin(yaw * SnowballKB.DegToRad), -Mth.cos(yaw * SnowballKB.DegToRad));
            }
            else {
                livingEntity.push(self.getDeltaMovement().normalize().scale(config.knockbackMultiplier));
            }
            // This triggers a velocity update
            livingEntity.hurtMarked = true;

            original.call(targetEntity, source, config.hitDamage);
        }
        else
            original.call(targetEntity, source, damage);
    }
    public enum ThrownItem {
        Snowball,
        Egg,
        Enderpearl
    }

    public static Config.ThrownItemConfig GetThrownItemConfig(ThrownItem item) {
        switch (item)
        {
            case Snowball -> {
                return config.snowball;
            }
            case Egg -> {
                return config.egg;
            }
            case Enderpearl -> {
                return config.enderpearl;
            }
            default -> {
                throw new IllegalArgumentException();
            }
        }
    }

}
