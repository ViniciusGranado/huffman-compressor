package shared;

public class ByteHelper {
  public static byte stringToByte(String str) {
    byte ret = (byte) 0;

    for (byte stringPosition = (byte) 7, bytePosition = (byte) 0; bytePosition < 8; stringPosition--, bytePosition++) {
      if (str.charAt(stringPosition) == '1') {
        ret = setBit(bytePosition, ret);
      }
    }

    return ret;
  }

  public static String byteToString(Byte byt) {
    StringBuilder ret = new StringBuilder("00000000");

    for (byte stringPosition = (byte) 7, bytePosition = (byte) 0; bytePosition < 8; stringPosition--, bytePosition++) {
      if (getBit(bytePosition, byt)) {
        ret.setCharAt(stringPosition, '1');
      }
    }

    return ret.toString();
  }

  private static byte setBit(byte bitPos, byte value) {
    byte mascara = (byte) 1;
    mascara <<= bitPos;

    return value |= mascara;
  }

  private static boolean getBit(byte bitPos, byte value) {
    byte mascara = (byte) 1;
    mascara <<= bitPos;

    return (value &= mascara) > 0;
  }
}
