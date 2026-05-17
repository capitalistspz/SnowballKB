package capitalistspz.snowballkb.mixin;

import capitalistspz.snowballkb.commands.SnowballKBCommands;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Register the command
@Mixin(Commands.class)
public abstract class CommandsMxn {
    @Shadow
    @Final
    private CommandDispatcher<CommandSourceStack> dispatcher;
    @Inject(method="<init>", at=@At("TAIL"))
    void registerCommands(CallbackInfo ci){
        SnowballKBCommands.register(dispatcher);
    }

}