package mn.astvision.starter.api.request;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * @author Tergel
 */
@Data
public class AppVersionUpdateRequest {
    @NotBlank
    private String version;
}
