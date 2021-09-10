package ang.neggaw.accounts.graphqls;

import ang.neggaw.accounts.entities.Account;
import ang.neggaw.accounts.services.AccountService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author by: ANG
 * since: 10/09/2021 09:35
 */

@Log4j2
@RequiredArgsConstructor
@Component
public class AccountQuery implements GraphQLQueryResolver {

    private final AccountService accountService;

    public Account getAccount(final long accountNumber) {

        log.info("Fetching Account with accountNumber: '{}'...", accountNumber);
        Object resp = accountService.getAccount(accountNumber);
        if(resp == null) {
            log.error("Unable to find Account with accountNumber: '{}'", accountNumber);
            return null;
        }

        return (Account) resp;
    }

    public Collection<Account> allAccounts(final int offset, final int count) {

        log.info("Fetching all accounts...");
        return accountService.allAccounts(offset, count);
    }
}