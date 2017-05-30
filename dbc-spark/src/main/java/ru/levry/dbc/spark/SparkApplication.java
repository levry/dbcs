package ru.levry.dbc.spark;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.levry.dbc.*;
import ru.levry.dbc.support.DataCallback;
import ru.levry.dbc.support.DataSupport;
import spark.Route;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static spark.Spark.get;
import static spark.Spark.port;

/**
 * @author levry
 */
public class SparkApplication {

    private final DataSupport dataSupport;

    private SparkApplication(DataSupport dataSupport) {
        this.dataSupport = dataSupport;
    }

    public static void main(String[] args) throws Exception {
        DataConfig config = readConfig(args);
        DataSupport dataSupport = createDataSupport(config);
        new SparkApplication(dataSupport).run();
    }

    private static DataSupport createDataSupport(DataConfig config) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(config.getDriverClass());
        hikariConfig.setJdbcUrl(config.getUrl());
        hikariConfig.setUsername(config.getUsername());
        hikariConfig.setPassword(config.getPassword());

        DataSource dataSource = new HikariDataSource(hikariConfig);
        return new DataSupport(dataSource);
    }

    private void run() {
        port(8080);
        get(":schema/:table/create", routeGenerateScript(CreateScript::new));
        get(":schema/:table/select", routeGenerateScript(SelectScript::new));
        get(":schema/:table/update", routeGenerateScript(UpdateScript::new));
    }

    private Route routeGenerateScript(TableScriptFactory scriptFactory) {
        return (request, response) -> {
            String schema = request.params("schema");
            String table = request.params("table");
            DataCallback<?> script = scriptFactory.script(schema, table);
            return dataSupport.execute(script);
        };
    }

    private static DataConfig readConfig(String[] args) throws Exception {
        if(args.length == 0) {
            throw new RuntimeException("Not specify a path to config");
        }

        Path configPath = Paths.get(args[0]).toAbsolutePath();
        return parseConfig(configPath.toFile());
    }

    private static DataConfig parseConfig(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        JsonNode rootNode = mapper.readTree(file);
        JsonNode configNode = rootNode.get("database");
        return mapper.readValue(new TreeTraversingParser(configNode, mapper), DataConfig.class);
    }
}
