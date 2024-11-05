package mn.astvision.starter.model.xyp.legalentity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import mn.astvision.starter.model.xyp.address.AddressType;
import org.springframework.data.annotation.Transient;
import org.springframework.util.ObjectUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class LegalEntityAddress {

    private String companyRegnum;

    private String addressStatus;
    private AddressType addressType;
    private AddressType stateCity;
    private AddressType soumDistrict;
    private AddressType bagKhoroo;
    private AddressType region;
    private AddressType street;
    private AddressType town;
    private AddressType apartment;
    private String door;

    private String phoneNumber;
    private String mobileNumber;
    private String postBox;
    private String fax;
    private String email;

    private String createdDate;
    private String createdUserName;

    @Transient
    public String getFullAddress() {
        StringBuilder sb = new StringBuilder();

        if (stateCity != null && !ObjectUtils.isEmpty(stateCity.getName())) {
            sb.append(stateCity.getName());
        }
        if (soumDistrict != null && !ObjectUtils.isEmpty(soumDistrict.getName())) {
            sb.append(", ").append(soumDistrict.getName());
        }
        if (bagKhoroo != null && !ObjectUtils.isEmpty(bagKhoroo.getName())) {
            sb.append(", ").append(bagKhoroo.getName());
        }
        if (region != null && !ObjectUtils.isEmpty(region.getName())) {
            sb.append(", ").append(region.getName());
        }
        if (street != null && !ObjectUtils.isEmpty(street.getName())) {
            sb.append(", ").append(street.getName());
        }
        if (town != null && !ObjectUtils.isEmpty(town.getName())) {
            sb.append(", ").append(town.getName());
        }
        if (apartment != null && !ObjectUtils.isEmpty(apartment.getName())) {
            sb.append(", ").append(apartment.getName());
        }
        if (!ObjectUtils.isEmpty(door)) {
            sb.append(", ").append(door);
        }

        return sb.toString();
    }
}
