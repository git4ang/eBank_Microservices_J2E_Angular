package ang.neggaw.operations.graphqls;

import ang.neggaw.operations.entities.Operation;
import ang.neggaw.operations.proxies.AccountRestProxy;
import ang.neggaw.operations.proxies.EmployeeRestProxy;
import ang.neggaw.operations.services.OperationService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author by: ANG
 * since: 11/09/2021 08:17
 */

@Log4j2
@RequiredArgsConstructor
@Component
public class OperationQuery implements GraphQLQueryResolver {

    private final OperationService operationService;
    private final AccountRestProxy accountRestProxy;
    private final EmployeeRestProxy employeeRestProxy;

    public Operation getOperation(final long operationNumber) {

        log.info("Fetching Operation with operationNumber: '{}'", operationNumber);
        Operation operation = operationService.getOperation(operationNumber);

        if(operation == null) {
            log.error("Unable to find Operation with operationNumber: '{}'", operationNumber);
            return null;
        }

        return operation;
    }

    public Collection<Operation> allOperations(final int offset, final int count) {

        log.info("Fetching allOperations...");
        return operationService.allOperations(offset, count);
    }
}