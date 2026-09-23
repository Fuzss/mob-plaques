package fuzs.mobplaques.common.client.gui.plaque;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.mobplaques.common.MobPlaques;
import fuzs.mobplaques.common.client.renderer.entity.state.MobPlaquesRenderState;
import fuzs.mobplaques.common.config.ClientConfig;
import fuzs.puzzleslib.common.api.config.v3.ValueCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.data.AtlasIds;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.objects.AtlasSprite;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.ModConfigSpec;

public abstract class MobPlaqueRenderer {
    private static final int BACKGROUND_BORDER_SIZE = 1;

    protected boolean allowRendering;

    public boolean isRenderingAllowed(MobPlaquesRenderState renderState) {
        return this.allowRendering && this.getValue(renderState) > 0;
    }

    public int getWidth(MobPlaquesRenderState renderState) {
        return Minecraft.getInstance().font.width(this.getComponent(renderState)) + 2;
    }

    public int getHeight(MobPlaquesRenderState renderState) {
        return Minecraft.getInstance().font.lineHeight + 2;
    }

    public abstract int getValue(MobPlaquesRenderState renderState);

    protected MutableComponent getTextComponent(MobPlaquesRenderState renderState) {
        return Component.literal(this.getValue(renderState) + "x");
    }

    public final Component getComponent(MobPlaquesRenderState renderState) {
        return this.getTextComponent(renderState)
                .append(Component.object(new AtlasSprite(AtlasIds.GUI, this.getSprite(renderState))).withColor(-1));
    }

    protected int getColor(MobPlaquesRenderState renderState) {
        return -1;
    }

    public void submit(int posX, int posY, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, EntityRenderState entityRenderState, MobPlaquesRenderState renderState) {
        this.submitTextBackground(poseStack, posX, posY, submitNodeCollector, entityRenderState, renderState);
        this.submitTextComponent(poseStack, posX, posY, submitNodeCollector, entityRenderState, renderState);
    }

    private void submitTextBackground(PoseStack poseStack, int posX, int posY, SubmitNodeCollector submitNodeCollector, EntityRenderState entityRenderState, MobPlaquesRenderState renderState) {
        if (MobPlaques.CONFIG.get(ClientConfig.class).renderBackground) {
            int backgroundColor = Minecraft.getInstance().options.getBackgroundColor(0.25F);
            int minX = posX - this.getWidth(renderState) / 2;
            int minY = posY;
            int maxX = posX + this.getWidth(renderState) / 2;
            int maxY = posY + this.getHeight(renderState);
            Font.DisplayMode displayMode =
                    MobPlaques.CONFIG.get(ClientConfig.class).behindWalls ? Font.DisplayMode.SEE_THROUGH :
                            Font.DisplayMode.NORMAL;
            int lightCoords = MobPlaques.CONFIG.get(ClientConfig.class).fullBrightness ? LightCoordsUtil.FULL_BRIGHT :
                    entityRenderState.lightCoords;
            submitNodeCollector.submitTextBackground(poseStack,
                    minX,
                    minY,
                    maxX,
                    maxY,
                    backgroundColor,
                    displayMode,
                    lightCoords);
        }
    }

    private void submitTextComponent(PoseStack poseStack, int posX, int posY, SubmitNodeCollector submitNodeCollector, EntityRenderState entityRenderState, MobPlaquesRenderState renderState) {
        FormattedCharSequence formattedCharSequence = this.getComponent(renderState).getVisualOrderText();
        int x = posX - this.getWidth(renderState) / 2 + BACKGROUND_BORDER_SIZE;
        int y = posY + BACKGROUND_BORDER_SIZE + 1;
        int lightCoords = MobPlaques.CONFIG.get(ClientConfig.class).fullBrightness ? LightCoordsUtil.FULL_BRIGHT :
                entityRenderState.lightCoords;
        if (MobPlaques.CONFIG.get(ClientConfig.class).behindWalls) {
            // this does not respect the light level, use some very low alpha so it does not appear too bright
            submitNodeCollector.order(1)
                    .submitText(poseStack,
                            x,
                            y,
                            formattedCharSequence,
                            MobPlaques.CONFIG.get(ClientConfig.class).renderTextShadow,
                            Font.DisplayMode.SEE_THROUGH,
                            lightCoords,
                            ARGB.color(MobPlaques.CONFIG.get(ClientConfig.class).fullBrightness ? 0x80 : 0x20,
                                    this.getColor(renderState)),
                            0,
                            0);
        }

        submitNodeCollector.order(1)
                .submitText(poseStack,
                        x,
                        y,
                        formattedCharSequence,
                        MobPlaques.CONFIG.get(ClientConfig.class).renderTextShadow,
                        Font.DisplayMode.NORMAL,
                        lightCoords,
                        ARGB.opaque(this.getColor(renderState)),
                        0,
                        0);
    }

    protected abstract Identifier getSprite(MobPlaquesRenderState renderState);

    public void setupConfig(ModConfigSpec.Builder builder, ValueCallback callback) {
        callback.accept(builder.comment("Allow for rendering this type of plaque.").define("allow_rendering", true),
                v -> this.allowRendering = v);
    }

    public void extractRenderState(LivingEntity livingEntity, MobPlaquesRenderState renderState, float partialTick) {
        // NO-OP
    }

    public abstract String getName();
}
