package com.example.helpers;

import java.util.HashMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import com.example.enums.ErrorLevel;
import com.example.model.LogError;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonHelper {
    // No condition to check does the folder is exist or not, so make sure the folder is created before
    public void WriteToJson(HashMap<String,String> list_data, String path_name) throws StreamWriteException, DatabindException, IOException {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode jsonNode = objectMapper.createObjectNode();
            
            for(String data: list_data.keySet()) {
                jsonNode.put(data, list_data.get(data));
            }
            
            this.existJsonFile(path_name);
            objectMapper.writeValue(new File(path_name), jsonNode);
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
