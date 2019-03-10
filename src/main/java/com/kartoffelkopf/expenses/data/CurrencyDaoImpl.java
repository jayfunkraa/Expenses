package com.kartoffelkopf.expenses.data;

import com.kartoffelkopf.expenses.model.Currency;
import com.kartoffelkopf.expenses.model.CurrencyMap;
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

    @Override
    public List<CurrencyMap> findAllCurrencyMaps() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<CurrencyMap> criteriaQuery = criteriaBuilder.createQuery(CurrencyMap.class);
        Root<CurrencyMap> root = criteriaQuery.from(CurrencyMap.class);
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("from")));
        List<CurrencyMap> currencyMaps = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return currencyMaps;
    }

    @Override
    public CurrencyMap findCurrencyMapByFromTo(Currency from, Currency to) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<CurrencyMap> criteriaQuery = criteriaBuilder.createQuery(CurrencyMap.class);
        Root<CurrencyMap> root = criteriaQuery.from(CurrencyMap.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("from"), from), criteriaBuilder.equal(root.get("to"), to));
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("date")));
        List<CurrencyMap> currencyMaps = session.createQuery(criteriaQuery).getResultList();
        if (currencyMaps.size() != 0) {
            return currencyMaps.get(0);
        }else return null;
    }


}
