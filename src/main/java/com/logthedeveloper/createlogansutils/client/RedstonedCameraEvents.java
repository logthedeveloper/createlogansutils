package com.logthedeveloper.createlogansutils.client;

import com.logthedeveloper.createlogansutils.CreateLogansUtils;
import com.logthedeveloper.createlogansutils.effect.RedstonedMobEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderFrameEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;

@EventBusSubscriber(modid = CreateLogansUtils.MODID, value = Dist.CLIENT)
public class RedstonedCameraEvents {

    // ---- drift ----
    private static final float DRIFT_STRENGTH = 5.0F;  // sideways drift, degrees per second, per level
    private static final float PITCH_STRENGTH = 1.2F;  // up/down drift, degrees per second, per level

    // ---- screen roll ----
    private static final float ROLL_STRENGTH  = 3.0F;  // screen tilt per level (degrees)

    // ---- heavy camera (resistance) ----
    private static final float YAW_RANGE    = 25.0F;   // how far you can look sideways before it pushes back (degrees)
    private static final float PITCH_RANGE  = 12.0F;   // how far you can look up/down before it pushes back (degrees)
    private static final float SOFT_SPRING  = 0.6F;    // gentle pull back toward center, even inside the range (0 = off)
    private static final float HARD_SPRING  = 12.0F;   // how hard it shoves you back once outside the range
    private static final float CHASE_SPEED  = 20.0F;   // how fast the "center" follows you (degrees/sec); lower = harder to turn

    private static long lastNanos = 0L;
    private static boolean anchorSet = false;
    private static float anchorYaw;
    private static float anchorPitch;

    /** Finds the Redstoned effect on an entity without needing the registry holder. */
    private static MobEffectInstance getRedstoned(LivingEntity entity) {
        for (MobEffectInstance inst : entity.getActiveEffects()) {
            if (inst.getEffect().value() instanceof RedstonedMobEffect) {
                return inst;
            }
        }
        return null;
    }

    /** Pushes an offset back toward zero: a soft pull inside the range, a hard shove outside it. */
    private static float springPush(float offset, float range, float dt) {
        float over = Math.max(0.0F, Math.abs(offset) - range) * Math.signum(offset);
        float push = -(offset * SOFT_SPRING + over * HARD_SPRING) * dt;
        float max = Math.abs(offset); // never overshoot past the center
        return Mth.clamp(push, -max, max);
    }

    /**
     * Runs every rendered FRAME so it's smooth at any frame rate.
     * 1) drift: slowly turns the real facing direction
     * 2) resistance: pushes the camera back toward a "center" that slowly follows you
     */
    @SubscribeEvent
    public static void onRenderFrame(RenderFrameEvent.Pre event) {
        long now = System.nanoTime();
        float dt = lastNanos == 0L ? 0.0F : (now - lastNanos) / 1.0E9F;
        lastNanos = now;
        dt = Math.min(dt, 0.1F); // avoid a big jump after lag or pausing

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) { anchorSet = false; return; }
        if (mc.isPaused() || dt <= 0.0F) return;

        MobEffectInstance inst = getRedstoned(player);
        if (inst == null) { anchorSet = false; return; }

        if (!anchorSet) {
            anchorYaw = player.getYRot();
            anchorPitch = player.getXRot();
            anchorSet = true;
        }

        float level = inst.getAmplifier() + 1;
        float t = now / 1.0E9F; // seconds

        // ---- 1) drift (moves the center too, so the resistance doesn't cancel it) ----
        float yawSpeed = (Mth.sin(t * 0.9F) + 0.5F * Mth.sin(t * 2.2F + 1.3F)) * DRIFT_STRENGTH * level;
        float pitchSpeed = Mth.sin(t * 1.4F + 2.0F) * PITCH_STRENGTH * level;
        float yawDelta = yawSpeed * dt;
        float pitchDelta = pitchSpeed * dt;

        player.setYRot(player.getYRot() + yawDelta);
        player.yRotO += yawDelta;
        anchorYaw += yawDelta;

        float newPitch = Mth.clamp(player.getXRot() + pitchDelta, -90.0F, 90.0F);
        float appliedPitch = newPitch - player.getXRot();
        player.setXRot(newPitch);
        player.xRotO += appliedPitch;
        anchorPitch += appliedPitch;

        // ---- 2) resistance: higher level = smaller range = heavier camera ----
        float scale = 1.0F / (1.0F + 0.3F * (level - 1.0F));
        float yawRange = YAW_RANGE * scale;
        float pitchRange = PITCH_RANGE * scale;

        // yaw
        float offYaw = Mth.wrapDegrees(player.getYRot() - anchorYaw);
        float yawPush = springPush(offYaw, yawRange, dt);
        player.setYRot(player.getYRot() + yawPush);
        player.yRotO += yawPush;
        offYaw += yawPush;
        anchorYaw += Mth.clamp(offYaw, -CHASE_SPEED * dt, CHASE_SPEED * dt);

        // pitch
        float offPitch = player.getXRot() - anchorPitch;
        float pitchPush = springPush(offPitch, pitchRange, dt);
        float pushedPitch = Mth.clamp(player.getXRot() + pitchPush, -90.0F, 90.0F);
        float appliedPush = pushedPitch - player.getXRot();
        player.setXRot(pushedPitch);
        player.xRotO += appliedPush;
        offPitch += appliedPush;
        anchorPitch += Mth.clamp(offPitch, -CHASE_SPEED * dt, CHASE_SPEED * dt);
        anchorPitch = Mth.clamp(anchorPitch, -90.0F, 90.0F);
    }

    /** Tilts (rolls) the screen side to side. Stronger with each level. */
    @SubscribeEvent
    public static void onCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        MobEffectInstance inst = getRedstoned(player);
        if (inst == null) return;

        float level = inst.getAmplifier() + 1;
        float time = player.tickCount + (float) event.getPartialTick();

        float roll = (Mth.sin(time * 0.05F) + 0.4F * Mth.sin(time * 0.13F)) * ROLL_STRENGTH * level;
        event.setRoll(event.getRoll() + roll);
    }
}