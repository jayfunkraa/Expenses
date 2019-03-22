package com.kartoffelkopf.expenses.data;

import com.kartoffelkopf.expenses.model.CurrencyMap;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class CurrencyMapDaoImpl implements CurrencyMapDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<CurrencyMap> findAll() {
            Session session = sessionFactory.openSession();
            CriteriaQuery<CurrencyMap> criteriaQuery = session.getCriteriaBuilder().createQuery(CurrencyMap.class);
            criteriaQuery.from(CurrencyMap.class);
            List<CurrencyMap> currencyMaps = session.createQuery(criteriaQuery).getResultList();
            session.close();
            return currencyMaps;
        }

    @Override
    public CurrencyMap findById(long id) {
            Session session = sessionFactory.openSession();
            CurrencyMap currencyMap = session.get(CurrencyMap.class, id);
            session.close();
            return currencyMap;
        }

    @Override
    public void save(CurrencyMap currencyMap) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.saveOrUpdate(currencyMap);
            session.getTransaction().commit();
            session.close();
        }

    @Override
    public void delete(CurrencyMap currencyMap) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.delete(currencyMap);
            session.getTransaction().commit();
            session.close();
        }
}
