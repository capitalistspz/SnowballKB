package capitalistspz.test.mixin;

import capitalistspz.test.commands.Commands;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Register the command
@Mixin(CommandManager.class)
public abstract class CommandManagerMxn {
    @Shadow
    @Final
    private CommandDispatcher<ServerCommandSource> dispatcher;
    @Inject(method="<init>", at=@At("TAIL"))
    void registerCommands(CallbackInfo ci){
        Commands.register(dispatcher);
    }

}