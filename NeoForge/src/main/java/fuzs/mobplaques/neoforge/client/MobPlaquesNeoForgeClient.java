package fuzs.mobplaques.neoforge.client;

import fuzs.mobplaques.common.MobPlaques;
import fuzs.mobplaques.common.client.MobPlaquesClient;
import fuzs.mobplaques.common.data.client.ModLanguageProvider;
import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = MobPlaques.MOD_ID, dist = Dist.CLIENT)
public class MobPlaquesNeoForgeClient {

    public MobPlaquesNeoForgeClient() {
        ClientModConstructor.construct(MobPlaques.MOD_ID, MobPlaquesClient::new);
        DataProviderHelper.registerDataProviders(MobPlaques.MOD_ID, ModLanguageProvider::new);
    }
}
