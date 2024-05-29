package com.example.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;

import com.example.enums.ErrorLevel;
import com.example.model.LogError;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonHelper {
    // No condition to check does the folder is exist or not, so make sure the folder is created before
    public boolean writeToJson(HashMap<String,String> list_data, String path_name) throws StreamWriteException, DatabindException, IOException {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode jsonNode = objectMapper.createObjectNode();
            
            for(String data: list_data.keySet()) {
                jsonNode.put(data, list_data.get(data));
            }
            
            objectMapper.writeValue(new File(path_name), jsonNode);
            return this.existJsonFile(path_name);
        }
        catch (StreamWriteException stream_exception) {
            new LogError(ErrorLevel.EMERGENCY, stream_exception.getOriginalMessage());
        }
        catch (DatabindException databind_exception) {
            new LogError(ErrorLevel.EMERGENCY, databind_exception.getOriginalMessage());
        }
        catch (IOException io_exception) {
            new LogError(ErrorLevel.EMERGENCY, io_exception.getMessage());
        }
        
        return false;
    }
    
    public HashMap<String, String> getJsonFile(String path_name) {
        HashMap<String, String> json_data = new HashMap<>();
        try {
            if(existJsonFile(path_name)) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(new File(path_name));
                Iterator<String> field = jsonNode.fieldNames();
                
                while(field.hasNext()) {
                    String fieldName = field.next();
                    json_data.put(fieldName, jsonNode.get(fieldName).asText());
                }
            }
        } catch (IOException e) {
            new LogError(ErrorLevel.ERROR, e.getMessage());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            new LogError(ErrorLevel.CRITICAL, e.getLocalizedMessage());
        }
        return json_data;
    }
    
    public void removeFileContent(String path_name) throws IOException {
        File file = new File(Paths.get(path_name).toString());
        file.setWritable(true);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
        bufferedOutputStream.write("{}".getBytes());
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }
    
    public boolean existJsonFile(String path_name) {
        try {
            return Files.exists(Paths.get(path_name));
        } catch (Exception e) {
            new LogError(ErrorLevel.ERROR, e.getMessage());
            return false;
        }
    }
}
