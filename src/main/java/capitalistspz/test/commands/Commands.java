package capitalistspz.test.commands;

import capitalistspz.test.SnowballKB;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.*;

public class Commands {
    public static String kbSetFeedback = "Set knockback multiplier to %s";
    public static String kbGetFeedback = "Knockback multiplier is %s";
    public static String dmgSetFeedback = "Set damage to %s";
    public static String dmgGetFeedback = "Damage is %s";
    public static String pullSetFeedback = "Set pull multiplier to %s";
    public static String pullGetFeedback = "Pull multiplier is %s";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher){
        // Argbuilder init
        LiteralArgumentBuilder<ServerCommandSource> knockbackCommand = literal("snowkb")
                .requires(executor -> executor.hasPermissionLevel(2));

        knockbackCommand.then(literal("knockback")
                .then(literal("set")
                        .then(argument("mult", FloatArgumentType.floatArg())
                                .executes(cmd -> {
                                    SnowballKB.config.snowKbMultiplier = FloatArgumentType.getFloat(cmd, "mult");
                                    SendValueFeedback(cmd, kbSetFeedback, SnowballKB.config.snowKbMultiplier);
                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                )
                // Gets the current global knockback multiplier
                .then(literal("get")
                        .executes(cmd -> {
                            SendValueFeedback(cmd, kbGetFeedback, SnowballKB.config.snowKbMultiplier);
                            return (int)SnowballKB.config.snowKbMultiplier;
                        }).then(argument("scale", DoubleArgumentType.doubleArg())
                                .executes(cmd -> {
                                    double scaledValue = DoubleArgumentType.getDouble(cmd, "scale") * SnowballKB.config.snowKbMultiplier;
                                    SendValueFeedback(cmd, kbGetFeedback, scaledValue);
                                    return (int) scaledValue;
                                }))
                )
                // Adds a value onto the current multiplier
                .then(literal("add")
                        .then(argument("added_mult", FloatArgumentType.floatArg())
                                .executes(cmd -> {
                                    SnowballKB.config.snowKbMultiplier += FloatArgumentType.getFloat(cmd, "added_mult");
                                    SendValueFeedback(cmd, kbSetFeedback, SnowballKB.config.snowKbMultiplier);
                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                )
        )
        .then(literal("damage")
                .then(literal("set")
                        .then(argument("damage", FloatArgumentType.floatArg())
                                .executes(cmd -> {
                                    SnowballKB.config.snowDamage = FloatArgumentType.getFloat(cmd, "damage");
                                    SendValueFeedback(cmd, dmgSetFeedback, SnowballKB.config.snowDamage);
                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                )
                // Gets the current global damage amount
                .then(literal("get")
                        .executes(cmd -> {
                            SendValueFeedback(cmd, dmgGetFeedback, SnowballKB.config.snowDamage);
                            return (int)SnowballKB.config.snowDamage;
                        }).then(argument("scale", DoubleArgumentType.doubleArg())
                                .executes(cmd -> {
                                    double scaledValue = DoubleArgumentType.getDouble(cmd, "scale") * SnowballKB.config.snowDamage;
                                    SendValueFeedback(cmd, dmgGetFeedback, scaledValue);
                                    return (int) scaledValue;
                                }))
                )
                // Adds a value onto the current amount
                .then(literal("add")
                        .then(argument("damage", FloatArgumentType.floatArg())
                                .executes(cmd -> {
                                    SnowballKB.config.snowDamage += FloatArgumentType.getFloat(cmd, "damage");
                                    SendValueFeedback(cmd, dmgSetFeedback, SnowballKB.config.snowDamage);
                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                )
        );
        dispatcher.register(knockbackCommand);

        LiteralArgumentBuilder<ServerCommandSource> eggKnockbackCommand = literal("eggkb")
                .requires(executor -> executor.hasPermissionLevel(2));

        eggKnockbackCommand.then(literal("knockback")
                .then(literal("set")
                        .then(argument("mult", FloatArgumentType.floatArg())
                                .executes(cmd -> {
                                    SnowballKB.config.eggKbMultiplier = FloatArgumentType.getFloat(cmd, "mult");
                                    SendValueFeedback(cmd, kbSetFeedback, SnowballKB.config.eggKbMultiplier);
                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                )
                // Gets the current global knockback multiplier
                .then(literal("get")
                        .executes(cmd -> {
                            SendValueFeedback(cmd, kbGetFeedback, SnowballKB.config.eggKbMultiplier);
                            return (int)SnowballKB.config.eggKbMultiplier;
                        }).then(argument("scale", DoubleArgumentType.doubleArg())
                                .executes(cmd -> {
                                    double scaledValue = DoubleArgumentType.getDouble(cmd, "scale") * SnowballKB.config.eggKbMultiplier;
                                    SendValueFeedback(cmd, kbGetFeedback, scaledValue);
                                    return (int) scaledValue;
                                }))
                )
                // Adds a value onto the current multiplier
                .then(literal("add")
                        .then(argument("added_mult", FloatArgumentType.floatArg())
                                .executes(cmd -> {
                                    SnowballKB.config.eggKbMultiplier += FloatArgumentType.getFloat(cmd, "added_mult");
                                    SendValueFeedback(cmd, kbSetFeedback, SnowballKB.config.eggKbMultiplier);
                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                )
        )
                .then(literal("damage")
                        .then(literal("set")
                                .then(argument("damage", FloatArgumentType.floatArg())
                                        .executes(cmd -> {
                                            SnowballKB.config.eggDamage = FloatArgumentType.getFloat(cmd, "damage");
                                            SendValueFeedback(cmd, dmgSetFeedback, SnowballKB.config.eggDamage);
                                            return Command.SINGLE_SUCCESS;
                                        })
                                )
                        )
                        // Gets the current global damage amount
                        .then(literal("get")
                                .executes(cmd -> {
                                    SendValueFeedback(cmd, dmgGetFeedback, SnowballKB.config.eggDamage);
                                    return (int) SnowballKB.config.eggDamage;
                                }).then(argument("scale", DoubleArgumentType.doubleArg())
                                        .executes(cmd ->  {
                                            double scaledValue = DoubleArgumentType.getDouble(cmd, "scale") * SnowballKB.config.eggDamage;
                                            SendValueFeedback(cmd, dmgGetFeedback, scaledValue);
                                            return (int) scaledValue;
                                        }))
                        )
                        // Adds a value onto the current amount
                        .then(literal("add")
                                .then(argument("damage", FloatArgumentType.floatArg())
                                        .executes(cmd -> {
                                            SnowballKB.config.eggDamage += FloatArgumentType.getFloat(cmd, "damage");
                                            SendValueFeedback(cmd, dmgSetFeedback, SnowballKB.config.eggDamage);
                                            return Command.SINGLE_SUCCESS;
                                        })
                                )
                        )
                );
        dispatcher.register(eggKnockbackCommand);
        
        LiteralArgumentBuilder<ServerCommandSource> bobber = literal("bobberpull").requires(executor -> executor.hasPermissionLevel(2));

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

    private static void SendValueFeedback(CommandContext<ServerCommandSource> cmd, String message, double value){
        cmd.getSource().sendFeedback(Text.literal(String.format(message, value)), false);
    }
}
