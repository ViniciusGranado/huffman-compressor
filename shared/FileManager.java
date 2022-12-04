package shared;

import java.io.RandomAccessFile;

public class FileManager {
  private RandomAccessFile file;
  private long position;

  public FileManager(String filename, String mode) throws Exception {
    file = new RandomAccessFile(filename, mode);
  }

	public RandomAccessFile getFile() {
		return file;
	}

	public long getPosition() {
		return position;
	}

  public byte readByte() {
		byte byt = 0;

		try {
      byt = file.readByte();
			position++;
		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}

		return byt;
	}

  public void writeByte(byte byt) {
		try {
      file.writeByte(byt);
		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}
	}

	public void writeInt(int value) {
		try {
      file.writeInt(value);
		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}
	}

  public Boolean gotToEndOfFile() {
    try {
      return position == file.length();
    } catch(Exception e) {
      System.out.println(e);
      System.exit(0);
    }

    return null;
  }

  public void resetReading() {
		try {
			file.seek(0);
			position = 0;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}

  public void close() {
		try {
			file.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}

  public void generateCompressedFile(String code) {
		String byteString = "";
		for (int i = 0; i < code.toCharArray().length; i++) {
      byteString += code.charAt(i);

			if (byteString.length() == 8) {
				writeByte(ByteHelper.stringToByte(byteString));
				byteString = "";
			}
		}

    close();
  }
}
