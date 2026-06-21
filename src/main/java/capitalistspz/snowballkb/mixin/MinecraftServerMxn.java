package capitalistspz.snowballkb.mixin;

import capitalistspz.snowballkb.SnowballKB;
import capitalistspz.snowballkb.config.Config;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.file.Path;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMxn {
    @Shadow
    public abstract Path getWorldPath(LevelResource resource);

    @Shadow
    @Final
    protected LevelStorageSource.LevelStorageAccess storageSource;

    @Shadow
    public abstract RegistryAccess.Frozen registryAccess();

    @Inject(method = "saveAllChunks", at = @At("TAIL"))
    public void saveConfig(boolean suppressLogs, boolean flush, boolean force, CallbackInfoReturnable<Boolean> cir) {

        if (Config.save(SnowballKB.config, storageSource.getLevelPath(SnowballKB.CONFIG_RESOURCE)) && !suppressLogs) {
            SnowballKB.logger.log(Level.INFO, "Config saved.");
        }
    }

    @Inject(method = "loadLevel", at = @At("TAIL"))
    public void loadConfig(CallbackInfo ci) {
        SnowballKB.config = Config.load(storageSource.getLevelPath(SnowballKB.CONFIG_RESOURCE));
        var entityTypeRegistry = registryAccess().lookupOrThrow(Registries.ENTITY_TYPE);
        var affected = entityTypeRegistry.get(SnowballKB.AFFECTED_TAG_KEY);
        if (affected.isPresent()) {
            SnowballKB.affectedEntities = affected.get();
        } else {
            // This exists because the mod datapack isn't loaded without Fabric API
            SnowballKB.affectedEntities = HolderSet.direct(entityTypeRegistry.wrapAsHolder(EntityTypes.PLAYER));
        }
    }
}
