package me.xiaoying.sb.service.operation.operations;

import me.xiaoying.sb.service.TextComponentService;
import me.xiaoying.sb.service.operation.Operation;
import me.xiaoying.sb.service.operation.Operator;

@Operator(value = "按钮")
public class GetTextComponentOperation extends Operation {
    @Override
    public Object performOperation(Object[] objects) {
        String name = (String) objects[0];
        if (TextComponentService.getTextComponent(name) == null)
            return null;
        return TextComponentService.getTextComponent(name) == null ? null : TextComponentService.getTextComponent(name);
    }
}