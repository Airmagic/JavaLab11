package tasks;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.Arrays;


/**
 * Created by clara on 4/12/18.
 */
public class TaskClient {
    
    private static final String URL = "http://127.0.0.1:8080/";
    
    public static void getAllTasks(TaskGUI gui) {
    
        Unirest.get(URL + "tasks")
                .header("Content-Type", "application/json")
               .asObjectAsync(Task[].class, new Callback<Task[]>() {

                    @Override
                    public void completed(HttpResponse<Task[]> httpResponse) {
                        //System.out.println("Response= " + Arrays.toString(httpResponse.getBody()));
                        gui.tasksUpdated(httpResponse.getBody());
                    }
    
                    @Override
                    public void failed(UnirestException e) {
                        System.out.println(e);
                    }
    
                    @Override
                    public void cancelled() {
                        System.out.println("cancelled");
                    }
                });
    
    }
    
    
    public static void addTask(Task task) {
    
        Unirest.post(URL + "add")
                .header("Content-Type", "application/json")
                .body(task)
                .asJsonAsync(new Callback<JsonNode>() {
                    @Override
                    public void completed(HttpResponse<JsonNode> httpResponse) {
                        System.out.println("add response " + httpResponse.getStatus()); // hopefully 201, should check
                    }
    
                    @Override
                    public void failed(UnirestException e) {
                        System.out.println(e);
                    }
    
                    @Override
                    public void cancelled() {
                        System.out.println("Cancelled");
                    }
                });
    
    
    }
    
    public static void updateTask(Task task) {
        System.out.println("Update - Implement me!");
    }
    
    
    public static void deleteTask(Task task) {
        System.out.println("Delete - implement me!");
    }
}
