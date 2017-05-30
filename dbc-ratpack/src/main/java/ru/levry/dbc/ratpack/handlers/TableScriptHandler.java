package ru.levry.dbc.ratpack.handlers;

import ratpack.handling.Context;
import ratpack.handling.Handler;
import ru.levry.dbc.TableScriptFactory;
import ru.levry.dbc.ratpack.DataOperations;
import ru.levry.dbc.support.DataCallback;

/**
 * @author levry
 */
public class TableScriptHandler implements Handler {

    private final TableScriptFactory scriptFactory;

    public TableScriptHandler(TableScriptFactory scriptFactory) {
        this.scriptFactory = scriptFactory;
    }

    @Override
    public void handle(Context ctx) throws Exception {
        String schema = ctx.getPathTokens().get("schema");
        String table = ctx.getPathTokens().get("table");

        DataCallback<?> script = scriptFactory.script(schema, table);

        DataOperations dataOperations = ctx.get(DataOperations.class);
        dataOperations.execute(script).then(ctx::render);
    }
}
