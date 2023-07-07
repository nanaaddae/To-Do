package task;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import user.User;

public interface TaskRepository extends JpaRepository<Task, Long> 
{
    
    List<Task> findByUser (User user);
    
}
