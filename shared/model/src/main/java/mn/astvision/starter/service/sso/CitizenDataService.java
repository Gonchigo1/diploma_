package mn.astvision.starter.service.sso;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.dto.sso.CitizenIDCardInfo;
import mn.astvision.starter.model.xyp.citizen.Citizen;
import mn.astvision.starter.model.xyp.citizen.CitizenImage;
import mn.astvision.starter.util.DateUtil;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class CitizenDataService {

    private final MongoTemplate mongoTemplate;

    public void save(CitizenIDCardInfo citizenIDCardInfo) {
        Citizen citizen = mongoTemplate.findOne(
                new Query().addCriteria(
                        Criteria.where(Citizen.Fields.regnum).is(citizenIDCardInfo.getRegnum().toLowerCase())),
                Citizen.class);


        if (citizen == null) {
            citizen = new Citizen();
            citizen.setCivilId(citizenIDCardInfo.getCivilId());
            citizen.setRegnum(citizenIDCardInfo.getRegnum().toLowerCase());
            citizen.setCreatedDate(LocalDateTime.now());
        }

        citizen.setPersonId(citizenIDCardInfo.getPersonId());
        citizen.setSurname(citizenIDCardInfo.getSurname());
        citizen.setLastname(citizenIDCardInfo.getLastname());
        citizen.setFirstname(citizenIDCardInfo.getFirstname());
        citizen.setGender(citizenIDCardInfo.getGender());
        citizen.setNationality(citizenIDCardInfo.getNationality());
        citizen.setBirthPlace(citizenIDCardInfo.getBirthPlace());
        if (citizenIDCardInfo.getBirthDate() != null) {
            citizen.setBirthDate(DateUtil.toLocalDate(citizenIDCardInfo.getBirthDate()).toLocalDate());
        }
        citizen.setBirthDateAsText(citizenIDCardInfo.getBirthDateAsText());

//        citizen.setImage(citizenIDCardInfo.getImage()); // TODO store in different place
        citizen.setPassportIssueDate(citizenIDCardInfo.getPassportIssueDate());
        citizen.setPassportExpireDate(citizenIDCardInfo.getPassportExpireDate());
        citizen.setPassportAddress(citizenIDCardInfo.getPassportAddress());
        try {
            citizen.setAimagCityCode(Integer.parseInt(citizenIDCardInfo.getAimagCityCode()));
        } catch (Exception ignored) {
        }
        citizen.setAimagCityName(citizenIDCardInfo.getAimagCityName());
        try {
            citizen.setSoumDistrictCode(Integer.parseInt(citizenIDCardInfo.getSoumDistrictCode()));
        } catch (Exception ignored) {
        }
        citizen.setSoumDistrictName(citizenIDCardInfo.getSoumDistrictName());
        try {
            citizen.setBagKhorooCode(Integer.parseInt(citizenIDCardInfo.getBagKhorooCode()));
        } catch (Exception ignored) {
        }
        citizen.setBagKhorooName(citizenIDCardInfo.getBagKhorooName());
        citizen.setAddressRegionName(citizenIDCardInfo.getAddressRegionName());
        citizen.setAddressStreetName(citizenIDCardInfo.getAddressStreetName());
        citizen.setAddressTownName(citizenIDCardInfo.getAddressTownName());
        citizen.setAddressApartmentName(citizenIDCardInfo.getAddressApartmentName());
        citizen.setAddressDetail(citizenIDCardInfo.getAddressDetail());

        citizen.setModifiedDate(LocalDateTime.now());
        mongoTemplate.save(citizen);

        CitizenImage citizenImage = mongoTemplate.findOne(
                new Query().addCriteria(
                        Criteria.where(Citizen.Fields.regnum).is(citizenIDCardInfo.getRegnum().toLowerCase())),
                CitizenImage.class);

        if (citizenImage == null) {
            citizenImage = new CitizenImage();
            citizenImage.setRegnum(citizenIDCardInfo.getRegnum().toLowerCase());
            citizenImage.setCreatedDate(LocalDateTime.now());
        }
        citizenImage.setImage(citizenIDCardInfo.getImage());

        citizenImage.setModifiedDate(LocalDateTime.now());
        mongoTemplate.save(citizenImage);
    }
}
