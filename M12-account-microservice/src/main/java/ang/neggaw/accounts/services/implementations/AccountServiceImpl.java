package ang.neggaw.accounts.services.implementations;

import ang.neggaw.accounts.entities.Account;
import ang.neggaw.accounts.proxies.CustomerRestProxy;
import ang.neggaw.accounts.proxies.EmployeeRestProxy;
import ang.neggaw.accounts.proxyModels.Customer;
import ang.neggaw.accounts.proxyModels.Employee;
import ang.neggaw.accounts.repositories.AccountRepository;
import ang.neggaw.accounts.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;

/**
 * @author by: ANG
 * since: 09/09/2021 20:49
 */

@Log4j2
@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRestProxy customerRestProxy;
    private final EmployeeRestProxy employeeRestProxy;

    @Override
    public Object createAccount(Account a) {

        Customer customer = customerRestProxy.getCustomerById(a.getIdCustomer());
        if(customer == null) return String.format("Unable to create. Customer with id: '%d' Not Found", a.getIdCustomer());

        Employee employee = employeeRestProxy.getEmployeeById(a.getIdEmployee());
        if(employee == null) return String.format("Unable to create. Employee with id: '%d' Not Found", a.getIdEmployee());

        a.setCustomer(customer);
        a.setEmployee(employee);
        a.setDateCreation(new Date());
        a.setEntityState(Account.EntityState.CREATED);

        return accountRepository.save(a);
    }

    @Override
    public Account getAccount(long accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public Collection<Account> allAccounts(int offset, int count) {
        offset = offset > accountRepository.count() ? 0 : offset;
        count = count > accountRepository.count() ? (int) accountRepository.count() : count;
        return accountRepository.findAll().subList(offset, count);
    }

    @Override
    public Object updateAccount(long accountNumber, Account a) {

        Account accountDB = getAccount(accountNumber);
        if(accountDB == null) return String.format("Unable to Update. Account with '%d' Not Found", accountNumber);
        a.setIdCustomer(accountDB.getIdCustomer());
        a.setIdEmployee(accountDB.getIdEmployee());
        a.setDateCreation(accountDB.getDateCreation());
        a.setEntityState(Account.EntityState.UPDATED);
        return accountRepository.saveAndFlush(a);
    }

    @Override
    public String deleteAccount(long accountNumber) {

        Account account = getAccount(accountNumber);
        accountRepository.delete(account);
        if(accountRepository.existsById(accountNumber)) return String.format("Unable to delete Account with '%d'.", accountNumber);
        else return String.format("Account with '%d' DELETED successfully.", accountNumber);
    }
}
