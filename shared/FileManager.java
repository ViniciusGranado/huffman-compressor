package shared;

import java.io.RandomAccessFile;

public class FileManager {
  private RandomAccessFile file;
  private long position;

  public FileManager(String filename) throws Exception {
    file = new RandomAccessFile(filename, "r");
  }

  public char readChar() {
		char character = 0;

		try {
      character = (char) file.read();
			position++;
		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}

		return character;
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
}
