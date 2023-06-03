package app;


public class User {
	  private String username;
	    private String password;
	    private String userType;
	    /**
	     * Constructs a User object with the given username, password, and user type.
	     * @param username the username of the user
	     * @param password the password of the user
	     * @param userType the type of the user (e.g., "user" or "admin")
	     */
	    public User(String username, String password, String userType) {
	        this.username = username;
	        this.password = password;
	        this.userType = userType;
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

		public String getUserType() {
			return userType;
		}

		public void setUserType(String userType) {
			this.userType = userType;
		}

   
  
}
