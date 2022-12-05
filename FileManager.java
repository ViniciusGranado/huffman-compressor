

import java.io.EOFException;
import java.io.RandomAccessFile;

public class FileManager {
  private RandomAccessFile file;
  private long position;
	private byte[] bytesArr;

  public FileManager(String filename, String mode) throws Exception {
    file = new RandomAccessFile(filename, mode); 
		bytesArr = new byte[(int) file.length()];
		file.read(bytesArr);
		file.seek(0);
  }

	public byte[] getBytesArr() {
		return bytesArr;
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

	public void writeByte(byte byt) {
		try {
      file.writeByte(byt);
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
}
