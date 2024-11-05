package mn.astvision.starter.api.request.system;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mn.astvision.starter.model.systemconfig.enums.SystemApiModuleType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModuleToggleRequest {

    @NotNull
    private SystemApiModuleType moduleType;
}
