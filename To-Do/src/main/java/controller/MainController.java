package controller;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import task.Task;
import task.TaskService;
import user.User;
import user.UserService;

@Controller 
public class MainController 
{
	
	@Autowired
	 private UserService userService;

	 @Autowired
	 private TaskService taskService;

	    public MainController(UserService userService) 
	    {
	        this.userService = userService;
	    }
	
	 @GetMapping("/register")
	    public String showRegisterPage() {
	        return "register";
	    }
	 
	 @PostMapping("/users/register")
	    public String registerUser(String username, String password, String fname, String lname, Model model) 
		{
	        try {
	            userService.registerUser(username, password, fname, lname);
	            model.addAttribute("successMessage", "Registration successful. Please log in.");
	            return "index";
	        } catch (Exception e) {
	            model.addAttribute("errorMessage", e.getMessage());
	            return "register";
	        }
	    }


@PostMapping("/login")
public ModelAndView loginUser(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
    ModelAndView modelAndView = new ModelAndView();
    User user = userService.getUserByUsername(username);
    if (user == null || !user.getPassword().equals(password)) {
        modelAndView.addObject("error", "Invalid username or password");
        modelAndView.setViewName("index");
    } else {
        // login successful, set session attribute and redirect to home page
        modelAndView.addObject("user", user);
		 
        List<Task> tasks = taskService.getTasksForUser(user); // Retrieve tasks associated with the logged-in user
        
        modelAndView.addObject("user", user);
        modelAndView.addObject("tasks", tasks);
        modelAndView.setViewName("home");
        HttpSession session = request.getSession();
        session.setAttribute("loggedInUser", user);
    }
    return modelAndView;
}
@PostMapping("/tasks")
public String createTask(@RequestParam("title") String title,
                         @RequestParam("description") String description,
                         @RequestParam("dueDate") String dueDate,HttpServletRequest request , Model model) {
    // Parse the dueDate string to LocalDate, if needed
    
	User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
						
	if (loggedInUser == null) {
        // User is not logged in, handle the scenario appropriately (e.g., redirect to login page)
        return "redirect:/home";
    }						

	Task newTask = new Task();
    newTask.setTitle(title);
    newTask.setDescription(description);
    newTask.setDue_date(LocalDate.parse(dueDate));
    newTask.setUser(loggedInUser); 

    // Call the createTask method in the TaskService
    taskService.createTask(newTask);

        List<Task> tasks = taskService.getTasksForUser(loggedInUser);

            model.addAttribute("tasks", tasks);


    
    return "home";
}

@GetMapping("/home")
    public String displayHomePage(Model model) {
        // Retrieve the logged-in user's tasks from the taskService
        //List<Task> tasks = taskService.getTasksForLoggedInUser();
        
        // Add the tasks to the model for rendering in the view
        //model.addAttribute("tasks", tasks);
        
        return "home";
    }

    @PostMapping("/tasks/{taskId}/delete")
public String deleteTask(@PathVariable Long taskId , HttpServletRequest request, Model model) {
    try {
        taskService.deleteTask(taskId);

        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");

        if (loggedInUser != null) {
            // Retrieve the updated task list for the logged-in user
            List<Task> tasks = taskService.getTasksForUser(loggedInUser);

            // Add the updated task list to the model
            model.addAttribute("tasks", tasks);
        }

        return "home";
    } catch (NoSuchElementException e) {
        // Handle the exception, e.g., display an error message
        return "redirect:/home";
    }
}


@PostMapping("/logout")
public String logout(HttpServletRequest request) {
    // Clear the session or perform any other logout actions
    request.getSession().invalidate();
    
    // Redirect the user to the login page or any other appropriate page
    return "index";
}

}
