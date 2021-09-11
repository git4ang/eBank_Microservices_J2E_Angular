package ang.neggaw.operations.services;

import ang.neggaw.operations.entities.Operation;

import java.util.Collection;

/**
 * @author by: ANG
 * since: 11/09/2021 08:00
 */

public interface OperationService {
    Object createOperation(Operation o);
    Operation getOperation(long operationNumber);
    Collection<Operation> allOperations(int offset, int count);
    Object updateOperation(long operationAccount, Operation o);
    String deleteOperation(long operationNumber);
}

