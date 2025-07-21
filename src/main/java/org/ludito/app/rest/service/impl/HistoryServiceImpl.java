package org.ludito.app.rest.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.ludito.app.config.utils.GlobalVar;
import org.ludito.app.rest.entity.auth.User;
import org.ludito.app.rest.entity.transaction.Operation;
import org.ludito.app.rest.payload.req.history.ReqHistorySearch;
import org.ludito.app.rest.payload.res.history.ResHistorySearch;
import org.ludito.app.rest.service.HistoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final EntityManager entityManager;

    @Override
    public ResHistorySearch searchHistory(ReqHistorySearch req) {
        User user = GlobalVar.getAuthUser();

        var cb = entityManager.getCriteriaBuilder();
        var cq = cb.createQuery(Operation.class);
        var root = cq.from(Operation.class);

        var predicates = new ArrayList<Predicate>();

        predicates.add(cb.equal(root.get("user"), user));

        if (req.getType() != null) {
            predicates.add(cb.equal(root.get("type"), req.getType()));
        }

        if (req.getFromDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("executeDate"), req.getFromDate()));
        }
        if (req.getToDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("executeDate"), req.getToDate()));
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        cq.orderBy(cb.desc(root.get("executeDate")));

        var query = entityManager.createQuery(cq);

        int pageNumber = req.getPaging().getPage();
        int pageSize = req.getPaging().getSize();
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);

        var resultList = query.getResultList();

        return ResHistorySearch.of(resultList, req.getPaging());

    }
}