package shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HuffmanEncoder {
  public void compress(FileManager data, String newFilename) {
    Map<Byte, Integer> frequency = getFrequencyMap(data.getBytesArr());
    Priority<Node> priorityQueue = getPriorityQueue(frequency);
    HuffmanTree tree = new HuffmanTree(priorityQueue);
    Map<Byte, String> charCodeMap = buildCharCodeMap(tree.getRoot());

    CompressResult compressedData = getCompressedData(data, charCodeMap, tree.getRoot());

    FileManager compressedFile = null;

    try {
      compressedFile = new FileManager(newFilename + ".edd", "rw");
    } catch (Exception e) {
      System.out.println(e);
      System.exit(0);
    }

    String newFileContent = compressedData.getCompressedData();

    generateCompressedFile(tree, newFileContent, compressedFile);
  }

  public void decompress(FileManager data, String newFilename) {
    Node root = new Node();
    byte endUsefulBits = 0;
    
    try {
      endUsefulBits = decompressMap(data, root);
    } catch(Exception e) {
      System.out.println(e);
      System.exit(0);
    }

    // String compressedData = getCompressedData(data);
    // String decompressedString = "";
    // Node node = root;

    // FileManager decompressedFile = null;

    // try {
    //   decompressedFile = new FileManager(newFilename, "w");
    // } catch (Exception e) {
    //   System.out.println(e);
    //   System.exit(0);
    // }

    // for (int i = 0; i < compressedData.length();) {
    //   while (!node.isLeaf()) {
    //     char character = compressedData.charAt(i);

    //     if (character == '0') {
    //       node = node.getLeft();
    //     } else {
    //       node = node.getRight();
    //     }

    //     i++;
    //   }

    //   decompressedFile.writeByte(node.getByte());
    //   node = root;
    // }

    // for (char c : decompressedString.toCharArray()) {
    //   decompressedFile.writeChar(c);
    // }
  }

  public static Map<Byte, Integer> getFrequencyMap(byte[] byteArr) {
    Map<Byte, Integer> frequencyMap = new HashMap<>();

    for (byte byt : byteArr) {
      if (frequencyMap.containsKey(byt)) {
        frequencyMap.put(byt, frequencyMap.get(byt) + 1);
        continue;
      }

      frequencyMap.put(byt, 1);
    }

    return frequencyMap;
  }

  private Priority<Node> getPriorityQueue(Map<Byte, Integer> frequencyMap) {
    Priority<Node> queue = new Priority<>();

    for (Map.Entry<Byte, Integer> frequency : frequencyMap.entrySet()) {
      if (frequency.getValue() > 0) {
        queue.add(new Node(frequency.getValue(), frequency.getKey()));
      }
    }

    return queue;
  }

  private Map<Byte, String> buildCharCodeMap(Node root) {
    Map<Byte, String> codeMap = new HashMap<Byte, String>();

    getCodes(root, "", codeMap);

    return codeMap;
  }

  private void getCodes(Node node, String code, Map<Byte, String> codeMap) {
    if (!node.isLeaf()) {
      getCodes(node.getLeft(), code + "0", codeMap);
      getCodes(node.getRight(), code + "1", codeMap);
    } else {
      codeMap.put(node.getByte(), code);
    }
  }

  private CompressResult getCompressedData(FileManager data, Map<Byte, String> charCodeMap, Node root) {
    String ret = "";

    for (byte b : data.getBytesArr()) {
      String code = charCodeMap.get(b);
      ret += code;
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

  private void generateCompressedFile(HuffmanTree tree, String newFileContent, FileManager newFile) {
    int endUsefulBits = newFileContent.length() % 8;

    newFile.writeByte((byte) endUsefulBits);

    StringBuilder stringTree = new StringBuilder();
    treeToString(tree.getRoot(), stringTree);

		for (char c : stringTree.toString().toCharArray()) {
			if (c == '0') {
				newFile.writeByte((byte) 0);
			} else if (c == '1') {
				newFile.writeByte((byte) 1);
			} else {
				newFile.writeChar(c);
			}
		}

    StringBuilder newFileContentSb = new StringBuilder(newFileContent);
    
    for (int i = 0; i < endUsefulBits; i++) {
      newFileContentSb.append('0');
    }

    newFileContent = newFileContentSb.toString();
    String aux = "";

    for (char c : newFileContent.toCharArray()) {
      aux += c;

      if (aux.length() == 8) {
        newFile.writeByte(ByteHelper.stringToByte(aux));
        aux = "";
      }
    }
  }

  private void treeToString(Node root, StringBuilder stringTree) {
		if (root != null) {
			if (root.getLeft() == null && root.getRight() == null) {
				stringTree.append(0);
				stringTree.append(root.getByte());
			} else {
				stringTree.append(1);
			}
			treeToString(root.getLeft(), stringTree);
			treeToString(root.getRight(), stringTree);
		}
  }

  private byte decompressMap(FileManager data, Node root) throws Exception {
    ArrayList<Node> frequencyArr = new ArrayList<>();

    while (true) {
      Byte byt = data.readByte();
      int frequency = data.readInt();

      frequencyArr.add(new Node(frequency, byt));

      char c = data.readCharWithoutChanginPosition();

      if (c == (char) 0) {
        data.readChar();
        break;
      }
    }

    char c = data.readChar();

    if (c != (char) 0) {
      throw new Exception("Arquivo compactado incorretamente");
    }

    byte endUsefulBits = data.readByte();

    c = data.readChar();

    if (c != (char) 0) {
      throw new Exception("Arquivo compactado incorretamente");
    }

    return endUsefulBits;
  }

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