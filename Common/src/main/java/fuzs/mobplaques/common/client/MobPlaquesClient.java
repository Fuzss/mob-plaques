package fuzs.mobplaques.common.client;

import fuzs.mobplaques.common.client.handler.KeyBindingHandler;
import fuzs.mobplaques.common.client.handler.MobPlaqueHandler;
import fuzs.mobplaques.common.client.handler.PickEntityHandler;
import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.common.api.client.core.v1.context.KeyMappingsContext;
import fuzs.puzzleslib.common.api.client.event.v1.ClientTickEvents;
import fuzs.puzzleslib.common.api.client.event.v1.renderer.ComputeCameraAnglesCallback;
import fuzs.puzzleslib.common.api.client.event.v1.renderer.ExtractEntityRenderStateCallback;
import fuzs.puzzleslib.common.api.client.event.v1.renderer.SubmitNameTagCallback;
import net.minecraft.client.Minecraft;

public class MobPlaquesClient implements ClientModConstructor {

    @Override
    public void onConstructMod() {
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        ExtractEntityRenderStateCallback.EVENT.register(MobPlaqueHandler::onExtractEntityRenderState);
        SubmitNameTagCallback.EVENT.register(MobPlaqueHandler::onSubmitNameTag);
        ComputeCameraAnglesCallback.EVENT.register((camera, partialTick, pitch, yaw, roll) -> {
            PickEntityHandler.onBeforeGameRender(Minecraft.getInstance(),
                    Minecraft.getInstance().gameRenderer,
                    Minecraft.getInstance().getDeltaTracker());
        });
        ClientTickEvents.START.register(PickEntityHandler::onStartClientTick);
    }

    @Override
    public void onRegisterKeyMappings(KeyMappingsContext context) {
        KeyBindingHandler.onRegisterKeyMappings(context);
    }
}
