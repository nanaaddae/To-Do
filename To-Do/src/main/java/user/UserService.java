package user;


import org.springframework.stereotype.Service;

@Service
public class UserService 
{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    
    
    public void registerUser(String username, String password,String fname,String lname) {
        // Check if the user already exists
        if (userRepository.findByUsername(username) != null) {
            throw new RuntimeException("Username already exists");
        }

        // Create a new user
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setFname(fname);
        user.setLname(lname);
        userRepository.save(user);
    }


  public boolean authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return true; // Authentication successful
        }
        return false; // Authentication failed
    }

    public User getUserByUsername(String username)
    {
        User user = userRepository.findByUsername(username);

        return user;
    }
}
