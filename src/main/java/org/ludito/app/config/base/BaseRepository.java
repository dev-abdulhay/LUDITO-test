package org.ludito.app.config.base;

import org.ludito.app.config.utils.CoreUtils;
import org.ludito.app.rest.payload.req.ReqPaging;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface BaseRepository<R extends BaseEntity> extends JpaRepository<R, Long> {

    Optional<R> findByUuid(UUID uuid);

    default Page<R> findAllEntity(ReqPaging req) {
        PageRequest pageRequest = PageRequest.of(req.getPage(), req.getSize());
        return findAll(pageRequest.withSort(CoreUtils.getSortByOrderAndId()));
    }

}
