package fuzs.mobplaques.common.data.client;

import fuzs.mobplaques.common.MobPlaques;
import fuzs.mobplaques.common.client.handler.KeyBindingHandler;
import fuzs.puzzleslib.common.api.client.data.v3.language.AbstractLanguageProvider;
import fuzs.puzzleslib.common.api.data.v3.core.DataProviderContext;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTranslations() {
        this.addKeyCategory(MobPlaques.MOD_ID, MobPlaques.MOD_NAME);
        this.add(KeyBindingHandler.TOGGLE_PLAQUES_KEY_MAPPING, "Toggle Mob Plaques");
        this.add(KeyBindingHandler.KEY_MOB_PLAQUES_STATUS, "Render Mob Plaques: %s");
    }
}
