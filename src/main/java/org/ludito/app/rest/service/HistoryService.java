package org.ludito.app.rest.service;

import org.ludito.app.rest.payload.req.history.ReqHistorySearch;
import org.ludito.app.rest.payload.res.history.ResHistorySearch;

public interface HistoryService {
    ResHistorySearch searchHistory(ReqHistorySearch req);
}
