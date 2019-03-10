package com.kartoffelkopf.expenses.data;

import com.kartoffelkopf.expenses.model.ReportView;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ReportViewDaoImpl implements ReportViewDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<ReportView> findAll() {
            Session session = sessionFactory.openSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ReportView> criteriaQuery = criteriaBuilder.createQuery(ReportView.class);
            Root<ReportView> root = criteriaQuery.from(ReportView.class);
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("dueDate")));
            List<ReportView> reportViews = session.createQuery(criteriaQuery).getResultList();
            session.close();
            return reportViews;
        }

    @Override
    public ReportView findById(long id) {
        Session session = sessionFactory.openSession();
        ReportView reportView = session.get(ReportView.class, id);
        session.close();
        return reportView;
    }
}
