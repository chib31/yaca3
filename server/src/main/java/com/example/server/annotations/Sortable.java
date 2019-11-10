package com.example.server.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Sortable {
  String value() default ""; // Default sort: "Desc-1" means 1st priority, descending order
}
