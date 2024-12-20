package mn.astvision.starter.service.reference;

import lombok.RequiredArgsConstructor;
import mn.astvision.starter.dao.reference.ReferenceDataDao;
import mn.astvision.starter.model.reference.ReferenceData;
import mn.astvision.starter.model.reference.ReferenceType;
import mn.astvision.starter.repository.reference.ReferenceTypeRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
@Component
public class ReferenceDataService {

    private final ReferenceDataDao referenceDataDAO;
    private final ReferenceTypeRepository referenceTypeRepository;
//    private final UserService userService;

    public Iterable<ReferenceData> list(
            String typeCode,
            String name,
            String typeMean,
            String typeShortMean,
            String description,
            Pageable pageable
    ) {
        Iterable<ReferenceData> listData = referenceDataDAO.list(typeCode,name,typeMean,typeShortMean, description, pageable);

        for (ReferenceData referenceData : listData) {
            fillRelatedData(referenceData);
        }

        return listData;
    }

    private void fillRelatedData(ReferenceData referenceData) {
//        if (referenceData.getCreatedBy() != null) {
//            referenceData.setCreatedUserFullName(userService.getFullNameById(referenceData.getCreatedBy()));
//        }

        if (!ObjectUtils.isEmpty(referenceData.getTypeCode())) {
            ReferenceType type = referenceTypeRepository.findByCodeAndDeletedFalse(referenceData.getTypeCode());
            if (type != null) {
                referenceData.setTypeName(type.getName());
            }
        }
    }
}
