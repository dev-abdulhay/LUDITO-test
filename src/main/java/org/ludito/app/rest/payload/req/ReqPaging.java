package org.ludito.app.rest.payload.req;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqPaging {
    private Integer page;
    private Integer size;

    private final Integer minSize = 1;
    private final Integer maxSize = 100;

    public Integer getPage() {
        if (page == null || page < 0) {
            return 0;
        }
        return page;
    }

    public Integer getSize() {
        if (this.size == null) {
            return minSize;
        }
        if (this.size < minSize) {
            return minSize;
        }
        if (this.size > maxSize) {
            return maxSize;
        }
        return this.size;
    }
}
