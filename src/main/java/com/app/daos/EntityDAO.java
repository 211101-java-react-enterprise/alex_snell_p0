package main.java.com.revature.app.daos;
// TODO: Document this type.
public interface EntityDAO<E> {
    E create(E newEntity);

    // QUESTION Use enum as parameter for generic getEntity()?
    // TODO Clarify parameter as UUID if implemented
    E getById(String id);

    E update(E updatedEntity);
    void deleteById(String id);
}
