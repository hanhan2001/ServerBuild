package me.xiaoying.serverbuild.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Command annotation Command
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    /**
     * Command's names</br>
     * If sender called command's name not equal any name of the command's name will skip this command.
     *
     * @return Command's names
     */
    String[] values();

    /**
     * Command's length<br>
     * If sender called command length not equal this length will print help message.<br>
     * Allow sender call the command when sender called command length equal any length<br>
     * No limit if length is -1<br>
     *
     * @return Command's length
     */
    int[] length();

    /**
     * Command's description
     *
     * @return command's description
     */
    String description() default "";

    /**
     * Get command's permission<br>
     * Allow sender call the command when sender has any permission<br>
     * No limit if sender is Admin
     *
     * @return command's permissions
     */
    String[] permission() default "";

    /**
     * Get command's parameter<br>
     *
     * @return command's parameter description
     */
    String[] parameters() default "";
}