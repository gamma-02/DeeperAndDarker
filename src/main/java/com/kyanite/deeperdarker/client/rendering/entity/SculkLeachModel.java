package com.kyanite.deeperdarker.client.rendering.entity;

import com.kyanite.deeperdarker.DeeperAndDarker;
import com.kyanite.deeperdarker.registry.entities.custom.SculkLeachEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SculkLeachModel extends AnimatedGeoModel<SculkLeachEntity> {
    @Override
    public ResourceLocation getModelResource(SculkLeachEntity object) {
        return new ResourceLocation(DeeperAndDarker.MOD_ID, "geo/sculk_leach.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SculkLeachEntity object) {
        return new ResourceLocation(DeeperAndDarker.MOD_ID, "textures/entity/sculk_leach.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SculkLeachEntity animatable) {
        return new ResourceLocation(DeeperAndDarker.MOD_ID, "animations/sculk_leach.animation.json");
    }
}
