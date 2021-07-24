package capitalistspz.test.commands;

import capitalistspz.test.SnowballKB;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

import static net.minecraft.server.command.CommandManager.*;

public class Commands {
    public static String kbSetFeedback = "The knockback multiplier has been set to: ";
    public static String kbGetFeedback = "The knockback multiplier is : ";
    public static String dmgSetFeedback = "The damage multiplier has been set to: ";
    public static String dmgGetFeedback = "The damage multiplier is : ";
    public static String pullSetFeedback = "The pull multiplier has been set to: ";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher){
        // Argbuilder init
        LiteralArgumentBuilder<ServerCommandSource> knockbackCommand = literal("snowkb")
                .requires(executor -> executor.hasPermissionLevel(2));

        knockbackCommand.then(literal("knockback")
                .then(literal("set")
                        .then(argument("mult", FloatArgumentType.floatArg())
                                .executes(cmd -> {
                                    SnowballKB.config.snowKbMultiplier = FloatArgumentType.getFloat(cmd, "mult");
                                    cmd.getSource().sendFeedback(new LiteralText(kbSetFeedback + SnowballKB.config.snowKbMultiplier), false);
                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                )
                // Gets the current global knockback multiplier
                .then(literal("get")
                        .executes(cmd -> {
                            cmd.getSource().sendFeedback(new LiteralText(kbGetFeedback + SnowballKB.config.snowKbMultiplier), false);
                            return (int)(SnowballKB.config.snowKbMultiplier * 1000);
                        })
                )
                // Adds a value onto the current multiplier
                .then(literal("add")
                        .then(argument("added_mult", FloatArgumentType.floatArg())
                                .executes(cmd -> {
                                    SnowballKB.config.snowKbMultiplier += FloatArgumentType.getFloat(cmd, "added_mult");
                                    cmd.getSource().sendFeedback(new LiteralText(kbSetFeedback + SnowballKB.config.snowKbMultiplier), false);
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
                                    cmd.getSource().sendFeedback(new LiteralText(dmgSetFeedback + SnowballKB.config.snowDamage), false);
                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                )
                // Gets the current global knockback multiplier
                .then(literal("get")
                        .executes(cmd -> {
                            cmd.getSource().sendFeedback(new LiteralText(dmgGetFeedback + SnowballKB.config.snowDamage), false);
                            return (int)(SnowballKB.config.snowDamage * 1000);
                        })
                )
                // Adds a value onto the current multiplier
                .then(literal("add")
                        .then(argument("damage", FloatArgumentType.floatArg())
                                .executes(cmd -> {
                                    SnowballKB.config.snowDamage += FloatArgumentType.getFloat(cmd, "damage");
                                    cmd.getSource().sendFeedback(new LiteralText(dmgSetFeedback + SnowballKB.config.snowDamage), false);
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
                                    cmd.getSource().sendFeedback(new LiteralText(kbSetFeedback + SnowballKB.config.eggKbMultiplier), false);
                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                )
                // Gets the current global knockback multiplier
                .then(literal("get")
                        .executes(cmd -> {
                            cmd.getSource().sendFeedback(new LiteralText(kbGetFeedback + SnowballKB.config.eggKbMultiplier), false);
                            return (int)(SnowballKB.config.eggKbMultiplier * 1000);
                        })
                )
                // Adds a value onto the current multiplier
                .then(literal("add")
                        .then(argument("added_mult", FloatArgumentType.floatArg())
                                .executes(cmd -> {
                                    SnowballKB.config.eggKbMultiplier += FloatArgumentType.getFloat(cmd, "added_mult");
                                    cmd.getSource().sendFeedback(new LiteralText(kbSetFeedback + SnowballKB.config.eggKbMultiplier), false);
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
                                            cmd.getSource().sendFeedback(new LiteralText(dmgSetFeedback + SnowballKB.config.eggDamage), false);
                                            return Command.SINGLE_SUCCESS;
                                        })
                                )
                        )
                        // Gets the current global knockback multiplier
                        .then(literal("get")
                                .executes(cmd -> {
                                    cmd.getSource().sendFeedback(new LiteralText(dmgGetFeedback + SnowballKB.config.eggDamage), false);
                                    return (int)(SnowballKB.config.eggDamage * 1000);
                                })
                        )
                        // Adds a value onto the current multiplier
                        .then(literal("add")
                                .then(argument("damage", FloatArgumentType.floatArg())
                                        .executes(cmd -> {
                                            SnowballKB.config.eggDamage += FloatArgumentType.getFloat(cmd, "damage");
                                            cmd.getSource().sendFeedback(new LiteralText(dmgSetFeedback + SnowballKB.config.eggDamage), false);
                                            return Command.SINGLE_SUCCESS;
                                        })
                                )
                        )
                );
        dispatcher.register(eggKnockbackCommand);
        
        LiteralArgumentBuilder<ServerCommandSource> bobber = literal("bobberpull").requires(executor -> executor.hasPermissionLevel(2));

        bobber.then(literal("set").then(argument("value", FloatArgumentType.floatArg()).executes(cmd -> {
            SnowballKB.config.fishingRodPullMultiplier = FloatArgumentType.getFloat(cmd, "value");
            cmd.getSource().sendFeedback(new LiteralText(pullSetFeedback + SnowballKB.config.fishingRodPullMultiplier), true);
            return Command.SINGLE_SUCCESS;
        }))).then(literal("add").then(argument("value", FloatArgumentType.floatArg()).executes(cmd -> {
            SnowballKB.config.fishingRodPullMultiplier += FloatArgumentType.getFloat(cmd, "value");
            cmd.getSource().sendFeedback(new LiteralText(pullSetFeedback + SnowballKB.config.fishingRodPullMultiplier), true);
            return Command.SINGLE_SUCCESS;
        }))).then(literal("get").executes(cmd -> (int)(SnowballKB.config.fishingRodPullMultiplier * 1000)));
        dispatcher.register(bobber);
    }
}
