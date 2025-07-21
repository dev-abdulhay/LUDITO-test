package org.ludito.app.rest.payload.res.history;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ludito.app.rest.entity.transaction.Operation;
import org.ludito.app.rest.payload.req.ReqPaging;
import org.ludito.app.rest.payload.res.transaction.ResOperationDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResHistorySearch {

    private List<ResOperationDto> operations;

    private ReqPaging paging;


    public static ResHistorySearch of(List<Operation> operations, ReqPaging paging) {
        ResHistorySearch res = new ResHistorySearch(ResOperationDto.of(operations), paging);
        return res;
    }


}
