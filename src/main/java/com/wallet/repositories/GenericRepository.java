package main.java.com.wallet.repositories;

public interface GenericRepository<T> {
    public Integer saveOrUpdate(T entity);
    public T findById(Integer id);
}
