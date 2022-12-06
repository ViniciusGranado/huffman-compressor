import java.io.EOFException;

public class Decompressor {
  static Node root;

  public static void decompress(FileManager data, String newFilename) {
    Byte endUsefulBits = null;

    try {
      endUsefulBits = data.readByte();
    } catch(Exception e) {
      System.out.println(e);
      System.exit(0);
    }
    
    decompressTree(data);

    FileManager newFile = null;

    try {
      newFile = new FileManager(newFilename, "rw");
    } catch (Exception e) {
      System.out.println(e);
    }

    String compressedData = getCompressedString(data);

    generateDecompressedFile(compressedData, endUsefulBits, newFile);
  }

  private static void decompressTree(FileManager compressedFile) {
    try {
      byte b = compressedFile.readByte();
      if (b == (byte) 1) {
        root = new Node();
      }
  
      decompressTree(compressedFile, root);
    } catch(Exception e) {
      System.out.println(e);
      System.exit(0);
    } 
	}

  private static void decompressTree(FileManager data, Node root){
    try {
      if (!data.gotToEndOfFile()) {
        Byte byt = data.readByte();
  
        if (byt == (byte) 1) {
          root.setLeft(new Node());
          decompressTree(data, root.getLeft());
        } else {
          byte b = data.readByte();
          root.setLeft(new Node(b));
        }
  
        byt = data.readByte();
        // 1 = internal node
        if (byt == (byte) 1) {
          root.setRight(new Node());
          decompressTree(data, root.getRight());
        } else {
          byte b = data.readByte();
          root.setRight(new Node(b));
        }
      }
    } catch (Exception e) {
      System.out.println(e);
      System.exit(0);
    }
  }

  private static String getCompressedString(FileManager data) {
    String compressedString = "";

    while (true) {
      Byte b = null;
      try {
        b = data.readByte();
      } catch(EOFException e) {
        break;
      } catch(Exception e) {
        System.out.println(e);
        System.exit(0);
      }

      compressedString += ByteHelper.byteToString(b);
    }
    
    return compressedString;
  }

  private static void generateDecompressedFile(String compressedData, Byte endUsefulBits, FileManager newFile) {
    Node node = root;

    for (int i = 0; i < compressedData.length() - endUsefulBits; i++) {
      if (node.isLeaf()) {
        newFile.writeByte(node.getByte());
        node = root;
        i--;
      } else {
        if (compressedData.charAt(i) == '1') {
          node = node.getRight();
        } else {
          node = node.getLeft();
        }
      }
    }
  }
}
