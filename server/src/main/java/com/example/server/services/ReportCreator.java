package com.example.server.services;

import com.example.server.annotations.Display;
import com.example.server.annotations.DisplayName;
import com.example.server.annotations.Filter;
import com.example.server.annotations.Sortable;
import com.example.server.enumerations.DisplayType;
import com.example.server.enumerations.FilterType;
import com.example.server.enumerations.IndexColumnType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Field;

class ReportCreator {
  static String constructColumnsJson(Class statClass, IndexColumnType indexColumnType) {

    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
    if (indexColumnType == IndexColumnType.NUMBERED) {
      arrayBuilder.add(createStatColumn("index", DisplayType.ALWAYS_SHOW, "#", null, false, null));
    }
    Field[] fields = statClass.getDeclaredFields();
    for (Field field : fields) {
      arrayBuilder.add(createStatColumn(
          field.getName(),
          field.getAnnotation(Display.class) == null ? DisplayType.OPTIONAL_SHOW : field.getAnnotation(Display.class).value(),
          field.getAnnotation(DisplayName.class) == null ? null : field.getAnnotation(DisplayName.class).value(),
          field.getAnnotation(Filter.class) == null ? null : field.getAnnotation(Filter.class).value(),
          field.isAnnotationPresent(Sortable.class),
          field.getAnnotation(Sortable.class) == null || field.getAnnotation(Sortable.class).value().length() < 1 ?
              null : field.getAnnotation(Sortable.class).value()));
    }
    return arrayBuilder.build().toString();
  }

  private static JsonObjectBuilder createStatColumn(String name, DisplayType displayType, @Nullable String displayName,
                                                    @Nullable FilterType filterType, boolean sortable,
                                                    String defaultSort) {
    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder().add("key", name);
    jsonObjectBuilder.add("label", displayName == null ?
        StringUtils.capitalize(StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(name), StringUtils.SPACE)) :
        displayName);
    jsonObjectBuilder.add("displayType", displayType.toString());
    // Flag to indicate default display settings so that defaults can be restored
    jsonObjectBuilder.add("displayByDefault",
        displayType == DisplayType.OPTIONAL_SHOW || displayType == DisplayType.ALWAYS_SHOW);
    if (filterType != null) {
      jsonObjectBuilder.add("filterType", filterType.toString());
    }
    if (sortable) {
      jsonObjectBuilder.add("sortColumn", true);
      if (defaultSort != null) {
        String[] arr = defaultSort.split("-");
        jsonObjectBuilder.add("sortDirection", arr[0]);
        jsonObjectBuilder.add("sortPriority", Integer.valueOf(arr[1]));
      }
    }
    return jsonObjectBuilder;
  }
}
