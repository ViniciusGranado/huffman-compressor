import java.util.Arrays;

public class GenericArray<T> {
  private final Object[] array;
  public int len;

  public GenericArray(int len) {
    array = new Object [len];
    this.len = 0;
  }

  T getArr(int index)
  {
    final T object= (T) array[index];
    return object;
  }

  void setArr(int index, T object)
  {
    array[index] = object;
  }

  void add(int index, T object) {
    T aux1 = (T) array[index];
    T aux2 = null;

    array[index] = object;

    for (int i = index + 1; i <= len; i++) {
      aux2 = (T) array[i];
      array[i] = aux1;
      aux1 = aux2;
    }

    len++;
  }

  T get(int index) {
    return (T) array[index];
  }

  int size() {
    return len;
  }

  @Override
  public String toString() {
    return Arrays.toString(array);
  }
}