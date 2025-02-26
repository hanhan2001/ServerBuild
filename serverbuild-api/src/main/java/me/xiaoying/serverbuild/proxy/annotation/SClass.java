package me.xiaoying.serverbuild.proxy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SClass {
    Type type();

    /**
     * Support multi version<br>
     * <br>
     * class name format is "class-version" or "class - version"<br>
     * if you don't set the class's version that will use version in SProxyProvider
     * <br>
     * eg:
     * <pre>
     *     {@code SClass(
     *     type = SClass.Type.NULL,
     *     className = {"net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata - v1_20_R0", "net.minecraft.network.PacketPlayOutEntityMetadata"
     * })}
     * </pre>
     * loop determine version until version matched<br>
     * if matched SProxyProvide will break loop and use the class of matched
     *
     * @return setting class names
     */
    String[] className();

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