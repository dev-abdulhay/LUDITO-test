package org.ludito.app.rest.payload.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.ludito.app.rest.payload.req.ReqPaging;

import java.util.Objects;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class ResPaging {
    private Integer currentPage;
    private Integer totalPages;
    private Long totalItems;

    public static ResPaging of(ReqPaging reqPaging, Long totalItems) {
        ResPaging paging = new ResPaging();
        paging.setCurrentPage(!Objects.isNull(reqPaging) ? reqPaging.getPage() : 0);
        paging.setTotalItems(totalItems);
        paging.setTotalPages(!Objects.isNull(reqPaging) ? ((int) Math.ceil(totalItems.doubleValue() / reqPaging.getSize())) : 0);
        return paging;
    }

    public ResPaging(Integer currentPage, Integer totalPages, Long totalItems) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
    }
}
