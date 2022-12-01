package shared;

import java.io.RandomAccessFile;

public class FileManager {
  private RandomAccessFile file;
  private long position;

  public FileManager(String filename, String mode) throws Exception {
    file = new RandomAccessFile(filename, mode);
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

	public int readIntChar() {
		int character = 0;

		try {
      character = file.read();
			position++;
		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}

		return character;
	}

  public void writeChar(char character) {
		try {
      file.write(character);
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
    for(char c : code.toCharArray()) {
      writeChar(c);
    }

    close();
  }
}
