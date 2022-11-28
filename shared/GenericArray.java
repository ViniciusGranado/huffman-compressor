package shared;
import java.util.Arrays;

public class GenericArray<T> {
  public final Object[] values;
  private int len;

  public GenericArray(int len) {
    values = new Object [len];
    this.len = 0;
  }

  public T[] getArr()
  {
    return (T[]) values;
  }

  public void setArr(int index, T object)
  {
    values[index] = object;
  }

  public void add(int index, T object) {
    T aux1 = (T) values[index];
    T aux2 = null;

    values[index] = object;

    for (int i = index + 1; i <= len; i++) {
      aux2 = (T) values[i];
      values[i] = aux1;
      aux1 = aux2;
    }

    len++;
  }

  public T remove(int index) {
    T ret = (T) values[index];

    for (int i = index; i < len - 1; i++) {
      values[i] = values[i + 1];
    }

    len--;
    values[len] = null;

    return ret;
  }

  public void clear() {
    for (int i = 0; i < len; i++) {
      values[i] = null;
    }

    len = 0;
  }

  public void push(T value) {
    values[len] = value;
    len++;
  }

  public T get(int index) {
    return (T) values[index];
  }

  public int size() {
    return len;
  }

  @Override
  public String toString() {
    return Arrays.toString(values);
  }
}