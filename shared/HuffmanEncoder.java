package shared;

import java.io.RandomAccessFile;
import java.util.ArrayList;

public class HuffmanEncoder {
  public void compress(FileManager data, String newFilename) {
    ArrayList<Node> frequency = getFrequencyArray(data);
    Node root = getHuffmanTree(frequency);
    ArrayList<CharCodeMap> charCodeMap = buildCharCodeMap(root);

    CompressResult compressedData = getCompressedData(data, charCodeMap, root);

    FileManager compressedFile = null;

    try {
      compressedFile = new FileManager(newFilename + ".edd", "rw");
    } catch (Exception e) {
      System.out.println(e);
      System.exit(0);
    }

    String newFileContent = compressedData.getCompressedData();
    compressCodeMap(charCodeMap, compressedFile, newFileContent.length() % 8);
    compressedFile.generateCompressedFile(newFileContent);
  }

  // public void decompress(FileManager data, String newFilename) {
  //   Node root = new Node();
  //   decompressMap(data, root);

  //   String compressedData = getCompressedData(data);
  //   String decompressedString = "";
  //   Node node = root;

  //   FileManager decompressedFile = null;

  //   try {
  //     decompressedFile = new FileManager(newFilename, "w");
  //   } catch (Exception e) {
  //     System.out.println(e);
  //     System.exit(0);
  //   }

  //   for (int i = 0; i < compressedData.length();) {
  //     while (!node.isLeaf()) {
  //       char character = compressedData.charAt(i);

  //       if (character == '0') {
  //         node = node.getLeft();
  //       } else {
  //         node = node.getRight();
  //       }

  //       i++;
  //     }

  //     decompressedFile.writeByte(node.getByte());
  //     node = root;
  //   }

  //   for (char c : decompressedString.toCharArray()) {
  //     decompressedFile.writeChar(c);
  //   }
  // }

  public static ArrayList<Node> getFrequencyArray(FileManager data) {
    ArrayList<Node> frequency = new ArrayList<>();
    ArrayList<Byte> byteControl = new ArrayList<>();
    boolean isEndOfFile = false;

    while (!isEndOfFile) {
      byte b = data.readByte();

      if (frequency.size() == 0) {
        byteControl.add(b);
        frequency.add(new Node(1, b));
        isEndOfFile = data.gotToEndOfFile();
        continue;
      }

      int index = byteControl.indexOf(b);
      if (index != -1) {
        frequency.get(index).addFrequency();
        isEndOfFile = data.gotToEndOfFile();
        continue;
      }

      byteControl.add(b);
      frequency.add(new Node(1, b));

      isEndOfFile = data.gotToEndOfFile();
    }

    return frequency;
  }

  public static Node getHuffmanTree(ArrayList<Node> frequency) {
    Priority<Node> queue = new Priority<>();

    for (Node node : frequency) {
      if (node.getFrequency() > 0) {
        queue.add(new Node(node.getFrequency(), node.getByte()));
      }
    }

    if (queue.size() == 1) {
      queue.add(new Node(1, (byte) 0, null, null));
    }

    while (queue.size() > 1) {
      Node left = queue.poll();
      Node right = queue.poll();

      Node newNode = new Node((left.getFrequency() + right.getFrequency()), null, left, right);

      queue.add(newNode);
    }

    return queue.poll();
  }

  public ArrayList<CharCodeMap> buildCharCodeMap(Node root) {
    ArrayList<CharCodeMap> codeMap = new ArrayList<CharCodeMap>(root.getFrequency());

    getCodes(root, "", codeMap);

    return codeMap;
  }

  public void getCodes(Node node, String code, ArrayList<CharCodeMap> codeMap) {
    if (!node.isLeaf()) {
      getCodes(node.getLeft(), code + "0", codeMap);
      getCodes(node.getRight(), code + "1", codeMap);
    } else {
      codeMap.add(new CharCodeMap(node.getByte(), node.getFrequency(), code));
    }
  }

  public CompressResult getCompressedData(FileManager data, ArrayList<CharCodeMap> charCodeMap, Node root) {
    data.resetReading();
    String ret = "";
    boolean isEndOfFile = false;

    while (!isEndOfFile) {
      byte b = data.readByte();

      for (int i = 0; i < charCodeMap.size(); i++) {
        if (charCodeMap.get(i).byt == b) {
          ret += charCodeMap.get(i).code;
          break;
        }
      }

      isEndOfFile = data.gotToEndOfFile();
    }

    return new CompressResult(ret, root);
  }

  // public String getCompressedData(FileManager data) {
  //   String ret = "";
  //   boolean isEndOfFile = false;

  //   while (!isEndOfFile) {
  //     byte b = data.readByte();

  //     ret += c;

  //     isEndOfFile = data.gotToEndOfFile();
  //   }

  //   return ret;
  // }

  private void compressCodeMap(ArrayList<CharCodeMap> codeMap, FileManager file, int endUsefulBits) {
    for (int i = 0; i < codeMap.size(); i++) {
      CharCodeMap cm = codeMap.get(i);
      file.writeByte(cm.byt);
      file.writeInt(cm.frequency);
      file.writeByte((byte) 0);
    }

    file.writeByte((byte) endUsefulBits);
    file.writeByte((byte) 0);
  }

  // private void decompressMap(FileManager data, Node root) {
  //   Node node = root;

  //   while (true) {
  //     char character = data.readChar();
  //     Character c = null;

  //     if (character == (char) 4) {
  //       break;
  //     }

  //     String code = "";
  //     while (true) {
  //       c = data.readChar();

  //       if (c == (char) 0) {
  //         break;
  //       }

  //       code += c;
  //     }

  //     for (int i = 0; i < code.length(); i++) {
  //       if (code.charAt(i) == '0') {
  //         Node left = node.getLeft();

  //         if (left == null) {
  //           node.setLeft(new Node());
  //         }

  //         node = node.getLeft();

  //         if (i == code.length() - 1) {
  //           node.setCharacter(character);
  //         }
  //       }

  //       if (code.charAt(i) == '1') {
  //         Node right = node.getRight();

  //         if (right == null) {
  //           node.setRight(new Node());
  //         }

  //         node = node.getRight();

  //         if (i == code.length() - 1) {
  //           node.setCharacter(character);
  //         }
  //       }
  //     }

  //     node = root;
  //   }
  // }

  static class CompressResult {
    String compressedData;
    Node root;

    public CompressResult(String compressedData, Node root) {
      this.compressedData = compressedData;
      this.root = root;
    }

    public String getCompressedData() {
      return compressedData;
    }

    public String toString() {
      return compressedData;
    }
  }
}