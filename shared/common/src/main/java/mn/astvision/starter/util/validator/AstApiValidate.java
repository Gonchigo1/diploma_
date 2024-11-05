package mn.astvision.starter.util.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import mn.astvision.starter.model.systemconfig.enums.SystemApiModuleType;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AstApiValidate {

    SystemApiModuleType moduleName();
}
