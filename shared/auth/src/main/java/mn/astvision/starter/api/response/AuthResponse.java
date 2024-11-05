package mn.astvision.starter.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mn.astvision.starter.model.auth.BusinessRole;

import java.time.LocalDateTime;

/**
 * @author digz6666
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    /*
    nextjs хэрэглэгчийн дата
     */
    private String id; // хэрэглэгчийн ID
    private String name; // харуулах нэр
    private String email; // мэйл хаяг
    private String image; // профайл зурагны URL
    private String avatarUrl;
    private String mobile;
//    private List<Tenant> tenantList;

    /*
    нэмэлт дата
     */
    private String token;
    private String tokenLong; // урт токен (365 хоног)
    private LocalDateTime expires;
    private BusinessRole businessRole;
}
