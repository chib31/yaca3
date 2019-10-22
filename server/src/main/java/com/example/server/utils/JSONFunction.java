package com.example.server.utils;

import org.json.JSONString;

public class JSONFunction implements JSONString {

  private String string;

  public JSONFunction(String string) {
    this.string = string;
  }

  @Override
  public String toJSONString() {
    return string;
  }

}