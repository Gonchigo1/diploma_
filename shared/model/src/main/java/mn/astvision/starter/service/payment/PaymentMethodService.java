package mn.astvision.starter.service.payment;

import lombok.RequiredArgsConstructor;
import mn.astvision.starter.dao.payment.PaymentMethodDao;
import mn.astvision.starter.model.payment.PaymentMethod;
import mn.astvision.starter.model.payment.enums.PaymentType;
import mn.astvision.starter.util.LocalizationUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {

    private final LocalizationUtil localizationUtil;
    private final PaymentMethodDao paymentMethodDao;

    public List<PaymentMethod> list(PaymentType paymentType, Boolean requireLogin, Boolean visible) {
        List<PaymentMethod> paymentMethods = paymentMethodDao.list(
                paymentType,
                requireLogin,
                visible);
        for (PaymentMethod paymentMethod : paymentMethods) {
            fillRelatedData(paymentMethod);
        }
        return paymentMethods;
    }

    private void fillRelatedData(PaymentMethod paymentMethod) {
        paymentMethod.setName(
                localizationUtil.buildMessage("PaymentMethodType." + paymentMethod.getType()));
    }
}
