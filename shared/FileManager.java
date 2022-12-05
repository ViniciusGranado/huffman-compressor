package shared;

import java.io.EOFException;
import java.io.RandomAccessFile;

public class FileManager {
  private RandomAccessFile file;
  private long position;
	private int totalBits;
	private int bitsRead;
	private byte[] bytesArr;

  public FileManager(String filename, String mode) throws Exception {
    file = new RandomAccessFile(filename, mode); 
		bytesArr = new byte[(int) file.length()];
		file.read(bytesArr);
		file.seek(0);
  }

	public RandomAccessFile getFile() {
		return file;
	}

	public long getPosition() {
		return position;
	}

	public byte[] getBytesArr() {
		return bytesArr;
	}

	public int getTotalBits() {
		return totalBits;
	}

	public void setTotalBits(int totalBits) {
		this.totalBits = totalBits;
	}

  public byte readByte() throws Exception{
		byte byt = 0;

		try {
      byt = file.readByte();
			position++;
		} catch(EOFException e) {
			throw new EOFException("Got to the end of the file");
		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}

		return byt;
	}

	public int readInt() {
		int value = 0;

		try {
      value = file.readInt();
			position++;
		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}

		return value;
	}

	public char readChar() {
		char value = 0;

		try {
      value = file.readChar();
			position++;
		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}

		return value;
	}

	public char readCharWithoutChanginPosition() {
		char value = 0;

		try {
      value = file.readChar();
			file.seek(position);
		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}

		return value;
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

	public void writeChar(char value) {
		try {
      file.writeChar(value);
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
