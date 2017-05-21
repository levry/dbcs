package ru.levry.dbc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author levry
 */
class UpdateScriptTests extends ScriptTests {

    @Test
    void generateUpdateScript() {
        UpdateScript script = new UpdateScript("", "USERS");

        String sql = executeScript(script);

        assertEquals("UPDATE USERS SET USERNAME = ?, FULLNAME = ? WHERE ID = ?", sql);
    }

}