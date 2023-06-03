package app;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * This class handles user registration, login, and data persistence.
 */
class LogIn {
    private HashMap<String, User> users = new HashMap<>();
    private User currentUser = null;
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9@]+$");// user can use also @
    private final File userDataFile = new File("Data" + File.separator + "UserData.txt");
    private final File passwordDataFile = new File("Data" + File.separator + "PasswordData.bin");

    /**
     * Register a new user with the provided username, password and user type.
     *
     * @param username the desired username
     * @param password the desired password
     * @param userType the desired user type
     */
    public void registerUser(String username, String password, String userType) {
        if (!USERNAME_PATTERN.matcher(username).matches()) {
            System.out.println("Invalid username. Only alphanumeric characters and '@' are allowed.");
            return;
        }
        User user = new User(username, password, userType);
        users.put(username, user);
        writeToFile(user);
    }
    /**
     * Attempt to login with the provided username and password.
     *
     * @param username the username
     * @param password the password
     * @return true if the login is successful, false otherwise
     */
    public boolean loginUser(String username, String password) {
        if (users.containsKey(username) && users.get(username).getPassword().equals(password)) {
            currentUser = users.get(username);
            return true;
        }
        return false;
    }
    /**
     * Attempt to login as an admin with the provided password.
     *
     * @param password the password
     * @return true if the login is successful, false otherwise
     */
    public boolean loginAsAdmin(String password) {
        if (password.equals("123")) {
            currentUser = new User("admin", password, "admin");
            return true;
        }
        return false;
    }
    /**
     * Get the currently logged in user.
     *
     * @return the current user, or null if no user is logged in
     */
    public User getCurrentUser() {
        return this.currentUser;
    }
    /**
     * Writes the user's data to files.
     * The username and user type are written to a text file and the password is written to a binary file.
     *
     * @param user the user whose data should be written to files
     */
    private void writeToFile(User user) {
        try (DataOutputStream dosPasswordData = new DataOutputStream(new BufferedOutputStream(new 	FileOutputStream(passwordDataFile, true)))) {

            FileUtils.writeStringToFile(userDataFile, user.getUsername() + "," + user.getUserType() + "\n", 	StandardCharsets.UTF_8, true);
            dosPasswordData.writeUTF(user.getPassword() + "\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Loads user data from files and populates the users hashmap.
     * The username and user type are read from a text file and the password is read from a binary file.
     */
    public void loadUsers() {
        try (LineIterator it = FileUtils.lineIterator(userDataFile, "UTF-8");
             DataInputStream disPasswordData = new DataInputStream(new BufferedInputStream(new 			FileInputStream(passwordDataFile)))) {

            while (it.hasNext() && disPasswordData.available() > 0) {
                String[] parts = it.nextLine().split(",");
                if (parts.length >= 2) {
                    String username = parts[0];
                    String userType = parts[1];

                    String password = disPasswordData.readUTF().trim();
                    users.put(username, new User(username, password, userType));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Clears all user data from the hashmap and the files.
     */
    public void clearUsers() {
        users.clear();
        try (DataOutputStream dosPasswordData = new DataOutputStream(new BufferedOutputStream(new 			FileOutputStream(passwordDataFile)))) {

            FileUtils.writeStringToFile(userDataFile, "", StandardCharsets.UTF_8);
            dosPasswordData.writeUTF("");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
