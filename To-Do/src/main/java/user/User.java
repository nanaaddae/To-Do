package user;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import task.Task;

@Entity
@Table(name = "users")
public class User 
{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "username")
    private String username;
	
	@Column(name = "password")
    private String password;
	
	@Column(name = "fname")
	private String fname;
	
	@Column(name = "lname")
    private String lname;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Task> tasks;

	
    public User()
    {
    	
    }
    
    
    
    public User(Long id, String username, String password, String fname, String lname) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.fname = fname;
		this.lname = lname;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}



	public List<Task> getTasks() {
		return tasks;
	}



	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	
	
    
    

}
