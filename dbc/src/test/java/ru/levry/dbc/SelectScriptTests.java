package ru.levry.dbc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author levry
 */
class SelectScriptTests extends ScriptTests {

    @Test
    void generateSelectScript() {
        SelectScript script = new SelectScript("", "USERS");

        String sql = executeScript(script);

        assertEquals("SELECT ID, USERNAME, FULLNAME FROM USERS WHERE ID = ?", sql);
    }
}