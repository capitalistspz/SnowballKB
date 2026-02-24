package capitalistspz.test.commands;

import capitalistspz.test.SnowballKB;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.DefaultPermissions;
import net.minecraft.command.permission.Permission;
import net.minecraft.command.permission.PermissionLevel;
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
        LiteralArgumentBuilder<ServerCommandSource> snowKbCommand = literal("snowkb")
                .requires(executor -> executor.getPermissions().hasPermission(DefaultPermissions.GAMEMASTERS));

        snowKbCommand.then(literal("knockback")
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
        snowKbCommand.then(literal("traditional")
                .then(literal("set")
                        .then(argument("enable", BoolArgumentType.bool())
                                .executes(cmd ->  {
                                    var enable = BoolArgumentType.getBool(cmd, "enable");
                                    if (enable){
                                        cmd.getSource().sendFeedback(() -> Text.literal("Enabled traditional knockback for snowballs"), false);
                                    }
                                    else {
                                        cmd.getSource().sendFeedback(() -> Text.literal("Disabled traditional knockback for snowballs"), false);
                                    }
                                    SnowballKB.config.snowTraditionalKb = enable;
                                    return Command.SINGLE_SUCCESS;
                                })))
                .then(literal("get")
                        .executes(cmd -> {
                            cmd.getSource().sendFeedback(() -> Text.literal("Traditional knockback for snowballs is " + (SnowballKB.config.snowTraditionalKb ? "enabled" : "disabled")), false);
                            return SnowballKB.config.snowTraditionalKb ? 1 : 0;
                        }))
        );

        dispatcher.register(snowKbCommand);

        LiteralArgumentBuilder<ServerCommandSource> eggKbCommand = literal("eggkb")
                .requires(executor -> executor.getPermissions().hasPermission(DefaultPermissions.GAMEMASTERS));

        eggKbCommand.then(literal("knockback")
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
        eggKbCommand.then(literal("traditional")
                .then(literal("set")
                        .then(argument("enable", BoolArgumentType.bool())
                                .executes(cmd ->  {
                                    var enable = BoolArgumentType.getBool(cmd, "enable");
                                    if (enable){
                                        cmd.getSource().sendFeedback(() -> Text.literal("Enabled traditional knockback for eggs"), false);
                                    }
                                    else {
                                        cmd.getSource().sendFeedback(() -> Text.literal("Disabled traditional knockback for eggs"), false);
                                    }
                                    SnowballKB.config.eggTraditionalKb = enable;
                                    return Command.SINGLE_SUCCESS;
                                })))
                .then(literal("get")
                        .executes(cmd -> {
                            cmd.getSource().sendFeedback(() -> Text.literal("Traditional knockback for eggs is " + (SnowballKB.config.eggTraditionalKb ? "enabled" : "disabled")), false);
                            return SnowballKB.config.eggTraditionalKb ? 1 : 0;
                        }))
        );
        dispatcher.register(eggKbCommand);

        LiteralArgumentBuilder<ServerCommandSource> pearlKbCommand = literal("pearlkb")
                .requires(executor -> executor.getPermissions().hasPermission(DefaultPermissions.GAMEMASTERS));

        pearlKbCommand.then(literal("knockback")
                        .then(literal("set")
                                .then(argument("mult", FloatArgumentType.floatArg())
                                        .executes(cmd -> {
                                            SnowballKB.config.pearlKbMultiplier = FloatArgumentType.getFloat(cmd, "mult");
                                            SendValueFeedback(cmd, kbSetFeedback, SnowballKB.config.pearlKbMultiplier);
                                            return Command.SINGLE_SUCCESS;
                                        })
                                )
                        )
                        // Gets the current global knockback multiplier
                        .then(literal("get")
                                .executes(cmd -> {
                                    SendValueFeedback(cmd, kbGetFeedback, SnowballKB.config.pearlKbMultiplier);
                                    return (int)SnowballKB.config.pearlKbMultiplier;
                                }).then(argument("scale", DoubleArgumentType.doubleArg())
                                        .executes(cmd -> {
                                            double scaledValue = DoubleArgumentType.getDouble(cmd, "scale") * SnowballKB.config.pearlKbMultiplier;
                                            SendValueFeedback(cmd, kbGetFeedback, scaledValue);
                                            return (int) scaledValue;
                                        }))
                        )
                        // Adds a value onto the current multiplier
                        .then(literal("add")
                                .then(argument("added_mult", FloatArgumentType.floatArg())
                                        .executes(cmd -> {
                                            SnowballKB.config.pearlKbMultiplier += FloatArgumentType.getFloat(cmd, "added_mult");
                                            SendValueFeedback(cmd, kbSetFeedback, SnowballKB.config.pearlKbMultiplier);
                                            return Command.SINGLE_SUCCESS;
                                        })
                                )
                        )
                )
                .then(literal("damage")
                        .then(literal("set")
                                .then(argument("damage", FloatArgumentType.floatArg())
                                        .executes(cmd -> {
                                            SnowballKB.config.pearlDamage = FloatArgumentType.getFloat(cmd, "damage");
                                            SendValueFeedback(cmd, dmgSetFeedback, SnowballKB.config.pearlDamage);
                                            return Command.SINGLE_SUCCESS;
                                        })
                                )
                        )
                        // Gets the current global damage amount
                        .then(literal("get")
                                .executes(cmd -> {
                                    SendValueFeedback(cmd, dmgGetFeedback, SnowballKB.config.pearlDamage);
                                    return (int)SnowballKB.config.pearlDamage;
                                }).then(argument("scale", DoubleArgumentType.doubleArg())
                                        .executes(cmd -> {
                                            double scaledValue = DoubleArgumentType.getDouble(cmd, "scale") * SnowballKB.config.pearlDamage;
                                            SendValueFeedback(cmd, dmgGetFeedback, scaledValue);
                                            return (int) scaledValue;
                                        }))
                        )
                        // Adds a value onto the current amount
                        .then(literal("add")
                                .then(argument("damage", FloatArgumentType.floatArg())
                                        .executes(cmd -> {
                                            SnowballKB.config.pearlDamage += FloatArgumentType.getFloat(cmd, "damage");
                                            SendValueFeedback(cmd, dmgSetFeedback, SnowballKB.config.pearlDamage);
                                            return Command.SINGLE_SUCCESS;
                                        })
                                )
                        )
                );
        pearlKbCommand.then(literal("traditional")
                .then(literal("set")
                        .then(argument("enable", BoolArgumentType.bool())
                                .executes(cmd ->  {
                                    var enable = BoolArgumentType.getBool(cmd, "enable");
                                    if (enable){
                                        cmd.getSource().sendFeedback(() -> Text.literal("Enabled traditional knockback for ender pearls"), false);
                                    }
                                    else {
                                        cmd.getSource().sendFeedback(() -> Text.literal("Disabled traditional knockback for ender pearls"), false);
                                    }
                                    SnowballKB.config.pearlTraditionalKb = enable;
                                    return Command.SINGLE_SUCCESS;
                                })))
                .then(literal("get")
                        .executes(cmd -> {
                            cmd.getSource().sendFeedback(() -> Text.literal("Traditional knockback for ender pearls is " + (SnowballKB.config.pearlTraditionalKb ? "enabled" : "disabled")), false);
                            return SnowballKB.config.pearlTraditionalKb ? 1 : 0;
                        }))
        );

        dispatcher.register(pearlKbCommand);


        LiteralArgumentBuilder<ServerCommandSource> bobber = literal("bobberpull").requires(executor -> executor.getPermissions().hasPermission(DefaultPermissions.GAMEMASTERS));

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
        cmd.getSource().sendFeedback(() -> Text.literal(String.format(message, value)), false);
    }
}
