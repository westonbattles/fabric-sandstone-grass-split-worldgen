package com.westonbattles.sandstonegrasssplitgen;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SandstoneGrassSplitGen implements ModInitializer {
	public static final String MOD_ID = "sandstone-grass-split-gen";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
        
        Registry.register(BuiltInRegistries.CHUNK_GENERATOR, Identifier.fromNamespaceAndPath(MOD_ID,"sandstone_grass"), SandstoneGrassChunkGenerator.CODEC);

		LOGGER.info("Hello Fabric world!");
	}
}