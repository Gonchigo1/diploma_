package mn.astvision.starter.api.request;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @author MethoD
 */
@Data
public class DeleteRequest {
    @NotBlank
    private String id;
}
