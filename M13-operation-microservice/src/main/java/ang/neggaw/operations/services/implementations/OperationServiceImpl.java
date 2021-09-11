package ang.neggaw.operations.services.implementations;

import ang.neggaw.operations.entities.Operation;
import ang.neggaw.operations.proxies.AccountRestProxy;
import ang.neggaw.operations.proxies.EmployeeRestProxy;
import ang.neggaw.operations.proxyModels.Account;
import ang.neggaw.operations.proxyModels.Employee;
import ang.neggaw.operations.repositories.OperationRepository;
import ang.neggaw.operations.services.OperationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;

/**
 * @author by: ANG
 * since: 11/09/2021 08:00
 */

@Log4j2
@RequiredArgsConstructor
@Service
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;
    private final AccountRestProxy accountRestProxy;
    private final EmployeeRestProxy employeeRestProxy;

    @Override
    public Object createOperation(Operation o) {

        Account account = accountRestProxy.getAccountById(o.getAccountNumber());
        if(account == null) return String.format("Unable to create Operation. Account with accountNumber: '%s' Not Found.", o.getAccountNumber());

        Employee employee = employeeRestProxy.getEmployeeById(o.getIdEmployee());
        if(employee == null) return String.format("Unable to create Operation. Employee with idEmployee: '%s' Not Found.", o.getIdEmployee());

        o.setEntityState(Operation.EntityState.CREATED);
        o.setDateOperation(new Date());

        return operationRepository.save(o);
    }

    @Override
    public Operation getOperation(long operationNumber) {
        return operationRepository.findByOperationNumber(operationNumber);
    }

    @Override
    public Collection<Operation> allOperations(int offset, int count) {

        offset = (offset > operationRepository.count()) ? 0 : offset;
        count = count > operationRepository.count() ? (int) operationRepository.count() : count;
        return operationRepository.findAll().subList(offset, count);
    }

    @Override
    public Object updateOperation(long operationNumber, Operation o) {

        Operation operation = getOperation(operationNumber);
        if(operation == null) return String.format("Unable to Update Operation. Operation with operationNumber: '%s' Not Found.", operationNumber);

        o.setDateOperation(operation.getDateOperation());
        o.setAccountNumber(operation.getAccountNumber());
        o.setIdEmployee(operation.getIdEmployee());
        o.setEntityState(Operation.EntityState.UPDATED);

        return operationRepository.saveAndFlush(o);
    }

    @Override
    public String deleteOperation(long operationNumber) {

        Operation operation = getOperation(operationNumber);
        if(operation == null) return String.format("Unable to Delete Operation. Operation with operationNumber: '%s' Not Found.", operationNumber);

        operationRepository.delete(operation);

        if (! operationRepository.existsById(operationNumber))
            return String.format("Operation with operationNumber: '%s' DELETED successfully", operationNumber);

        return String.format("Unable to delete Operation with operationNumber: '%s'", operationNumber);
    }
}
