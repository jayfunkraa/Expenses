package com.kartoffelkopf.expenses.data;

import com.kartoffelkopf.expenses.model.Receipt;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class ReceiptDaoImpl implements ReceiptDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Receipt> findAll() {
        Session session = sessionFactory.openSession();
        CriteriaQuery<Receipt> criteriaQuery = session.getCriteriaBuilder().createQuery(Receipt.class);
        criteriaQuery.from(Receipt.class);
        List<Receipt> receipts = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return receipts;
    }

    @Override
    public Receipt findById(long id) {
        Session session = sessionFactory.openSession();
        Receipt receipt = session.get(Receipt.class, id);
        session.close();
        return receipt;
    }

    @Override
    public void save(Receipt receipt) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(receipt);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Receipt receipt) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(receipt);
        session.getTransaction().commit();
        session.close();
    }
}
