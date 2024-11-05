package mn.astvision.starter.api.request;

import lombok.Data;

/**
 * @author digz6666
 */
@Data
public class CreateUserRequest {
    private String id;
    private String email;
    private String username;
    private String registryNumber;
    private String mobile;
    private String password;
    private String lastName;
    private String firstName;
    private String address;
    private String orgName;

    private String role;
    private boolean active;
}
