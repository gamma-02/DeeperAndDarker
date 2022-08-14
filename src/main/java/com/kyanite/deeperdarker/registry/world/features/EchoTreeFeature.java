package com.kyanite.deeperdarker.registry.world.features;

import com.kyanite.deeperdarker.registry.blocks.DDBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.WeepingVinesFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

import java.util.function.BiConsumer;

public class EchoTreeFeature extends Feature<NoneFeatureConfiguration> {
    public EchoTreeFeature(Codec<NoneFeatureConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext) {
        int height = pContext.random().nextInt(6, 9);

        if(pContext.random().nextInt(0, 2) == 0) return false;
        if(!pContext.level().getBlockState(pContext.origin().below()).is(Blocks.SCULK)) return false;
        if(noSpace(pContext.level(), pContext.origin(), height + 2)) return false;

        int logs;
        for(logs = 0; logs < height; logs++) {
            BlockPos logPos = new BlockPos(pContext.origin().getX(), pContext.origin().above(logs).getY(), pContext.origin().getZ());
            pContext.level().setBlock(logPos, DDBlocks.ECHO_LOG.get().defaultBlockState(), 3);
        }

        placeStems(pContext.level(), pContext.origin());
        placeLeaves(pContext.level(), new BlockPos(pContext.origin().getX(), pContext.origin().getY() + height, pContext.origin().getZ()), pContext.random());

        return true;
    }

    public boolean noSpace(WorldGenLevel getter, BlockPos origin, int distance) {
        for(int i = 0; i < distance; i++) {
            if(!getter.getBlockState(origin.above(i)).is(Blocks.AIR)) return true;
        }
        return false;
    }

    public void placeLeaves(WorldGenLevel level, BlockPos origin, RandomSource randomSources) {
        int x = origin.getX();
        int y = origin.getY();
        int z = origin.getZ();
        int x1 = 0;
        int z1 = 0;

        for(x1 = 0; x1 < randomSources.nextInt(4, 5); x1++) {
            tryPlaceLeaf(level, new BlockPos(x + x1, y, z));
            tryPlaceLeaf(level, new BlockPos(x - x1, y, z));
            for(z1 = 0; z1 < randomSources.nextInt(4, 5); z1++) {
                tryPlaceLeaf(level, new BlockPos(x, y, z + z1));
                tryPlaceLeaf(level, new BlockPos(x, y, z - z1));
            }
        }

        int x2 = 0;

        for(x2 = 0; x2 < randomSources.nextInt(3, 5); x2++) {
            tryPlaceLeaf(level, new BlockPos(x - x2, y, z - x2));
            tryPlaceLeaf(level, new BlockPos(x + x2, y, z + x2));
            tryPlaceLeaf(level, new BlockPos(x - x2, y, z + x2));
            tryPlaceLeaf(level, new BlockPos(x + x2, y, z - x2));
        }

        placeHanging(randomSources, level, new BlockPos(x + x1, y, z));
        placeHanging(randomSources, level, new BlockPos(x - x1, y, z));
        placeHanging(randomSources, level, new BlockPos(x, y, z + z1));
        placeHanging(randomSources, level, new BlockPos(x, y, z - z1));

        placeHanging(randomSources, level, new BlockPos(x + x2, y, z - x2));
        placeHanging(randomSources, level, new BlockPos(x - x2, y, z + x2));
        placeHanging(randomSources, level, new BlockPos(x + x2, y, z + x2));
        placeHanging(randomSources, level, new BlockPos(x - x2, y, z - x2));

        tryPlaceLeaf(level, new BlockPos(origin.getX(), origin.getY() + 1, origin.getZ()));

        tryPlaceLeaf(level, new BlockPos(origin.getX() - 1, origin.getY() - 1, origin.getZ()));
        tryPlaceLeaf(level, new BlockPos(origin.getX() + 1, origin.getY() - 1, origin.getZ()));
        tryPlaceLeaf(level, new BlockPos(origin.getX(), origin.getY() - 1, origin.getZ() - 1));
        tryPlaceLeaf(level, new BlockPos(origin.getX(), origin.getY() - 1, origin.getZ() + 1));
        tryPlaceLeaf(level, new BlockPos(origin.getX() - 1, origin.getY() - 1, origin.getZ() - 1));
        tryPlaceLeaf(level, new BlockPos(origin.getX() + 1, origin.getY() - 1, origin.getZ() + 1));
        tryPlaceLeaf(level, new BlockPos(origin.getX() + 1, origin.getY() - 1, origin.getZ() - 1));
        tryPlaceLeaf(level, new BlockPos(origin.getX() - 1, origin.getY() - 1, origin.getZ() + 1));
    }

    public void placeHanging(RandomSource source, WorldGenLevel level, BlockPos pos) {
        for(int dripAmount = 0; dripAmount < source.nextInt(2, 5    ); dripAmount++) {
            tryPlaceLeaf(level, new BlockPos(pos.getX(), pos.getY() - dripAmount, pos.getZ()));
        }
    }
    public void placeStems(WorldGenLevel level, BlockPos origin) {
        int x = origin.getX();
        int y = origin.getY();
        int z = origin.getZ();

        level.setBlock(new BlockPos(x - 1, y, z), DDBlocks.ECHO_WOOD.get().defaultBlockState(), 3);
        level.setBlock(new BlockPos(x - 2, y, z), DDBlocks.ECHO_WOOD.get().defaultBlockState(), 3);
        level.setBlock(new BlockPos(x - 1, y + 1, z), DDBlocks.ECHO_WOOD.get().defaultBlockState(), 3);

        level.setBlock(new BlockPos(x + 1, y, z), DDBlocks.ECHO_WOOD.get().defaultBlockState(), 3);
        level.setBlock(new BlockPos(x + 2, y, z), DDBlocks.ECHO_WOOD.get().defaultBlockState(), 3);
        level.setBlock(new BlockPos(x + 1, y + 1, z), DDBlocks.ECHO_WOOD.get().defaultBlockState(), 3);

        level.setBlock(new BlockPos(x, y, z - 1), DDBlocks.ECHO_WOOD.get().defaultBlockState(), 3);
        level.setBlock(new BlockPos(x, y, z - 2), DDBlocks.ECHO_WOOD.get().defaultBlockState(), 3);
        level.setBlock(new BlockPos(x, y + 1, z - 1), DDBlocks.ECHO_WOOD.get().defaultBlockState(), 3);

        level.setBlock(new BlockPos(x, y, z + 1), DDBlocks.ECHO_WOOD.get().defaultBlockState(), 3);
        level.setBlock(new BlockPos(x, y, z + 2), DDBlocks.ECHO_WOOD.get().defaultBlockState(), 3);
        level.setBlock(new BlockPos(x, y + 1, z + 1), DDBlocks.ECHO_WOOD.get().defaultBlockState(), 3);
    }

    public void tryPlaceLeaf(WorldGenLevel level, BlockPos pos) {
        if (TreeFeature.validTreePos(level, pos)) {
            if(level.getRandom().nextInt(0, 12) == 0)
                setBlock(level, pos, DDBlocks.SCULK_GLEAM.get().defaultBlockState());
            else
                setBlock(level, pos, DDBlocks.ECHO_LEAVES.get().defaultBlockState());
        }
    }
}
