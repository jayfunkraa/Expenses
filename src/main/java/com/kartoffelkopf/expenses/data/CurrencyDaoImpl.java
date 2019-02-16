package com.kartoffelkopf.expenses.data;

import com.kartoffelkopf.expenses.model.Client;
import com.kartoffelkopf.expenses.model.Currency;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CurrencyDaoImpl implements CurrencyDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Currency> findAll() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Currency> criteriaQuery = criteriaBuilder.createQuery(Currency.class);
        Root<Currency> root = criteriaQuery.from(Currency.class);
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("currency")));
        List<Currency> currencies = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return currencies;
    }

    @Override
    public Currency findById(long id) {
        Session session = sessionFactory.openSession();
        Currency currency = session.get(Currency.class, id);
        session.close();
        return currency;
    }

    @Override
    public void save(Currency currency) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(currency);
        session.getTransaction().commit();
        session.close();
    }
}
