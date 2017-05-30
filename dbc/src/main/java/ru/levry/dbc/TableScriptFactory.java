package ru.levry.dbc;

import ru.levry.dbc.support.DataCallback;

/**
 * @author levry
 */
@FunctionalInterface
public interface TableScriptFactory {
    DataCallback<String> script(String schema, String table);
}
