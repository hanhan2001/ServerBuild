package me.xiaoying.serverbuild.proxy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SClass {
    Type type();

    String className();

    enum Type {
        NMS("net.minecraft.server.%version%."),
        CRAFT_BUKKIT("org.bukkit.craftbukkit.%version%."),
        // use in don't supported server
        // need to call the method of NMSClass.Type#setPrefix to set prefix value if you use this type.
        OTHER(""),
        NULL("");

        private String prefix;

        Type(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefix() {
            return this.prefix;
        }

        public void setPrefix(String prefix) {
            if (this.prefix != null && !this.prefix.isEmpty())
                return;

            this.prefix = prefix;
        }

        public String getClassName(String className, String version) {
            return this.prefix.replace("%version%", version) + className;
        }
    }
}