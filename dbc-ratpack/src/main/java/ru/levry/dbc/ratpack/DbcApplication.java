package ru.levry.dbc.ratpack;

import ratpack.config.ConfigData;
import ratpack.func.Action;
import ratpack.guice.Guice;
import ratpack.handling.Chain;
import ratpack.handling.Context;
import ratpack.hikari.HikariModule;
import ratpack.registry.Registry;
import ratpack.server.RatpackServer;
import ru.levry.dbc.CreateScript;
import ru.levry.dbc.SelectScript;
import ru.levry.dbc.UpdateScript;
import ru.levry.dbc.ratpack.handlers.DataCallbackHandler;
import ru.levry.dbc.support.DataCallback;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author levry
 */
public class DbcApplication {

    private final DataConfig dataConfig;

    private DbcApplication(DataConfig dataConfig) {
        this.dataConfig = dataConfig;
    }

    public static void main(String[] args) throws Exception {

        if(!(args.length > 0)) {
            throw new RuntimeException("Not specify a path to config");
        }

        ConfigData configData = ConfigData.of(b -> {
            Path configPath = Paths.get(args[0]).toAbsolutePath();
            b.yaml(configPath).build();
        });
        DataConfig dataConfig = configData.get("/database", DataConfig.class);

        new DbcApplication(dataConfig).startSrever();
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
            .path(":schema/:table/create", new DataCallbackHandler(supplyScript(CreateScript::new)))
            .path(":schema/:table/select", new DataCallbackHandler(supplyScript(SelectScript::new)))
            .path(":schema/:table/update", new DataCallbackHandler(supplyScript(UpdateScript::new)));
    }

    private static Function<Context, DataCallback<?>> supplyScript(BiFunction<String, String, DataCallback<?>> supplier) {
        return ctx -> {
            String schema = ctx.getPathTokens().get("schema");
            String table = ctx.getPathTokens().get("table");
            return supplier.apply(schema, table);
        };
    }

}
