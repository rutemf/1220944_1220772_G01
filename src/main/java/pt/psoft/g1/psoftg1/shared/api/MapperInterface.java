package pt.psoft.g1.psoftg1.shared.api;

import java.util.Optional;

public abstract class MapperInterface {

    public <T> String map(final T value) {
        if (value == null)
            return null;
        return value.toString();}

    public <T> T mapOpt(final Optional<T> i) {return i.orElse(null);}
}
