package io.github.qMartinz.paranormal.registry;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import io.github.qMartinz.paranormal.api.PlayerData;
import io.github.qMartinz.paranormal.api.powers.PowerRegistry;
import io.github.qMartinz.paranormal.api.rituals.RitualRegistry;
import io.github.qMartinz.paranormal.server.data.StateSaverAndLoader;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class CommandRegistry {
	public static void registerCommands(){
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			// PEX commands
			dispatcher.register(literal("pex").requires(source -> source.hasPermissionLevel(2))
					.then(argument("player", EntityArgumentType.player())
					// Set PEX level
					.then(literal("set").then(argument("amount", IntegerArgumentType.integer(0, 20))
							.executes(context -> {
								ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
								PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
								final int amount = IntegerArgumentType.getInteger(context, "amount");

								playerData.setPex(amount);
								playerData.syncToClient(player);
								return 1;
							})))
					// Reset all player data
					.then(literal("reset")
							.executes(context -> {
								ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
								PlayerData playerData = StateSaverAndLoader.getPlayerState(player);

								playerData.setPex(0);
								playerData.setXp(0);
								playerData.setAttPoints(0);
								playerData.setPowerPoints(0);
								playerData.setRitualSlots(0);
								playerData.setAttribute(0, 0);
								playerData.setAttribute(1, 0);
								playerData.setAttribute(2, 0);
								playerData.clearRituals();
								playerData.clearPowers();
								playerData.syncToClient(player);
								return 1;
							}))
					.then(literal("xp")
							// Add xp
							.then(literal("add").then(argument("amount", IntegerArgumentType.integer(1))
									.executes(context -> {
										ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
										PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
										final int amount = IntegerArgumentType.getInteger(context, "amount");

										playerData.addXp(amount);
										playerData.syncToClient(player);
										return 1;
									}))))
					.then(literal("attributes")
							.then(literal("points")
									// Add attribute points
									.then(literal("add").then(argument("amount", IntegerArgumentType.integer(1))
											.executes(context -> {
												ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
												PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
												final int amount = IntegerArgumentType.getInteger(context, "amount");

												playerData.setAttPoints(playerData.getAttPoints() + amount);
												playerData.syncToClient(player);
												return 1;
											})))
									// Remove attribute points
									.then(literal("remove").then(argument("amount", IntegerArgumentType.integer(1))
											.executes(context -> {
												ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
												PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
												final int amount = IntegerArgumentType.getInteger(context, "amount");

												playerData.setAttPoints(playerData.getAttPoints() - amount);
												playerData.syncToClient(player);
												return 1;
											}))))
							.then(literal("strength")
									// Add strength
									.then(literal("add").then(argument("amount", IntegerArgumentType.integer(1))
											.executes(context -> {
												ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
												PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
												final int amount = IntegerArgumentType.getInteger(context, "amount");

												playerData.setAttribute(0, playerData.getAttribute(0) + amount);
												playerData.syncToClient(player);
												return 1;
											})))
									// Remove strength
									.then(literal("remove").then(argument("amount", IntegerArgumentType.integer(1))
											.executes(context -> {
												ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
												PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
												final int amount = IntegerArgumentType.getInteger(context, "amount");

												playerData.setAttribute(0, playerData.getAttribute(0) - amount);
												playerData.syncToClient(player);
												return 1;
											}))))
							.then(literal("vigor")
									// Add vigor
									.then(literal("add").then(argument("amount", IntegerArgumentType.integer(1))
											.executes(context -> {
												ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
												PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
												final int amount = IntegerArgumentType.getInteger(context, "amount");

												playerData.setAttribute(1, playerData.getAttribute(1) + amount);
												playerData.syncToClient(player);
												return 1;
											})))
									// Remove vigor
									.then(literal("remove").then(argument("amount", IntegerArgumentType.integer(1))
											.executes(context -> {
												ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
												PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
												final int amount = IntegerArgumentType.getInteger(context, "amount");

												playerData.setAttribute(1, playerData.getAttribute(1) - amount);
												playerData.syncToClient(player);
												return 1;
											}))))
							.then(literal("presence")
									// Add presence
									.then(literal("add").then(argument("amount", IntegerArgumentType.integer(1))
											.executes(context -> {
												ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
												PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
												final int amount = IntegerArgumentType.getInteger(context, "amount");

												playerData.setAttribute(2, playerData.getAttribute(2) + amount);
												playerData.syncToClient(player);
												return 1;
											})))
									// Remove presence
									.then(literal("remove").then(argument("amount", IntegerArgumentType.integer(1))
											.executes(context -> {
												ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
												PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
												final int amount = IntegerArgumentType.getInteger(context, "amount");

												playerData.setAttribute(2, playerData.getAttribute(2) - amount);
												playerData.syncToClient(player);
												return 1;
											})))
							))));

			// Ritual commands
			dispatcher.register(literal("rituals").requires(source -> source.hasPermissionLevel(2))
					.then(argument("player", EntityArgumentType.player())
					.then(literal("slots")
							// Add ritual slots
							.then(literal("add").then(argument("amount", IntegerArgumentType.integer(1))
									.executes(context -> {
										ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
										PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
										final int amount = IntegerArgumentType.getInteger(context, "amount");

										playerData.setRitualSlots(playerData.getRitualSlots() + amount);
										playerData.syncToClient(player);
										return 1;
									})))
							// Remove ritual slots
							.then(literal("remove").then(argument("amount", IntegerArgumentType.integer(1))
									.executes(context -> {
										ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
										PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
										final int amount = IntegerArgumentType.getInteger(context, "amount");

										playerData.setRitualSlots(playerData.getRitualSlots() - amount);
										playerData.syncToClient(player);
										return 1;
									}))))
					// Add ritual
					.then(literal("add").then(argument("id", IdentifierArgumentType.identifier()).suggests((context, builder) -> {
						for (Identifier id : RitualRegistry.rituals.keySet()){
							builder.suggest(id.toString());
						}
						return builder.buildFuture();
					})).executes(context -> {
						ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
						PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
						final Identifier id = IdentifierArgumentType.getIdentifier(context, "id");

						if (playerData.rituals.size() < playerData.getRitualSlots()) RitualRegistry.getRitual(id).ifPresent(playerData::addRitual);
						playerData.syncToClient(player);
						return 1;
					}))
					// Remove ritual
					.then(literal("remove").then(argument("id", IdentifierArgumentType.identifier()).suggests((context, builder) -> {
						for (Identifier id : RitualRegistry.rituals.keySet()){
							builder.suggest(id.toString());
						}
						return builder.buildFuture();
					})).executes(context -> {
						ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
						PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
						final Identifier id = IdentifierArgumentType.getIdentifier(context, "id");

						RitualRegistry.getRitual(id).ifPresent(playerData::removeRitual);
						playerData.syncToClient(player);
						return 1;
					}))
					));

			// Power commands
			dispatcher.register(literal("powers").requires(source -> source.hasPermissionLevel(2))
					.then(argument("player", EntityArgumentType.player())
					.then(literal("points")
							// Add power points
							.then(literal("add").then(argument("amount", IntegerArgumentType.integer(1))
									.executes(context -> {
										ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
										PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
										final int amount = IntegerArgumentType.getInteger(context, "amount");

										playerData.setPowerPoints(playerData.getPowerPoints() + amount);
										playerData.syncToClient(player);
										return 1;
									})))
							// Remove power points
							.then(literal("remove").then(argument("amount", IntegerArgumentType.integer(1))
									.executes(context -> {
										ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
										PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
										final int amount = IntegerArgumentType.getInteger(context, "amount");

										playerData.setPowerPoints(playerData.getPowerPoints() - amount);
										playerData.syncToClient(player);
										return 1;
									}))))
					// Add power
					.then(literal("add").then(argument("id", IdentifierArgumentType.identifier()).suggests((context, builder) -> {
						for (Identifier id : PowerRegistry.powers.keySet()){
							builder.suggest(id.toString());
						}
						return builder.buildFuture();
					})).executes(context -> {
						ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
						PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
						final Identifier id = IdentifierArgumentType.getIdentifier(context, "id");

						PowerRegistry.getPower(id).ifPresent(playerData::addPower);
						playerData.syncToClient(player);
						return 1;
					}))
					// Remove power
					.then(literal("remove").then(argument("id", IdentifierArgumentType.identifier()).suggests((context, builder) -> {
						for (Identifier id : RitualRegistry.rituals.keySet()){
							builder.suggest(id.toString());
						}
						return builder.buildFuture();
					})).executes(context -> {
						ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
						PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
						final Identifier id = IdentifierArgumentType.getIdentifier(context, "id");

						PowerRegistry.getPower(id).ifPresent(playerData::removePower);
						playerData.syncToClient(player);
						return 1;
					}))
					));
		});
	}
}
