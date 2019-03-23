package com.example.health_community.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:PYD
 * email:pyd2657@qq.com
 * time:2019/03/18
 * desc:
 */
public class JSonUtil {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    private JSonUtil() {

    }

    public static ObjectMapper getInstance() {

        return objectMapper;
    }

    /**
     * javaBean,list,array convert to json string
     */
    public static String obj2json(Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    /**
     * json string convert to javaBean
     */
    public static <T> T json2pojo(String jsonStr, Class<T> clazz)
            throws Exception {
        return objectMapper.readValue(jsonStr, clazz);
    }

    /**
     * json string convert to map
     */
    public static <T> Map<String, Object> json2map(String jsonStr)
            throws Exception {
        return objectMapper.readValue(jsonStr, Map.class);
    }

    /**
     * json string convert to map with javaBean
     */
    public static <T> Map<String, T> json2map(String jsonStr, Class<T> clazz)
            throws Exception {
        Map<String, Map<String, Object>> map = objectMapper.readValue(jsonStr,
                new TypeReference<Map<String, T>>() {
                });
        Map<String, T> result = new HashMap<String, T>();
        for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
            result.put(entry.getKey(), map2pojo(entry.getValue(), clazz));
        }
        return result;
    }

    /**
     * json array string convert to list with javaBean
     */
    public static <T> List<T> json2list(String jsonArrayStr, Class<T> clazz)
            throws Exception {
        List<Map<String, Object>> list = objectMapper.readValue(jsonArrayStr,
                new TypeReference<List<T>>() {
                });
        List<T> result = new ArrayList<T>();
        for (Map<String, Object> map : list) {
            result.add(map2pojo(map, clazz));
        }
        return result;
    }

    /**
     * map convert to json
     */
    public static <T> T map2pojo(Map map, Class<T> clazz) {
        return objectMapper.convertValue(map, clazz);
    }

    public static String mapToJsonJackSon(Map map) throws JsonProcessingException {
        map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
            String json=mapper.writeValueAsString(map);
        return json;
    }

    public static String mapToJsonGson(Map map) {
        return new Gson().toJson(map);
    }

    public static String listToJsonGson(List list) {
        return new Gson().toJson(list);
    }

}