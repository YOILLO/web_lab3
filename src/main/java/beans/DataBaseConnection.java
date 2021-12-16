package beans;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseConnection {
    private final String unit = "my_unit";

    private EntityTransaction transaction;
    private EntityManager manager;
    private EntityManagerFactory factory;

    public DataBaseConnection (){
        connect();
    }

    private void connect(){
        factory = Persistence.createEntityManagerFactory(unit);
        manager = factory.createEntityManager();
        transaction = manager.getTransaction();
    }

    public List<Point> loadRaws(){
        List<Point> buffer = new ArrayList<Point>();
        try {
            transaction.begin();
            Query query = manager.createQuery("SELECT e FROM Point e");
            buffer = query.getResultList();
            transaction.commit();
        }catch (RuntimeException ex){
            if (transaction.isActive())
                transaction.rollback();
            throw ex;
        }
        return buffer;
    }

    public void addRow(Point point){
        try{
            transaction.begin();
            manager.persist(point);
            transaction.commit();
        } catch (RuntimeException ex){
            if (transaction.isActive()){
                transaction.rollback();
            }
            throw ex;
        }
    }

    public void clear(){
        try {
            transaction.begin();
            Query query = manager.createQuery("DELETE FROM Point e");
            query.executeUpdate();
            manager.clear();
            transaction.commit();
        }catch (RuntimeException ex){
            if (transaction.isActive())
                transaction.rollback();
            throw ex;
        }
    }
}
