import java.util.Arrays;

public class GenericArray<T> {
  private final Object[] array;
  public final int len;

  public GenericArray(int len) {
    array = new Object [len];
    this.len = len;
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

  @Override
  public String toString() {
    return Arrays.toString(array);
  }
}