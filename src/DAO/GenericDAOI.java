package DAO;

import java.util.List;

import Model.Employe;

public interface GenericDAOI<T> {


    public void add(T t);

    public void update(T t);

    public void delete(int id);
    public T findById(int id);

    public List<T> findAll();


}
