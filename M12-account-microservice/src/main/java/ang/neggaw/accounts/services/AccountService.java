package ang.neggaw.accounts.services;

import ang.neggaw.accounts.entities.Account;

import java.util.Collection;

/**
 * @author by: ANG
 * since: 09/09/2021 20:49
 */

public interface AccountService {
    Object createAccount(Account a);
    Object getAccount(long accountNumber);
    Collection<Account> allAccounts(int offset, int count);
    Object updateAccount(long accountNumber, Account a);
    Object deleteAccount(long accountNumber);
}

