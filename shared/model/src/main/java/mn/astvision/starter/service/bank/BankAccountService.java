package mn.astvision.starter.service.bank;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.dao.bank.BankAccountDao;
import mn.astvision.starter.dao.bank.BankDao;
import mn.astvision.starter.model.bank.Bank;
import mn.astvision.starter.model.bank.BankAccount;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author digz6666
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankDao bankDao;
    private final BankAccountDao bankAccountDao;

    public List<BankAccount> list(String bankCode, String search, PageRequest pageRequest) {
        List<BankAccount> bankAccounts = bankAccountDao.list(bankCode, search, pageRequest);
        fillBankNames(bankAccounts);
        return bankAccounts;
    }

    public List<BankAccount> select() {
        List<BankAccount> bankAccounts = bankAccountDao.select();
        fillBankNames(bankAccounts);
        return bankAccounts;
    }

    public void fillBankNames(Collection<BankAccount> bankAccounts) {
        List<String> bankCodes = new ArrayList<>();
        for (BankAccount bankAccount : bankAccounts)
            bankCodes.add(bankAccount.getBankCode());

        Map<String, Bank> bankMap = bankDao.map(bankCodes);
        for (BankAccount bankAccount : bankAccounts) {
            if (bankMap.containsKey(bankAccount.getBankCode()))
                bankAccount.setBankName(bankMap.get(bankAccount.getBankCode()).getName());
        }
    }
}
