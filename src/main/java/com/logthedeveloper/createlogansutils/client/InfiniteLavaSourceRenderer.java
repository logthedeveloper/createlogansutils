package com.logthedeveloper.createlogansutils.client;

import com.logthedeveloper.createlogansutils.block.entity.InfiniteLavaSourceBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;

// Draws a lava-textured box inside the tank's window cavity every frame.
// Since the block is always "full", there's no fill-level math needed -
// it's just rendered at max height constantly.
public class InfiniteLavaSourceRenderer implements BlockEntityRenderer<InfiniteLavaSourceBlockEntity> {

    // Matches the actual open cavity in your Blockbench model: the frame's
    // corner posts occupy x/z 0-4 and 12-16, so the fluid box has to stay
    // fully inside the center to avoid overlapping (and z-fighting with)
    // that solid frame geometry.
    private static final float X0 = 1 / 16f, X1 = 15 / 16f;
    private static final float Y0 = 1 / 16f, Y1 = 15 / 16f;
    private static final float Z0 = 1 / 16f, Z1 = 15 / 16f;

    public InfiniteLavaSourceRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(InfiniteLavaSourceBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

        IClientFluidTypeExtensions extensions = IClientFluidTypeExtensions.of(Fluids.LAVA);
        TextureAtlasSprite sprite = Minecraft.getInstance()
                .getModelManager()
                .getAtlas(TextureAtlas.LOCATION_BLOCKS)
                .getSprite(extensions.getStillTexture());

        int tint = extensions.getTintColor();
        float a = ((tint >> 24) & 0xFF) / 255f;
        if (a <= 0f) a = 1f; // lava's tint has no alpha channel set, default to fully opaque
        float r = ((tint >> 16) & 0xFF) / 255f;
        float g = ((tint >> 8) & 0xFF) / 255f;
        float b = (tint & 0xFF) / 255f;

        VertexConsumer consumer = bufferSource.getBuffer(RenderType.translucent());
        PoseStack.Pose pose = poseStack.last();

        // Top and bottom faces deliberately omitted: they'd sit exactly
        // coplanar with the model's own Lid (bottom face, y=12) and Bottom
        // (top face, y=4) elements, causing z-fighting/flickering. Those
        // faces aren't visible from outside anyway - only the 4 side
        // windows are, so the side faces alone are all that's needed.
        quad(consumer, pose, sprite, r, g, b, a, packedLight,
                X0, Y0, Z0, X1, Y0, Z0, X1, Y1, Z0, X0, Y1, Z0, 0, 0, -1); // north
        quad(consumer, pose, sprite, r, g, b, a, packedLight,
                X1, Y0, Z1, X0, Y0, Z1, X0, Y1, Z1, X1, Y1, Z1, 0, 0, 1); // south
        quad(consumer, pose, sprite, r, g, b, a, packedLight,
                X1, Y0, Z0, X1, Y0, Z1, X1, Y1, Z1, X1, Y1, Z0, 1, 0, 0); // east
        quad(consumer, pose, sprite, r, g, b, a, packedLight,
                X0, Y0, Z1, X0, Y0, Z0, X0, Y1, Z0, X0, Y1, Z1, -1, 0, 0); // west
    }

    private void quad(VertexConsumer consumer, PoseStack.Pose pose, TextureAtlasSprite sprite,
                      float r, float g, float b, float a, int light,
                      float x1, float y1, float z1,
                      float x2, float y2, float z2,
                      float x3, float y3, float z3,
                      float x4, float y4, float z4,
                      float nx, float ny, float nz) {
        // Drawn both forwards and reversed so the face is visible from
        // either side, regardless of which way its winding order actually
        // faces - avoids relying on getting culling direction exactly right.
        quadOneSide(consumer, pose, sprite, r, g, b, a, light, x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4, nx, ny, nz);
        quadOneSide(consumer, pose, sprite, r, g, b, a, light, x4, y4, z4, x3, y3, z3, x2, y2, z2, x1, y1, z1, -nx, -ny, -nz);
    }

    private void quadOneSide(VertexConsumer consumer, PoseStack.Pose pose, TextureAtlasSprite sprite,
                             float r, float g, float b, float a, int light,
                             float x1, float y1, float z1,
                             float x2, float y2, float z2,
                             float x3, float y3, float z3,
                             float x4, float y4, float z4,
                             float nx, float ny, float nz) {
        float u0 = sprite.getU0(), u1 = sprite.getU1();
        float v0 = sprite.getV0(), v1 = sprite.getV1();

        consumer.addVertex(pose, x1, y1, z1).setColor(r, g, b, a).setUv(u0, v0)
                .setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, nx, ny, nz);
        consumer.addVertex(pose, x2, y2, z2).setColor(r, g, b, a).setUv(u1, v0)
                .setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, nx, ny, nz);
        consumer.addVertex(pose, x3, y3, z3).setColor(r, g, b, a).setUv(u1, v1)
                .setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, nx, ny, nz);
        consumer.addVertex(pose, x4, y4, z4).setColor(r, g, b, a).setUv(u0, v1)
                .setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, nx, ny, nz);
    }
}