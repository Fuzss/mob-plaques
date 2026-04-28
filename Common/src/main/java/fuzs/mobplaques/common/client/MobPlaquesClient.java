package fuzs.mobplaques.common.client;

import fuzs.mobplaques.common.client.handler.KeyBindingHandler;
import fuzs.mobplaques.common.client.handler.MobPlaqueHandler;
import fuzs.mobplaques.common.client.handler.PickEntityHandler;
import fuzs.mobplaques.common.client.renderer.rendertype.ModRenderType;
import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.common.api.client.core.v1.context.KeyMappingsContext;
import fuzs.puzzleslib.common.api.client.core.v1.context.RenderPipelinesContext;
import fuzs.puzzleslib.common.api.client.event.v1.ClientTickEvents;
import fuzs.puzzleslib.common.api.client.event.v1.renderer.ExtractEntityRenderStateCallback;
import fuzs.puzzleslib.common.api.client.event.v1.renderer.GameRenderEvents;
import fuzs.puzzleslib.common.api.client.event.v1.renderer.SubmitNameTagCallback;

public class MobPlaquesClient implements ClientModConstructor {

    @Override
    public void onConstructMod() {
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        ExtractEntityRenderStateCallback.EVENT.register(MobPlaqueHandler::onExtractEntityRenderState);
        SubmitNameTagCallback.EVENT.register(MobPlaqueHandler::onSubmitNameTag);
        GameRenderEvents.BEFORE.register(PickEntityHandler::onBeforeGameRender);
        ClientTickEvents.START.register(PickEntityHandler::onStartClientTick);
    }

    @Override
    public void onRegisterKeyMappings(KeyMappingsContext context) {
        KeyBindingHandler.onRegisterKeyMappings(context);
    }

    @Override
    public void onRegisterRenderPipelines(RenderPipelinesContext context) {
        context.registerRenderPipeline(ModRenderType.TEXT_BACKGROUND_PIPELINE);
    }
}
