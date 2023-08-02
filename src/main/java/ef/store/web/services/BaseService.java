package ef.store.web.services;

import java.util.Optional;
import java.util.Set;

public interface BaseService<T> {

    public Set<T> getAll();

    public Optional<T> get(String id);

}