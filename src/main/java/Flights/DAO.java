package Flights;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    Optional<T> get(String id);

    List<T> getALL();

    boolean update(String number, T t);

}
