package io.github.techdweebgaming.modularjda.api.configs.baseConfigs;

import com.electronwill.nightconfig.core.conversion.Path;
import io.github.techdweebgaming.modularjda.api.configs.ConfigSpecEntry;
import io.github.techdweebgaming.modularjda.api.configs.IConfig;

public class CoreConfig implements IConfig {

    @Path("bot.token")
    @ConfigSpecEntry
    public String token;

}
