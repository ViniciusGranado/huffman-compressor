package shared;

public class Priority<T extends Comparable<T>> {
  GenericArray<T> values;
  int len;

  public Priority() {
    values = new GenericArray<T>(256);
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
    if (values.size() == 0) return "[]";
    String ret = "[";

    for (int i = 0; i < values.size(); i++) {
      if (i == values.size() - 1) {
        ret += values.get(i) + "]";
        continue;
      }

      ret += values.get(i) + ", ";
    }

    return ret;
  }
}
