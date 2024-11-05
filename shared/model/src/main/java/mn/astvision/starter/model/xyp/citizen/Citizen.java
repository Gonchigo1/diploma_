package mn.astvision.starter.model.xyp.citizen;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;
import mn.astvision.starter.model.BaseEntity;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Sharded;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Ariuka
 */
@Sharded(shardKey = {"regnum"})
@FieldNameConstants
@Data
@EqualsAndHashCode(callSuper = true)
public class Citizen extends BaseEntity {

    private String civilId;
    private String personId;
    private String regnum;

    private String surname;
    private String lastname;
    private String firstname;
    private String gender;
    private String nationality;

    private String birthPlace;
    private LocalDate birthDate;
    private String birthDateAsText;

    private String passportIssueDate;
    private String passportExpireDate;
    private String passportAddress;
//    private String image;

    // address
    private Integer aimagCityCode;
    private String aimagCityName;
    private Integer soumDistrictCode;
    private String soumDistrictName;
    private Integer bagKhorooCode;
    private String bagKhorooName;
    private String addressRegionName;
    private String addressStreetName;
    private String addressTownName;
    private String addressApartmentName;
    private String addressDetail;

    @Transient
    public boolean requireUpdate() {
        return getModifiedDate() == null || getModifiedDate().isBefore(LocalDateTime.now().minusDays(1));
    }

    @Transient
    public String getFullName() {
        boolean hasFirstName = StringUtils.hasText(firstname);
        boolean hasLastName = StringUtils.hasText(lastname);
        if (hasFirstName && hasLastName) {
            return lastname.charAt(0) + "." + firstname;
        }
        return hasFirstName ? firstname : "-";
    }
}
