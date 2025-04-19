package me.xiaoying.serverbuild.entity.resolvelag;

public class ResolveLagMemoryEntity {
    private final int count;
    private final String type;
    private final String color;

    public ResolveLagMemoryEntity(int count, String type, String color) {
        this.count = count;
        this.type = type;
        this.color = color;
    }

    public int getCount() {
        return this.count;
    }

    public String getType() {
        return this.type;
    }

    public String getColor() {
        return this.color;
    }

    public boolean match(double count) {
        switch (this.getType()) {
            case ">":
                return count > this.getCount();
            case ">=":
            case "=>":
                return count >= this.getCount();
            case "<":
                return count < this.getCount();
            case "<=":
            case "=<":
                return count <= this.getCount();
            case "=":
                return count == this.getCount();
        }
        return false;
    }
}