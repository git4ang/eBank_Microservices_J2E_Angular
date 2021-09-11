package ang.neggaw.operations.graphqls;

import ang.neggaw.operations.entities.Operation;
import ang.neggaw.operations.proxies.AccountRestProxy;
import ang.neggaw.operations.proxies.EmployeeRestProxy;
import ang.neggaw.operations.services.OperationService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author by: ANG
 * since: 11/09/2021 08:18
 */

@Log4j2
@RequiredArgsConstructor
@Component
public class OperationMutation implements GraphQLMutationResolver {

    private final OperationService operationService;
    private final AccountRestProxy accountRestProxy;
    private final EmployeeRestProxy employeeRestProxy;

    public Operation createOperation(@RequestBody Operation operation) {

        log.info("Creating Operation with these data: '{}'", operation);

        Object resp = operationService.createOperation(operation);
        if(resp.getClass().getSimpleName().equals("String")) {
            log.error(resp);
            return null;
        }

        Operation op = (Operation) resp;
        log.info("Operation with operationNumber: '{}' CREATED successfully", op.getOperationNumber());
        return op;
    }

    public Operation updateOperation(final long operationNumber, @RequestBody Operation operation) {

        log.info("Updating Operation with operationNumber: '{}'...", operationNumber);

        Object resp = operationService.updateOperation(operationNumber, operation);
        if(resp.getClass().getSimpleName().equals("String")) {
            log.error(resp);
            return null;
        }

        Operation op = (Operation) resp;
        log.info("Operation with operationNumber: '{}' UPDATED successfully", op.getOperationNumber());
        return op;
    }

    public String deleteOperation(final long operationNumber) {

        log.info("Deleting Operation with operationNumber: '{}'...", operationNumber);

        String resp = operationService.deleteOperation(operationNumber);
        if(resp.startsWith("Unable")) {
            log.error(resp);
        }

        log.info("Operation with operationNumber: '{}' DELETED successfully.", operationNumber);
        return resp;
    }
}