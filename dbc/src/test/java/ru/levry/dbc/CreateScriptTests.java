package ru.levry.dbc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author levry
 */
class CreateScriptTests extends ScriptTests {

    @Test
    void generateCreateScript() {
        CreateScript createScript = new CreateScript("", "USERS");

        String sql = executeScript(createScript);

        assertEquals("CREATE TABLE USERS (ID INTEGER NOT NULL, USERNAME VARCHAR(255) NOT NULL, FULLNAME VARCHAR(500), CONSTRAINT PRIMARY KEY (ID))", sql);
    }
}