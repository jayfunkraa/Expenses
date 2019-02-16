package com.kartoffelkopf.expenses.data;

import com.kartoffelkopf.expenses.model.Expense;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class ExpenseDaoImpl implements ExpenseDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Expense> findAll() {
        Session session = sessionFactory.openSession();
        CriteriaQuery<Expense> criteriaQuery = session.getCriteriaBuilder().createQuery(Expense.class);
        criteriaQuery.from(Expense.class);
        List<Expense> expenses = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return expenses;
    }

    @Override
    public Expense findById(long id) {
        Session session = sessionFactory.openSession();
        Expense expense = session.get(Expense.class, id);
        session.close();
        return expense;
    }

    @Override
    public void save(Expense expense) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(expense);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Expense expense) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(expense);
        session.getTransaction().commit();
        session.close();

    }
}
