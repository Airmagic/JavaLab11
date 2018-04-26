package tasks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;

import java.io.IOException;

/**
 * Created by clara on 4/12/18.
 */
public class Config {
    
    public static void configObjectMapper() {
    
        Unirest.setObjectMapper(new ObjectMapper() {
            
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper =
                    new com.fasterxml.jackson.databind.ObjectMapper();
                    
            
            @Override
            public <T> T readValue(String s, Class<T> aClass) {
                try {
                    return jacksonObjectMapper.readValue(s, aClass);
                } catch (IOException e) {
                    System.err.println(e);
                    return readValue("File Error", aClass);
//                    throw new RuntimeException(e);  // todo improve error handling
                }
            }
    
            @Override
            public String writeValue(Object o) {
                try{
                    return jacksonObjectMapper.writeValueAsString(o);
                } catch(JsonProcessingException e) {
                    System.err.println(e);
                    return "Json problem: "+ e;
//                    throw new RuntimeException(e);  //todo improve error handling
                }
//                catch(Exception e){
//                    System.out.println("Error "+e+" happened");
//                    return e;
//                }
            }
        });
    }
}
