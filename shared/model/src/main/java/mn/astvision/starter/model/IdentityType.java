package mn.astvision.starter.model;

import lombok.Getter;

/**
 * @author Ariuka
 * Хүсэлт гаргагчийн төрөл
 */
@Getter
public enum IdentityType {

    CITIZEN("МУ-ын иргэн"), // МУ-ын иргэн
    LEGAL_ENTITY("Аж ахуйн нэгж, байгууллага");  // аж ахуйн нэгж, байгууллага

    private final String name;

    IdentityType(String name) {
        this.name = name;
    }
}
