package mn.astvision.starter.service.syslocale;

import lombok.RequiredArgsConstructor;
import mn.astvision.starter.dao.syslocale.SysLocaleDao;
import mn.astvision.starter.model.syslocale.SysLocale;
import mn.astvision.starter.repository.syslanguage.SysLanguageRepository;
import mn.astvision.starter.repository.syslocale.NameSpaceRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
@Component
public class SysLocaleService {

    private final SysLocaleDao sysLocaleDao;
    private final SysLanguageRepository sysLanguageRepository;
    private final NameSpaceRepository nameSpaceRepository;

    public Iterable<SysLocale> list(String nsId, String lng, String field, String translation, Boolean active, Pageable pageable) {
        Iterable<SysLocale> listData = sysLocaleDao.list(nsId, lng, field, translation, active, pageable);

        for (SysLocale locale : listData) {
            fillRelatedData(locale);
        }

        return listData;
    }

    private void fillRelatedData(SysLocale sysLocale) {
        if (!ObjectUtils.isEmpty(sysLocale.getNsId()))
            nameSpaceRepository.findById(sysLocale.getNsId()).ifPresent(nameSpace -> sysLocale.setNsName(nameSpace.getName()));

        if (!ObjectUtils.isEmpty(sysLocale.getNsId()))
            sysLanguageRepository.findByCode(sysLocale.getLng()).ifPresent(sysLanguage -> sysLocale.setLngName(sysLanguage.getName()));
    }
}
