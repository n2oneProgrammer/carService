public class AirBag {
    private final String name;
    private final boolean isExist;

    public AirBag(String name, boolean isExist) {
        this.name = name;
        this.isExist = isExist;
    }

    public String getName() {
        return name;
    }

    public boolean isExist() {
        return isExist;
    }
}
