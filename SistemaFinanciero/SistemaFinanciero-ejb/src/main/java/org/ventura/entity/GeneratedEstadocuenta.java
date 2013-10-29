package org.ventura.entity;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.ventura.entity.EstadocuentaType.ACTIVO;

@Target({ METHOD, FIELD })
@Retention(RUNTIME)
public @interface GeneratedEstadocuenta {

	EstadocuentaType strategy() default ACTIVO;

}
