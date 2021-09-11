package ang.neggaw.accounts.restControllers;

import ang.neggaw.accounts.entities.Account;
import ang.neggaw.accounts.graphqls.AccountMutation;
import ang.neggaw.accounts.graphqls.AccountQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

/**
 * @author by: ANG
 * since: 11/09/2021 08:35
 */

@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/api/accounts")
@RestController
public class AccountRestController {

    private final AccountQuery accountQuery;
    private final AccountMutation accountMutation;

    @GetMapping(value = "/{accountNumber}")
    public Account getAccountById(@PathVariable(value = "accountNumber") final long accountNumber) {
        return accountQuery.getAccount(accountNumber);
    }

    @DeleteMapping(value = "/{accountNumber}")
    public String deleteAccount(@PathVariable(value = "accountNumber") final long accountNumber) {
        return accountMutation.deleteAccount(accountNumber);
    }
}
