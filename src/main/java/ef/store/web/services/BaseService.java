package ef.store.web.services;

import ef.store.web.base.request.DataTableRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface BaseService<T> {

    public List<T> getAll();

    public Optional<T> get(String id);

}