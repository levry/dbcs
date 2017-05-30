package ru.levry.dbc.ratpack;

import ratpack.config.ConfigData;
import ratpack.func.Action;
import ratpack.guice.Guice;
import ratpack.handling.Chain;
import ratpack.hikari.HikariModule;
import ratpack.registry.Registry;
import ratpack.server.RatpackServer;
import ru.levry.dbc.CreateScript;
import ru.levry.dbc.DataConfig;
import ru.levry.dbc.SelectScript;
import ru.levry.dbc.UpdateScript;
import ru.levry.dbc.ratpack.handlers.TableScriptHandler;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author levry
 */
public class DbcApplication {

    private final DataConfig dataConfig;

    private DbcApplication(DataConfig dataConfig) {
        this.dataConfig = dataConfig;
    }

    public static void main(String[] args) throws Exception {
        DataConfig dataConfig = readConfig(args);
        new DbcApplication(dataConfig).startSrever();
    }

    private static DataConfig readConfig(String[] args) throws Exception {
        if(!(args.length > 0)) {
            throw new RuntimeException("Not specify a path to config");
        }

        ConfigData configData = ConfigData.of(b -> {
            Path configPath = Paths.get(args[0]).toAbsolutePath();
            b.yaml(configPath).build();
        });
        return configData.get("/database", DataConfig.class);
    }

    private void startSrever() throws Exception {
        RatpackServer.start(s -> s
            .registry(registry())
            .handlers(chain()));
    }

    private ratpack.func.Function<Registry, Registry> registry() {
        return Guice.registry(bindings -> bindings
            .module(HikariModule.class, hikariConfig -> {
                hikariConfig.setDriverClassName(dataConfig.getDriverClass());
                hikariConfig.setJdbcUrl(dataConfig.getUrl());
                hikariConfig.setUsername(dataConfig.getUsername());
                hikariConfig.setPassword(dataConfig.getPassword());
            })
            .bind(DataOperations.class)
        );
    }

    private Action<Chain> chain() {
        return chain -> chain
            .path(":schema/:table/create", new TableScriptHandler(CreateScript::new))
            .path(":schema/:table/select", new TableScriptHandler(SelectScript::new))
            .path(":schema/:table/update", new TableScriptHandler(UpdateScript::new));
    }

}
