package shared;

public class ByteHelper {
  public static byte stringToByte(String str) {
    byte ret = (byte) 0;

    for (byte stringPosition = (byte) 0, bytePosition = (byte) 0; bytePosition < 8; stringPosition--, bytePosition++) {
      if (str.charAt(stringPosition) == '1') {
        ret = setBit(bytePosition, ret);
      }
    }

    return ret;
  }

  private static byte setBit(byte bitPos, byte value) {
    byte mascara = (byte) 1;
    mascara <<= bitPos;

    return value |= mascara;
  }
}
