package fuzs.mobplaques.client.gui.plaque;

import fuzs.mobplaques.client.renderer.entity.state.MobPlaquesRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;

public class ArmorPlaqueRenderer extends MobPlaqueRenderer {
    private static final Identifier ARMOR_FULL_SPRITE = Identifier.withDefaultNamespace(
            "hud/armor_full");

    @Override
    public int getValue(MobPlaquesRenderState renderState) {
        return renderState.armor;
    }

    @Override
    protected Identifier getSprite(MobPlaquesRenderState renderState) {
        return ARMOR_FULL_SPRITE;
    }

    @Override
    public void extractRenderState(LivingEntity livingEntity, MobPlaquesRenderState renderState, float partialTick) {
        super.extractRenderState(livingEntity, renderState, partialTick);
        renderState.armor = livingEntity.getArmorValue();
    }

    @Override
    public String getName() {
        return "Armor";
    }
}
