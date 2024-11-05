package mn.astvision.starter.dto.auth;

import lombok.Builder;
import lombok.Data;
import mn.astvision.starter.model.auth.enums.ApplicationRole;

import java.util.List;

/**
 * @author digz6666
 */
@Data
@Builder
public class BusinessRoleDto {
    private String role;
    private List<ApplicationRole> roles;
}
