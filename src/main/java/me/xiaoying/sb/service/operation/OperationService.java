package me.xiaoying.sb.service.operation;

import me.xiaoying.sb.exception.OperationException;
import me.xiaoying.sb.utils.ExceptionUtil;

import java.util.HashMap;
import java.util.Map;

public class OperationService {
    Map<String, Operation> operations = new HashMap<>();

    public void registerOperation(Operation operation) {
        for (String s : operation.getClass().getAnnotation(Operator.class).value())
            this.operations.put(s, operation);
    }

    public void unregisterOperation(Operation operation) {
        for (String s : this.operations.keySet()) {
            if (this.operations.get(s) != operation)
                continue;

            this.operations.remove(s);
        }
    }

    public void unregisterOperations() {
        this.operations.clear();
    }

    public Map<String, Operation> getOperations() {
        return this.operations;
    }

    public Object performOperation(String name, Object[] objects) {
        if (this.operations.get(name) == null)
            ExceptionUtil.throwException(new OperationException("Can't find '" + name + "', please check is registered operation"));

        return this.operations.get(name).performOperation(objects);
    }
}