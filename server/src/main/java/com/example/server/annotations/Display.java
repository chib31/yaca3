package com.example.server.annotations;

import com.example.server.enumerations.DisplayType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Display {
  DisplayType value();
}
