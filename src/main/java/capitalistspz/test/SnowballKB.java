package capitalistspz.test;

import capitalistspz.test.config.Config;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SnowballKB implements ModInitializer {
    public static Config config = new Config();
    public static Logger logger = LogManager.getLogger();
    public static final float DegToRad = (float)Math.PI / 180;

    @Override
    public void onInitialize() {

    }
    public static boolean appliesToEntity(Entity entity) {
        if (entity instanceof LivingEntity livingEntity)
        {
            if (livingEntity.isInvulnerable())
                return false;
            if (livingEntity instanceof PlayerEntity)
            {
                return SnowballKB.config.applyToPlayers;
            }
            else if (livingEntity instanceof BlazeEntity)
            {
                return SnowballKB.config.applyToBlazes;
            }
            else return SnowballKB.config.applyToOtherEntities;
        }
        return true;
    }
}
