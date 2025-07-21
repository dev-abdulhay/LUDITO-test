package org.ludito.app.config.base;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false, updatable = false, columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private UUID uuid = UUID.randomUUID();

    @Column(name = "is_visible", columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isVisible = true;

    @JsonIgnore
    @JsonIgnoreProperties
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @JsonIgnore
    @JsonIgnoreProperties
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "order_num")
    protected Integer order;


    public Boolean getIsVisible() {
        return !Objects.isNull(isVisible) && isVisible;
    }

    public BaseEntity(UUID uuid) {
        this.uuid = uuid;
    }

    public Integer getOrder() {
        return Objects.requireNonNullElse(order, 0);
    }

    public BaseEntity(Integer order) {
        this.order = order;
    }
}
