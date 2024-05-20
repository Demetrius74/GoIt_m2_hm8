package org.august.utils.config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Config {
    public static final String DEFAULT_PREFS_FILE = "config.json";
    public static final String INIT_DB_FILE = "dbInit";
    public static final String INIT_POPULATE_DB_FILE = "dbPopulate";
    public static final String DB_URL_CONNECTION = "dbUrl";
    public static final String FIND_LONGEST_PROJECT_QUERY_FILE = "dbFindLongestQuery";
    public static final String FIND_MAX_SALARY_WORKER_QUERY_FILE = "dbFindMaxSalaryWorker";
    public static final String FIND_MAX_PROJECTS_CLIENT_QUERY_FILE = "dbFindMaxProjectsClient";
    public static final String FIND_YOUNGEST_ELDEST_WORKER_QUERY_FILE = "dbFindYoungestEldestWorker";
    public static final String GET_PROJECT_PRICE_QUERY_FILE = "dbGetProjectPrices";
    private Map<String, Object> prefs;

    public Config(String fileName) {
        prefs = new HashMap<>();
        try {
            String jsonData = String.join("\n", Files.readAllLines(Paths.get(fileName)));
            Type type = TypeToken.getParameterized(Map.class, String.class, Object.class).getType();
            prefs = new Gson().fromJson(jsonData, type);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public Config() {
        this(DEFAULT_PREFS_FILE);
    }

    public Object getPrefs(String key) {
        return prefs.get(key);
    }

    public String getValue(String key) {
        return getPrefs(key).toString();
    }
}
