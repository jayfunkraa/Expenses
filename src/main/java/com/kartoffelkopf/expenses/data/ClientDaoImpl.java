package com.kartoffelkopf.expenses.data;

import com.kartoffelkopf.expenses.model.Category;
import com.kartoffelkopf.expenses.model.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ClientDaoImpl implements ClientDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Client> findAll() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> root = criteriaQuery.from(Client.class);
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("name")));
        List<Client> clients = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return clients;
    }

    @Override
    public Client findById(long id) {
        Session session = sessionFactory.openSession();
        Client clients = session.get(Client.class, id);
        session.close();
        return clients;
    }

    @Override
    public void save(Client client) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(client);
        session.getTransaction().commit();
        session.close();
    }
}
