package ru.levry.dbc.dw.resources;

import ru.levry.dbc.CreateScript;
import ru.levry.dbc.SelectScript;
import ru.levry.dbc.UpdateScript;
import ru.levry.dbc.dw.jdbi.DataOperations;
import ru.levry.dbc.support.DataCallback;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author levry
 */
@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class DbcResource {

    private final DataOperations dataOperations;

    public DbcResource(DataOperations dataOperations) {
        this.dataOperations = dataOperations;
    }

    @GET
    @Path("/{schema}/{table}/create")
    public String create(@PathParam("schema") String schema,
                         @PathParam("table") String table) {
        CreateScript script = new CreateScript(schema, table);
        return generateScript(script);
    }

    @GET
    @Path("/{schema}/{table}/select")
    public String select(@PathParam("schema") String schema,
                         @PathParam("table") String table) {
        SelectScript script = new SelectScript(schema, table);
        return generateScript(script);
    }

    @GET
    @Path("/{schema}/{table}/update")
    public String update(@PathParam("schema") String schema,
                         @PathParam("table") String table) {
        UpdateScript script = new UpdateScript(schema, table);
        return generateScript(script);
    }

    private <T> T generateScript(DataCallback<T> script) {
        return dataOperations.execute(script);
    }

}
