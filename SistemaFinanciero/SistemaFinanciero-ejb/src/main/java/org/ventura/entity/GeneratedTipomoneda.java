package org.ventura.entity;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.ventura.entity.TipomonedaType.NUEVO_SOL;

@Target({ METHOD, FIELD })
@Retention(RUNTIME)
public @interface GeneratedTipomoneda {

	TipomonedaType strategy() default NUEVO_SOL;

}
