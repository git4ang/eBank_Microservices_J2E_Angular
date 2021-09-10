package ang.neggaw.accounts.graphqls;

import ang.neggaw.accounts.entities.Account;
import ang.neggaw.accounts.services.AccountService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author by: ANG
 * since: 10/09/2021 09:35
 */

@Log4j2
@RequiredArgsConstructor
@Component
public class AccountMutation implements GraphQLMutationResolver {

    private final AccountService accountService;

    public Account createAccount(@RequestBody Account account) {

        log.info("Creating Account with data: {}...", account);

        Object resp = accountService.createAccount(account);
        if(resp.getClass().getSimpleName().equals("String")) {
            log.error(resp);
            return null;
        }

        Account accountDB = (Account) resp;
        log.info("Account with accountNumber: '{}' CREATED successfully.", accountDB.getAccountNumber());
        return accountDB;
    }

    public Account updateAccount(final long accountNumber, @RequestBody Account account) {

        log.info("Updating Account with accountNumber: {}...", accountNumber);

        Object resp = accountService.updateAccount(accountNumber, account);
        if(resp.getClass().getSimpleName().equals("String")) {
            log.error(resp);
            return null;
        }

        log.info("Account with accountNumber: '{}' UPDATED successfully.", accountNumber);
        return (Account) resp;
    }

    public String deleteAccount(final long accountNumber) {

        log.info("Deleting Account with accountNumber: {}...", accountNumber);

        Object resp = accountService.deleteAccount(accountNumber);
        if(resp.getClass().getSimpleName().equals("String")) {
            log.error(resp);
            return resp.toString();
        }

        return resp.toString();
    }
}
