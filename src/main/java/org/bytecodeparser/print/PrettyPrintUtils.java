package org.bytecodeparser.print;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class PrettyPrintUtils {

  public static String prettyPrint(Object obj) {
    return prettyPrint(obj, 0);
  }

  public static String prettyPrint(Object obj, int tabs) {
    return prettyPrintLines(obj, tabs).stream()
        .map(l -> "  ".repeat(l.tabs) + l.data)
        .collect(Collectors.joining("\n"));
  }

  //todo: add support for Lists
  private static List<Line> prettyPrintLines(Object obj, int tabs) {
    if (obj == null) {
      return Collections.singletonList(new Line("null", tabs));
    }

    if (obj.getClass().isAnnotationPresent(CustomPrettyPrint.class)) {
      return customClassPrettyPrint(obj, tabs);
    }

    if (obj.getClass().isPrimitive() || isBoxed(obj.getClass()) || obj.getClass().equals(String.class)) {
      return Collections.singletonList(new Line(obj.toString(), tabs));
    }
    Class<?> clazz = obj.getClass();
    ArrayList<Line> rows = new ArrayList<>();

    rows.add(new Line(clazz.getSimpleName() + " {", tabs));

    Field[] fields = clazz.getDeclaredFields();
    for (Field field : fields) {
      if (Modifier.isStatic(field.getModifiers())) {
        continue;
      }
      Class<?> type = field.getType();
      List<Line> lines = new ArrayList<>();
      if (type.isPrimitive() || isBoxed(type)) {
        lines.add(new Line(prettyPrintValue(obj, field).toString(), tabs + 1));
      } else {
        Object value = getFieldValue(obj, field);
        if (type.isArray()) {
          lines.addAll(prettyPrintArray(value, tabs + 1));
        } else if (Collection.class.isAssignableFrom(type)) {
          lines.addAll(prettyPrintCollection(value, tabs + 1));
        } else if (type.isEnum()) {
          lines.add(new Line(value.toString(), tabs + 1));
        } else {
          lines.addAll(prettyPrintLines(value, tabs + 1));
        }
      }
      Line.prependFirst(lines, camelToSnake(field.getName()) + " = ");
      Line.appendLast(lines, ",");

      rows.addAll(lines);
    }
    Line.stripSuffixLast(rows, ",");
    rows.add(new Line("}", tabs));

    return rows;
  }

  private static List<Line> customClassPrettyPrint(Object obj, int tabs) {
    try {
      Method prettyPrint = obj.getClass().getDeclaredMethod("prettyPrint");
      prettyPrint.setAccessible(true);
      return Collections.singletonList(new Line((String) prettyPrint.invoke(obj), tabs));
    } catch (NoSuchMethodException e) {
      throw new IllegalStateException(
          "Class '" + obj.getClass().getSimpleName() + "' is annotated with @" +
              CustomPrettyPrint.class.getSimpleName() +
              ", but method 'prettyPrint()' is absent");
    } catch (InvocationTargetException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  private static boolean isBoxed(Class<?> type) {
    return type.equals(Byte.class) || type.equals(Short.class) || type.equals(Integer.class) ||
        type.equals(Float.class) || type.equals(Double.class);
  }

  private static List<Line> prettyPrintArray(Object array, int tabs) {
    int length = Array.getLength(array);
    if (length == 0) {
      return Collections.singletonList(new Line("[]", tabs));
    }
    ArrayList<Line> rows = new ArrayList<>();
    rows.add(new Line("[", tabs));
    for (int i = 0; i < length; i++) {
      Object element = Array.get(array, i);
      List<Line> lines = prettyPrintLines(element, tabs + 1);
      Line.prependFirst(lines, i + ": ");
      if (i < length - 1) {
        Line.appendLast(lines, ",");
      }
      rows.addAll(lines);
    }
    rows.add(new Line("]", tabs));
    return rows;
  }

  private static List<Line> prettyPrintCollection(Object coll, int tabs) {
    Collection<?> collection = (Collection<?>) coll;
    if ( collection.isEmpty()) {
      return Collections.singletonList(new Line("[]", tabs));
    }
    ArrayList<Line> rows = new ArrayList<>();
    rows.add(new Line("[", tabs));
    collection
        .forEach(element -> {
          rows.addAll(prettyPrintLines(element, tabs + 1));
          Line.appendLast(rows, ",");
        });
    Line.stripSuffixLast(rows, ",");

    rows.add(new Line("]", tabs));
    return rows;
  }

  private static Object prettyPrintValue(Object obj, Field field) {
    Object fieldValue;

    try {
      if (field.isAnnotationPresent(CustomPrettyPrint.class)) {
        Method prettyPrintMethod = obj.getClass().getDeclaredMethod(field.getName() + "PrettyPrint");
        prettyPrintMethod.setAccessible(true);
        fieldValue = prettyPrintMethod.invoke(obj);
      } else {
        fieldValue = getFieldValue(obj, field);
      }
    } catch (InvocationTargetException | IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (NoSuchMethodException e) {
      throw new IllegalStateException(
          "Field '" + field.getName() + "' is annotated with @" + CustomPrettyPrint.class.getSimpleName() +
              ", but method '" + field.getName() + "PrettyPrint()' is absent");
    }
    return fieldValue;
  }

  private static Object getFieldValue(Object obj, Field field) {
    try {
      field.setAccessible(true);
      return field.get(obj);
    } catch (IllegalAccessException ee) {
      throw new RuntimeException(ee);
    }
  }

  private static String camelToSnake(String input) {
    StringBuilder result = new StringBuilder();
    boolean firstChar = true;

    for (char c : input.toCharArray()) {
      if (Character.isUpperCase(c)) {
        if (!firstChar) {
          result.append('_');
        }
        result.append(Character.toLowerCase(c));
      } else {
        result.append(c);
      }
      firstChar = false;
    }

    return result.toString();
  }

  @Data
  @AllArgsConstructor
  static class Line {
    String data;
    int tabs;

    public void prepend(String str) {
      data = str + data;
    }

    public void append(String str) {
      data = data + str;
    }

    public void stripSuffix(String suffix) {
      if (data != null && suffix != null && data.endsWith(suffix)) {
        data = data.substring(0, data.length() - suffix.length());
      }
    }

    public static void prependFirst(List<Line> lines, String str) {
      lines.get(0).prepend(str);
    }

    public static void appendLast(List<Line> lines, String str) {
      lines.get(lines.size() - 1).append(str);
    }

    public static void stripSuffixLast(List<Line> lines, String suffix) {
      lines.get(lines.size() - 1).stripSuffix(suffix);
    }
  }
}
