package ru.levry.dbc.boot.web;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.levry.dbc.CreateScript;
import ru.levry.dbc.SelectScript;
import ru.levry.dbc.UpdateScript;
import ru.levry.dbc.support.DataCallback;

/**
 * @author levry
 */
@RestController
@RequestMapping("/{schema}/{table}")
public class DbcController {

    private final JdbcOperations jdbcOperations;

    public DbcController(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @RequestMapping("/create")
    public String create(@PathVariable(required = false) String schema,
                         @PathVariable String table) {
        CreateScript script = new CreateScript(schema, table);
        return generateScript(script);
    }

    @RequestMapping("/select")
    public String select(@PathVariable(required = false) String schema,
                         @PathVariable String table) {
        SelectScript script = new SelectScript(schema, table);
        return generateScript(script);
    }

    @RequestMapping("/update")
    public String update(@PathVariable(required = false) String schema,
                         @PathVariable String table) {
        UpdateScript script = new UpdateScript(schema, table);
        return generateScript(script);
    }

    private <T> T generateScript(DataCallback<T> script) {
        return jdbcOperations.execute(script::execute);
    }
}
