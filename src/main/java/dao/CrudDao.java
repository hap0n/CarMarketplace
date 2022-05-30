package dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T, PK> {

    Optional<T> get(PK id);

    List<T> getAll();

    PK save(T t);

    void update(T t);

    void delete(PK id);
}
