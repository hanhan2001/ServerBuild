package me.xiaoying.serverbuild.script.interpreter;

import me.xiaoying.serverbuild.script.interpreter.interpreters.PlayerSelectInterpreter;

import java.util.ArrayList;
import java.util.List;

/**
 * InterpreterService
 */
public class InterpreterService {
    List<Interpreter> knownInterpreter = new ArrayList<>();

    /**
     * 初始化
     */
    public InterpreterService() {
        this.registerInterpreter(new PlayerSelectInterpreter());
    }

    /**
     * 注册 Interpreter
     *
     * @param interpreter interpreter
     */
    public void registerInterpreter(Interpreter interpreter) {
        if (this.knownInterpreter.contains(interpreter))
            return;

        this.knownInterpreter.add(interpreter);
    }

    /**
     * 取消注册 Interpreter
     *
     * @param interpreter interpreter
     */
    public void unregisterInterpreter(Interpreter interpreter) {
        this.knownInterpreter.remove(interpreter);
    }

    /**
     * 获取 Interpreter
     *
     * @return ArrayList
     */
    public List<Interpreter> getInterpreter() {
        return this.knownInterpreter;
    }

    /**
     * 执行解释器
     *
     * @param string 被解释内容
     * @return String[]
     */
    public String interpreter(String string) {
        // 执行解释器
        for (Interpreter interpreter : this.knownInterpreter)
            string = interpreter.interpret(string);

        return string;
    }
}