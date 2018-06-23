package thebestdevelopers.pl.findmybeer.loginController;


import org.json.JSONException;
import org.json.JSONObject;


public class LoginParser {

    private User getLoggedUserData(JSONObject loggedUserDataJsonObject) {
        User user = null;
        try {
            String username = loggedUserDataJsonObject.getString("username");
            int id = loggedUserDataJsonObject.getInt("id");
            String role = loggedUserDataJsonObject.getString("role");
            Role userRole = Role.CLIENT;
            switch (role) {
                case "client":
                    userRole = Role.CLIENT;
                    break;
                case "admin":
                    userRole = Role.ADMIN;
                    break;
                case "pub":
                    userRole = Role.PUB;
                    break;
            }

            user = new User(username, id, userRole);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User parse(String jsonData) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getLoggedUserData(jsonObject);
    }
}
