package mn.astvision.starter.service.servicefee;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.dao.bank.BankAccountDao;
import mn.astvision.starter.model.bank.BankAccount;
import mn.astvision.starter.model.servicefee.ServiceFee;
import mn.astvision.starter.repository.servicefee.ServiceFeeRepository;
import mn.astvision.starter.service.bank.BankAccountService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author digz6666
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceFeeService {

    private final BankAccountDao bankAccountDao;
    private final BankAccountService bankAccountService;
    private final ServiceFeeRepository serviceFeeRepository;

    public List<ServiceFee> listByServiceId(String serviceId) {
        List<ServiceFee> serviceFees = serviceFeeRepository.findByServiceIdAndDeletedFalseOrderByCreatedDateDesc(serviceId);
        fillBankAccounts(serviceFees);
        return serviceFees;
    }

    private void fillBankAccounts(List<ServiceFee> serviceFees) {
        List<String> bankAccountIds = new ArrayList<>();
        for (ServiceFee serviceFee : serviceFees)
            if (!ObjectUtils.isEmpty(serviceFee.getBankAccountId()))
                bankAccountIds.add(serviceFee.getBankAccountId());

        Map<String, BankAccount> bankAccountMap = bankAccountDao.map(bankAccountIds);
        bankAccountService.fillBankNames(bankAccountMap.values());

        for (ServiceFee serviceFee : serviceFees) {
            if (bankAccountMap.containsKey(serviceFee.getBankAccountId()))
                serviceFee.setBankAccount(bankAccountMap.get(serviceFee.getBankAccountId()));
        }
    }
}
