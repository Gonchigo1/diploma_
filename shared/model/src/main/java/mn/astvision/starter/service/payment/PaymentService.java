package mn.astvision.starter.service.payment;

import lombok.RequiredArgsConstructor;
import mn.astvision.starter.dao.payment.PaymentDao;
import mn.astvision.starter.model.payment.Payment;
import mn.astvision.starter.model.payment.PaymentItem;
import mn.astvision.starter.model.payment.enums.PaymentStatus;
import mn.astvision.starter.model.payment.enums.PaymentType;
import mn.astvision.starter.repository.payment.PaymentAdditionalDataRepository;
import mn.astvision.starter.repository.payment.PaymentItemRepository;
import mn.astvision.starter.repository.payment.PaymentRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author digz6666
 */
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentAdditionalDataRepository paymentAdditionalDataRepository;
    private final PaymentDao paymentDao;
    private final PaymentItemRepository paymentItemRepository;
    private final PaymentRepository paymentRepository;

    public Iterable<Payment> list(
            LocalDate startDate,
            LocalDate endDate,
            PaymentType type,
            String payerId,
            PaymentStatus status,
            String number,
            String contactPhone,
            Pageable pageable
    ) {
        Iterable<Payment> payments = paymentDao.list(
                startDate,
                endDate,
                type,
                payerId,
                status,
                number,
                contactPhone,
                pageable);
        for (Payment payment : payments) {
            fillRelatedData(payment);
        }
        return payments;
    }

    public Payment getOneByNumber(String number, boolean withAdditionalData) {
        Payment payment = paymentRepository.findOneByNumber(number);

        if (payment != null) {
            fillRelatedData(payment);
            if (withAdditionalData)
                fillAdditionalData(payment);
        }

        return payment;
    }

    public Payment getByServiceRequestId(String serviceRequestId) {
        Payment payment = paymentRepository.findTopByServiceRequestIdAndStatusAndDeletedFalse(serviceRequestId, PaymentStatus.PAID);

        if (payment != null) {
            fillRelatedData(payment);
        }

        return payment;
    }

    public Payment getByNumber(String number) {
        Payment payment = paymentRepository.findOneByNumber(number);

        if (payment != null) {
            fillRelatedData(payment);
        }

        return payment;
    }

    public Payment get(String id, boolean withAdditionalData) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            fillRelatedData(payment);
            if (withAdditionalData)
                fillAdditionalData(payment);

            return payment;
        } else {
            return null;
        }
    }

    public BigDecimal getTotalAmountWithPassportNumber(String id, String passportNumber) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            BigDecimal totalAmount = BigDecimal.ZERO;
            List<PaymentItem> paymentItems =
                    paymentItemRepository.findByPaymentNumberAndPassportNumber(payment.getNumber(), passportNumber);
            for (PaymentItem paymentItem : paymentItems) {
                totalAmount = totalAmount.add(paymentItem.getTotalAmount());
            }
            return totalAmount;
        } else {
            return BigDecimal.ZERO;
        }
    }

    public BigDecimal getServiceFeeWithNameAndPassportNumber(String id, String passportNumber, String name) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            BigDecimal totalAmount = BigDecimal.ZERO;
            List<PaymentItem> paymentItems =
                    paymentItemRepository.findByPaymentNumberAndPassportNumber(payment.getNumber(), passportNumber);
            for (PaymentItem paymentItem : paymentItems) {
                if (Objects.equals(paymentItem.getName(), name)) {
                    totalAmount = totalAmount.add(paymentItem.getTotalAmount());
                }
            }
            return totalAmount;
        } else {
            return BigDecimal.ZERO;
        }
    }

    private void fillAdditionalData(Payment payment) {
        payment.setAdditionalData(paymentAdditionalDataRepository.findOneByNumber(payment.getNumber()));
    }

    private void fillRelatedData(Payment payment) {
        if (payment.getNumber() != null)
            payment.setItems(paymentItemRepository.findByPaymentNumber(payment.getNumber()));

        if (payment.getStatus() != null)
            payment.setStatusName(payment.getStatus().getName());

//        if (payment.getPaymentType() != null) {
//            payment.setPaymentTypeName(payment.getPaymentType().getName());
//        }
        if (payment.getMethodType() != null) {
//            payment.setMethodTypeName(payment.getMethodType().name());
            switch (payment.getMethodType()) {
                case GOLOMT_BANK_SOCIAL_PAY -> payment.setMethodTypeName("Social pay");
                case GOLOMT_BANK_CARD -> payment.setMethodTypeName("Карт");
                case QPAY -> payment.setMethodTypeName("QPay");
                case WECHAT_PAY -> payment.setMethodTypeName("Wechat Pay");
            }
        }
    }
}
