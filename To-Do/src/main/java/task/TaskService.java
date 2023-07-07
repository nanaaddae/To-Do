package task;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import user.User;


@Service
public class TaskService 
{
          private final TaskRepository taskRepository;
       
    
 public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
       
    }
    public void createTask(Task newTask) {
    // Perform any necessary business logic or validation
    
    // Create a new task object
   // Task task = new Task();
    
    // Set the values based on user input
   // task.setTitle(title);
   // task.setDescription(description);
   // task.setDue_date(dueDate);
    
    // Save the task in the repository
    //taskRepository.save(task);

        taskRepository.save(newTask);



}

public List<Task> getTasksForUser(User user) {
    // Perform any necessary business logic or validation
    
    // Retrieve the tasks associated with the given user
    return taskRepository.findByUser(user);
}

public void deleteTask(Long taskId) {
    // Perform any necessary business logic or validation
    
    // Retrieve the task from the repository
    Optional<Task> optionalTask = taskRepository.findById(taskId);
    
    // Check if the task exists
    if (optionalTask.isPresent()) {
        Task task = optionalTask.get();
        
        // Delete the task from the repository
        taskRepository.delete(task);
    } else {
        throw new NoSuchElementException("Task not found.");
    }
}

}
