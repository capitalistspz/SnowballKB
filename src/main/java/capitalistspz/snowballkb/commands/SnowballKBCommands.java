package capitalistspz.snowballkb.commands;

import capitalistspz.snowballkb.SnowballKB;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;

import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;


import static net.minecraft.commands.Commands.*;

public class SnowballKBCommands {
    public static String kbSetFeedback = "Set knockback multiplier to %s";
    public static String kbGetFeedback = "Knockback multiplier is %s";
    public static String dmgSetFeedback = "Set damage to %s";
    public static String dmgGetFeedback = "Damage is %s";
    public static String pullSetFeedback = "Set pull multiplier to %s";
    public static String pullGetFeedback = "Pull multiplier is %s";

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(ThrowItemCommand(SnowballKB.ThrownItem.Snowball, "snowballkb", "snowball"));
        dispatcher.register(ThrowItemCommand(SnowballKB.ThrownItem.Egg, "eggkb", "egg"));
        dispatcher.register(ThrowItemCommand(SnowballKB.ThrownItem.Enderpearl, "enderpearlkb", "enderpearl"));

        var bobber = literal("bobberpull")
                .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS));

        bobber.then(literal("set").then(argument("value", FloatArgumentType.floatArg()).executes(cmd -> {
            SnowballKB.config.fishingRodPullMultiplier = FloatArgumentType.getFloat(cmd, "value");
            SendValueFeedback(cmd, pullSetFeedback, SnowballKB.config.fishingRodPullMultiplier);
            return Command.SINGLE_SUCCESS;
        }
        ))).then(literal("add").then(argument("value", FloatArgumentType.floatArg()).executes(cmd -> {
            SnowballKB.config.fishingRodPullMultiplier += FloatArgumentType.getFloat(cmd, "value");
            SendValueFeedback(cmd, pullSetFeedback, SnowballKB.config.fishingRodPullMultiplier);
            return Command.SINGLE_SUCCESS;
        }
        ))).then(literal("get").executes(cmd -> {
            SendValueFeedback(cmd, pullGetFeedback, SnowballKB.config.fishingRodPullMultiplier);
            return (int) SnowballKB.config.fishingRodPullMultiplier;
        }).then(argument("scale", DoubleArgumentType.doubleArg())
                .executes(cmd -> {
                    double scaledValue = DoubleArgumentType.getDouble(cmd, "scale") * SnowballKB.config.fishingRodPullMultiplier;
                    SendValueFeedback(cmd, pullGetFeedback, scaledValue);
                    return (int) scaledValue;
                }))
        );
        dispatcher.register(bobber);
    }
    
    private static LiteralArgumentBuilder<CommandSourceStack> ThrowItemCommand(SnowballKB.ThrownItem item, String commandName, String friendlyName)
    {
        var builder = literal(commandName)
                .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS));
        builder.then(literal("knockback")
                        .then(literal("set")
                                .then(argument("mult", FloatArgumentType.floatArg())
                                        .executes(cmd -> {
                                            SnowballKB.GetThrownItemConfig(item).knockbackMultiplier = FloatArgumentType.getFloat(cmd, "mult");
                                            SendValueFeedback(cmd, kbSetFeedback, SnowballKB.GetThrownItemConfig(item).knockbackMultiplier);
                                            return Command.SINGLE_SUCCESS;
                                        })
                                )
                        )
                        // Gets the current global knockback multiplier
                        .then(literal("get")
                                .executes(cmd -> {
                                    SendValueFeedback(cmd, kbGetFeedback, SnowballKB.GetThrownItemConfig(item).knockbackMultiplier);
                                    return (int) SnowballKB.GetThrownItemConfig(item).knockbackMultiplier;
                                }).then(argument("scale", DoubleArgumentType.doubleArg())
                                        .executes(cmd -> {
                                            double scaledValue = DoubleArgumentType.getDouble(cmd, "scale") * SnowballKB.GetThrownItemConfig(item).knockbackMultiplier;
                                            SendValueFeedback(cmd, kbGetFeedback, scaledValue);
                                            return (int) scaledValue;
                                        }))
                        )
                        // Adds a value onto the current multiplier
                        .then(literal("add")
                                .then(argument("added_mult", FloatArgumentType.floatArg())
                                        .executes(cmd -> {
                                            SnowballKB.GetThrownItemConfig(item).knockbackMultiplier += FloatArgumentType.getFloat(cmd, "added_mult");
                                            SendValueFeedback(cmd, kbSetFeedback, SnowballKB.GetThrownItemConfig(item).knockbackMultiplier);
                                            return Command.SINGLE_SUCCESS;
                                        })
                                )
                        )
                )
                .then(literal("damage")
                        .then(literal("set")
                                .then(argument("damage", FloatArgumentType.floatArg())
                                        .executes(cmd -> {
                                            SnowballKB.GetThrownItemConfig(item).hitDamage = FloatArgumentType.getFloat(cmd, "damage");
                                            SendValueFeedback(cmd, dmgSetFeedback, SnowballKB.GetThrownItemConfig(item).hitDamage);
                                            return Command.SINGLE_SUCCESS;
                                        })
                                )
                        )
                        // Gets the current global damage amount
                        .then(literal("get")
                                .executes(cmd -> {
                                    SendValueFeedback(cmd, dmgGetFeedback, SnowballKB.GetThrownItemConfig(item).hitDamage);
                                    return (int) SnowballKB.GetThrownItemConfig(item).hitDamage;
                                }).then(argument("scale", DoubleArgumentType.doubleArg())
                                        .executes(cmd -> {
                                            double scaledValue = DoubleArgumentType.getDouble(cmd, "scale") * SnowballKB.GetThrownItemConfig(item).hitDamage;
                                            SendValueFeedback(cmd, dmgGetFeedback, scaledValue);
                                            return (int) scaledValue;
                                        }))
                        )
                        // Adds a value onto the current amount
                        .then(literal("add")
                                .then(argument("damage", FloatArgumentType.floatArg())
                                        .executes(cmd -> {
                                            SnowballKB.GetThrownItemConfig(item).hitDamage += FloatArgumentType.getFloat(cmd, "damage");
                                            SendValueFeedback(cmd, dmgSetFeedback, SnowballKB.GetThrownItemConfig(item).hitDamage);
                                            return Command.SINGLE_SUCCESS;
                                        })
                                )
                        )
                );
        builder.then(literal("vanilla-style")
                .then(literal("set")
                        .then(argument("enable", BoolArgumentType.bool())
                                .executes(cmd ->  {
                                    var enable = BoolArgumentType.getBool(cmd, "enable");
                                    if (enable){
                                        cmd.getSource().sendSuccess(() -> Component.literal("Enabled traditional knockback for " + friendlyName), false);
                                    }
                                    else {
                                        cmd.getSource().sendSuccess(() -> Component.literal("Disabled traditional knockback for " + friendlyName), false);
                                    }
                                    SnowballKB.GetThrownItemConfig(item).vanillaStyleKnockback = enable;
                                    return Command.SINGLE_SUCCESS;
                                })))
                .then(literal("get")
                        .executes(cmd -> {
                            cmd.getSource().sendSuccess(() -> Component.literal("Knockback for " +  friendlyName + " is " + (SnowballKB.GetThrownItemConfig(item).vanillaStyleKnockback ? "vanilla-style" : "alternate-style")), false);
                            return SnowballKB.GetThrownItemConfig(item).vanillaStyleKnockback ? 1 : 0;
                        }))
        );
        return builder;
    }

    private static void SendValueFeedback(CommandContext<CommandSourceStack> cmd, String message, double value){
        cmd.getSource().sendSuccess(() -> Component.literal(String.format(message, value)), false);
    }
}
