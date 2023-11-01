package config;

import lombok.Getter;

@Getter
public class BotConfig {
    private final String botName = AppConfig.getProperty("bot.name");
    private final String token = AppConfig.getProperty("bot.token");
}

