package org.ludito.app.config.base;


import org.ludito.app.config.exceptions.CustomException;
import org.ludito.app.config.utils.CoreUtils;
import org.ludito.app.rest.payload.req.ReqPaging;
import org.ludito.app.rest.payload.res.ResPagingList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public interface BaseService<T extends BaseEntity> {

    BaseRepository<T> getRepository();

    default ResPagingList<?> findAll(ReqPaging req) {
        throw new CustomException("Not supported yet.");
    }

    default T getById(Long id) {
        return findById(id).orElseThrow(() -> new CustomException("Entity not found"));
    }

    default T getByUuid(UUID id) {
        return findByUuid(id).orElseThrow(() -> new CustomException("Entity not found"));
    }

    default T save(T entity) {
        return getRepository().save(entity);
    }

    default T saveAndFlush(T entity) {
        return getRepository().saveAndFlush(entity);
    }

    default List<T> saveAll(List<T> entities) {
        return getRepository().saveAll(entities);
    }

    default List<T> saveAllAndFlush(List<T> entities) {
        return getRepository().saveAllAndFlush(entities);
    }

    default Optional<T> findById(Long id) {
        return getRepository().findById(id);
    }

    default Optional<T> findByUuid(UUID uuid) {
        return getRepository().findByUuid(uuid);
    }

    default List<T> findAll() {
        return getRepository().findAll(CoreUtils.getSortByOrderAndId());
    }

    default Page<T> findAll(PageRequest req) {
        return getRepository().findAll(req.withSort(CoreUtils.getSortByOrderAndId()));
    }

    default Page<T> findAllEntity(ReqPaging req) {
        PageRequest pageRequest = PageRequest.of(req.getPage(), req.getSize());
        return getRepository().findAll(pageRequest.withSort(CoreUtils.getSortByOrderAndId()));
    }

    default void delete(T entity) {
        if (!Objects.isNull(entity)) {
            getRepository().delete(entity);
            getRepository().flush();
        }
    }

    default void deleteAll(List<T> entities) {
        if (!Objects.isNull(entities)) {
            getRepository().deleteAll(entities);
            getRepository().flush();
        }
    }

    default void deleteAll() {
        getRepository().deleteAll();
    }

    default void deleteById(Long id) {
        delete(getById(id));
    }

    default void deleteByUuid(UUID id) {
        delete(getByUuid(id));
    }

    default void setInvisible(UUID id) {
        T entity = getByUuid(id);
        entity.setIsVisible(true);
        save(entity);
    }

    default void setInvisible(Long id) {
        T entity = getById(id);
        entity.setIsVisible(false);
        save(entity);
    }
}
