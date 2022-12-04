package shared;

import java.util.ArrayList;

public class Priority<T extends Comparable<T>> {
  ArrayList<T> values;
  int len;

  public Priority() {
    values = new ArrayList<T>();
    len = 0;
  }

  public int size() {
  return len;
  }

  public void add(T object) {
    if (len == 0) {
      values.add(0, object);
      len++;
      return;
    }

    int currentIndex = len - 1;

    do {
      if (values.get(currentIndex).compareTo(object) < 0) {
        values.add(currentIndex + 1, object);
        break;
      }

      currentIndex--;
    } while (currentIndex > -1);

    if (currentIndex == -1) {
      values.add(0, object);
    }
  
    len++;
  }

  public T poll () {
    len--;
    return values.remove(0);
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();

    for (Node node : (ArrayList<Node>) values) {
      sb.append(node.getByte() + " " + node.getFrequency());
    }

    return sb.toString();
  }
}
