package main.java.com.revature.app.app.daos;

public interface EntityDAO<E> {
    E createEntity(E newEntity);

    E getById(String id);

    E update(E updatedEntity);

    E deleteById(String id);

}
