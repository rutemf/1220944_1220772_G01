package auth_users.shared.api;

public abstract class MapperInterface {

    public <T> String map(final T value) {
        if (value == null)
            return null;
        return value.toString();
    }
}
