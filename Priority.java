import java.util.ArrayList;
import java.util.Objects;

public class Priority<T extends Comparable<T> & Cloneable> {
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

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    for (Node node : (ArrayList<Node>) values) {
      sb.append(node.getByte() + " " + node.getFrequency());
    }

    return sb.toString();
  }

  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    if (!super.equals(object)) return false;
    Priority<?> priority = (Priority<?>) object;
    return len == priority.len && java.util.Objects.equals(values, priority.values);
  }

  public int hashCode() {
    return Objects.hash(super.hashCode(), values, len);
  }
}
