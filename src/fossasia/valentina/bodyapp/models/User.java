package fossasia.valentina.bodyapp.models;

import java.io.Serializable;

public class User implements Serializable {
	String email;
	String name;
	String pic;
	public User(String email, String name) {
		super();
		this.email = email;
		this.name = name;
	}
	
	

}
