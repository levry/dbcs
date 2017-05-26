package ru.levry.dbc.ratpack.handlers;

import ratpack.handling.Context;
import ratpack.handling.Handler;
import ru.levry.dbc.ratpack.DataOperations;
import ru.levry.dbc.support.DataCallback;

import java.util.function.Function;

/**
 * @author levry
 */
public class DataCallbackHandler implements Handler {

    private final Function<Context, DataCallback<?>> supplier;

    public DataCallbackHandler(Function<Context, DataCallback<?>> supplier) {
        this.supplier = supplier;
    }

    @Override
    public void handle(Context ctx) throws Exception {
        DataCallback<?> callback = supplier.apply(ctx);

        DataOperations dataOperations = ctx.get(DataOperations.class);
        dataOperations.execute(callback).then(ctx::render);
    }
}
