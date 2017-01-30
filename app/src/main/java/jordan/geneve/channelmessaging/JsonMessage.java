package jordan.geneve.channelmessaging;

/**
 * Created by genevejo on 30/01/2017.
 */
public class JsonMessage {
    private String userID;
    private String message;
    private String date;
    private String imageUrl;
    private String username;
    private String latitude;
    private String longitude;

    public String getUserID() {
        return userID;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
