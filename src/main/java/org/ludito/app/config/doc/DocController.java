package org.ludito.app.config.doc;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Tag(name = "", description = "")
public @interface DocController {
    String name();

    String description() default "";
}
