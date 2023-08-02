package ef.store.web.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BaseRepository<T> {

    public Set<T> findAll();

    public Optional<T> findById(String id);

    public T save(T domain);

    public Set<T> saveAll(List<T> domains);

    public void delete(String id);

}
