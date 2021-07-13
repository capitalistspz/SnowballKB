package capitalistspz.test.commands;

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

    public static float snowKbMultiplier = 0.0f;
    public static float snowDamage = 0.001f;

    public static float eggKbMultiplier = 0.0f;
    public static float eggDamage = 0.001f;

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher){
        // Argbuilder init
        LiteralArgumentBuilder<ServerCommandSource> knockbackCommand = literal("snowkb")
                .requires(executor -> executor.hasPermissionLevel(2));

        knockbackCommand.then(literal("knockback")
                .then(literal("set")
                        .then(argument("mult", FloatArgumentType.floatArg())
                                .executes(cmd -> {
                                    snowKbMultiplier = FloatArgumentType.getFloat(cmd, "mult");
                                    cmd.getSource().sendFeedback(new LiteralText(kbSetFeedback + snowKbMultiplier), false);
                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                )
                // Gets the current global knockback multiplier
                .then(literal("get")
                        .executes(cmd -> {
                            cmd.getSource().sendFeedback(new LiteralText(kbGetFeedback + snowKbMultiplier), false);
                            return (int)(snowKbMultiplier * 1000);
                        })
                )
                // Adds a value onto the current multiplier
                .then(literal("add")
                        .then(argument("added_mult", FloatArgumentType.floatArg())
                                .executes(cmd -> {
                                    snowKbMultiplier += FloatArgumentType.getFloat(cmd, "added_mult");
                                    cmd.getSource().sendFeedback(new LiteralText(kbSetFeedback + snowKbMultiplier), false);
                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                )
        )
        .then(literal("damage")
                .then(literal("set")
                        .then(argument("damage", FloatArgumentType.floatArg())
                                .executes(cmd -> {
                                    snowDamage = FloatArgumentType.getFloat(cmd, "damage");
                                    cmd.getSource().sendFeedback(new LiteralText(dmgSetFeedback + snowDamage), false);
                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                )
                // Gets the current global knockback multiplier
                .then(literal("get")
                        .executes(cmd -> {
                            cmd.getSource().sendFeedback(new LiteralText(dmgGetFeedback + snowDamage), false);
                            return (int)(snowDamage * 1000);
                        })
                )
                // Adds a value onto the current multiplier
                .then(literal("add")
                        .then(argument("damage", FloatArgumentType.floatArg())
                                .executes(cmd -> {
                                    snowDamage += FloatArgumentType.getFloat(cmd, "damage");
                                    cmd.getSource().sendFeedback(new LiteralText(dmgSetFeedback + snowDamage), false);
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
                                    eggKbMultiplier = FloatArgumentType.getFloat(cmd, "mult");
                                    cmd.getSource().sendFeedback(new LiteralText(kbSetFeedback + eggKbMultiplier), false);
                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                )
                // Gets the current global knockback multiplier
                .then(literal("get")
                        .executes(cmd -> {
                            cmd.getSource().sendFeedback(new LiteralText(kbGetFeedback + eggKbMultiplier), false);
                            return (int)(eggKbMultiplier * 1000);
                        })
                )
                // Adds a value onto the current multiplier
                .then(literal("add")
                        .then(argument("added_mult", FloatArgumentType.floatArg())
                                .executes(cmd -> {
                                    eggKbMultiplier += FloatArgumentType.getFloat(cmd, "added_mult");
                                    cmd.getSource().sendFeedback(new LiteralText(kbSetFeedback + eggKbMultiplier), false);
                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                )
        )
                .then(literal("damage")
                        .then(literal("set")
                                .then(argument("damage", FloatArgumentType.floatArg())
                                        .executes(cmd -> {
                                            eggDamage = FloatArgumentType.getFloat(cmd, "damage");
                                            cmd.getSource().sendFeedback(new LiteralText(dmgSetFeedback + eggDamage), false);
                                            return Command.SINGLE_SUCCESS;
                                        })
                                )
                        )
                        // Gets the current global knockback multiplier
                        .then(literal("get")
                                .executes(cmd -> {
                                    cmd.getSource().sendFeedback(new LiteralText(dmgGetFeedback + eggDamage), false);
                                    return (int)(eggDamage * 1000);
                                })
                        )
                        // Adds a value onto the current multiplier
                        .then(literal("add")
                                .then(argument("damage", FloatArgumentType.floatArg())
                                        .executes(cmd -> {
                                            eggDamage += FloatArgumentType.getFloat(cmd, "damage");
                                            cmd.getSource().sendFeedback(new LiteralText(dmgSetFeedback + eggDamage), false);
                                            return Command.SINGLE_SUCCESS;
                                        })
                                )
                        )
                );
        dispatcher.register(eggKnockbackCommand);
    }
}
