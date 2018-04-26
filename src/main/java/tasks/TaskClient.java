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
//    This is where the json file is located
    private static final String URL = "http://127.0.0.1:8080/";   // Replace with your own URL, if different

//    This is getting all tasks in the jcson file
    public static void getAllTasks(TaskGUI gui) {

//        this is checking if it can get the infor and responding back
        Unirest.get(URL + "tasks")
                .header("Content-Type", "application/json")
               .asObjectAsync(Task[].class, new Callback<Task[]>() {

//                   This is when there is a responce from the storage
                    @Override
                    public void completed(HttpResponse<Task[]> httpResponse) {
                        System.out.println("all tasks response " + Arrays.toString(httpResponse.getBody()));
                        gui.newTaskList(httpResponse.getBody());
                    }

//                    This is when no responce from the storage
                    @Override
                    public void failed(UnirestException e) {
                        System.out.println(e);
                        gui.taskError(e);
    
                    }
    
                    @Override
                    public void cancelled() {
                        System.out.println("cancelled");
                    }
                });
    
    }
    

//    This is for adding a new task to the storage
    public static void addTask(TaskGUI gui, Task task) {
    
        Unirest.post(URL + "add")
                .header("Content-Type", "application/json")
                .body(task)
                .asJsonAsync(new Callback<JsonNode>() {
                    @Override
                    public void completed(HttpResponse<JsonNode> httpResponse) {
                        System.out.println("add response " + httpResponse.getStatus()); // hopefully 201, should check
                        gui.tasksUpdated();
                    }

//                    This is the responces if it can't store the new task
                    @Override
                    public void failed(UnirestException e) {
                        System.out.println("Add task " + e);
                        gui.taskError(e);
    
                    }


                    @Override
                    public void cancelled() {
                        System.out.println("Cancelled");
                    }
                });
    }
    
//    This is for updating a task to completed
    public static void updateTask(TaskGUI gui, Task task) {
        System.out.println("Update - Implement me!");
        
        Unirest.patch(URL + "completed")
                .header("Content-Type", "application/json")
                .body(task)
                .asJsonAsync(new Callback<JsonNode>() {
                    @Override
                    public void completed(HttpResponse<JsonNode> httpResponse) {
                        System.out.println("completed response " + httpResponse.getStatus()); // hopefully 201, should check
                        gui.tasksUpdated();
                    }
    
                    @Override
                    public void failed(UnirestException e) {
                        System.out.println("completed " + e);
                        gui.taskError(e);
    
                    }
    
                    @Override
                    public void cancelled() {
                        System.out.println("Completed cancelled");
                    }
                });
    }
    
    
    public static void deleteTask(TaskGUI gui, Task task) {
        System.out.println("Delete - implement me!");
    
        Unirest.delete(URL + "delete")
                .header("Content-Type", "application/json")
                .body(task)
                .asJsonAsync(new Callback<JsonNode>() {
                    @Override
                    public void completed(HttpResponse<JsonNode> httpResponse) {
                        System.out.println("completed response " + httpResponse.getStatus()); // hopefully 201, should check
                        gui.tasksUpdated();
                    }
                
                    @Override
                    public void failed(UnirestException e) {
                        System.err.println("delete " + e);
                        gui.taskError(e);
                    }
                
                    @Override
                    public void cancelled() {
                        System.out.println("Delete cancelled");
                    }
                });
    }

    public static void urgentTask(TaskGUI gui, Task task) {
        System.out.println("Urgent - implement me!");

        Unirest.delete(URL + "Urgent")
                .header("Content-Type", "application/json")
                .body(task)
                .asJsonAsync(new Callback<JsonNode>() {
                    @Override
                    public void completed(HttpResponse<JsonNode> httpResponse) {
                        System.out.println("completed response " + httpResponse.getStatus()); // hopefully 201, should check
                        gui.tasksUpdated();
                    }

                    @Override
                    public void failed(UnirestException e) {
                        System.err.println("urgent " + e);
                        gui.taskError(e);
                    }

                    @Override
                    public void cancelled() {
                        System.out.println("Urgent change cancelled");
                    }
                });
    }
}


