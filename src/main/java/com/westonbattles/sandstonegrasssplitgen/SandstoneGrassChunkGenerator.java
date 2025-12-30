package com.westonbattles.sandstonegrasssplitgen;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;

public class SandstoneGrassChunkGenerator  extends ChunkGenerator{
    
    public BiomeSource getBiomeSource() {
        return this.biomeSource;
    }

    public static final MapCodec<SandstoneGrassChunkGenerator> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            BiomeSource.CODEC.fieldOf("biome_source").forGetter(SandstoneGrassChunkGenerator::getBiomeSource)
            ).apply(instance, SandstoneGrassChunkGenerator::new)
        );


    public SandstoneGrassChunkGenerator(BiomeSource biomeSource) {
        super(biomeSource);
    }

    @Override
    public void addDebugScreenInfo(List<String> list, RandomState randomState, BlockPos blockPos) {
        return;
    }

    @Override
    public void applyCarvers(WorldGenRegion worldGenRegion, long l, RandomState randomState, BiomeManager biomeManager,
            StructureManager structureManager, ChunkAccess chunkAccess) {
        return;
    }

    @Override
    public void buildSurface(WorldGenRegion worldGenRegion, StructureManager structureManager, RandomState randomState,
            ChunkAccess chunkAccess) {
        // Surface building is done in fillFromNoise
    }

    @Override
    protected MapCodec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Blender blender, RandomState randomState,
        StructureManager structureManager, ChunkAccess chunkAccess) {
        // Fill the chunk with sandstone
        int minY = chunkAccess.getMinY();
        int maxY = 55; // Surface level

        BlockState SANDSTONE = Blocks.SANDSTONE.defaultBlockState();
        BlockState GRASS_BLOCK = Blocks.GRASS_BLOCK.defaultBlockState();
        BlockState DIRT_BLOCK = Blocks.DIRT.defaultBlockState();
        BlockState BEDROCK = Blocks.BEDROCK.defaultBlockState();

        if (chunkAccess.getPos().x < 0) {
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 16; j++) {
                    chunkAccess.setBlockState(new BlockPos(i, minY, j), BEDROCK);
                    for (int k = minY + 1; k < maxY; k++) {
                        chunkAccess.setBlockState(new BlockPos(i, k, j), DIRT_BLOCK);
                    }
                    chunkAccess.setBlockState(new BlockPos(i, maxY, j), GRASS_BLOCK);
                }
            }
        }
        else {
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 16; j++) {
                    chunkAccess.setBlockState(new BlockPos(i, minY, j), BEDROCK);
                    for (int k = minY + 1; k <= maxY; k++) {
                        chunkAccess.setBlockState(new BlockPos(i, k, j), SANDSTONE);
                    }
                }
            }
        }

        return CompletableFuture.completedFuture(chunkAccess);

    }

    @Override
    public NoiseColumn getBaseColumn(int i, int j, LevelHeightAccessor levelHeightAccessor, RandomState randomState) {
        // Return an empty noise column since we're not using noise generation
        return new NoiseColumn(levelHeightAccessor.getMinY(), new net.minecraft.world.level.block.state.BlockState[0]);
    }

    @Override
    public int getBaseHeight(int i, int j, Types types, LevelHeightAccessor levelHeightAccessor,
            RandomState randomState) {
        return 55;
    }

    @Override
    public int getGenDepth() {
        return 384; // Standard world height (from -64 to 320)
    }

    @Override
    public int getMinY() {
        return -64; // Standard minimum Y level
    }

    @Override
    public int getSeaLevel() {
        return 54;
    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion worldGenRegion) {
        return;
    }

    @Override
    public void applyBiomeDecoration(net.minecraft.world.level.WorldGenLevel worldGenLevel, ChunkAccess chunkAccess, StructureManager structureManager) {
        // Don't apply any biome decoration (no lava pools, trees, etc.)
    }


}
