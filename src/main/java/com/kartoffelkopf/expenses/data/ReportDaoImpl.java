package com.kartoffelkopf.expenses.data;

import com.kartoffelkopf.expenses.model.Report;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ReportDaoImpl implements ReportDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Report> findAll() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Report> criteriaQuery = criteriaBuilder.createQuery(Report.class);
        Root<Report> root = criteriaQuery.from(Report.class);
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("dueDate")));
        List<Report> reports = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return reports;
    }

    @Override
    public Report findById(long id) {
        Session session = sessionFactory.openSession();
        Report report = session.get(Report.class, id);
        session.close();
        return report;
    }

    @Override
    public void save(Report report) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(report);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Report report) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(report);
        session.getTransaction().commit();
        session.close();
    }

}
