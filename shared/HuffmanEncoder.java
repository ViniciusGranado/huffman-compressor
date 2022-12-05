package shared;

import java.io.EOFException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

public class HuffmanEncoder {
  Map<Byte, String> charCodeMap2;
  Node root;

  public void compress(FileManager data, String newFilename) {
    Map<Byte, Integer> frequency = getFrequencyMap(data.getBytesArr());
    Priority<Node> priorityQueue = getPriorityQueue(frequency);
    HuffmanTree tree = new HuffmanTree(priorityQueue);
    Map<Byte, String> charCodeMap = buildCharCodeMap(tree.getRoot());
    charCodeMap2 = buildCharCodeMap(tree.getRoot());

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
    Byte endUsefulBits = null;
    try {
      endUsefulBits = data.readByte();
    } catch(Exception e) {
      System.out.println(e);
      System.exit(0);
    }
    
    decompressTree(data);
    Map<Byte, String> charCodeMap = buildCharCodeMap(root);

    FileManager newFile = null;

    try {
      newFile = new FileManager(newFilename, "rw");
    } catch (Exception e) {
      System.out.println(e);
    }

    String compressedData = "";
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

      compressedData += ByteHelper.byteToString(b);
    }

    Node node = root;

    for (int i = 0; i < compressedData.length() - endUsefulBits; i++) {
      if (node.getLeft() == null && node.getRight() == null) {
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
    int endUsefulBits = 8 - (newFileContent.length() % 8);

    newFile.writeByte((byte) endUsefulBits);

    compressTree(tree.getRoot(), newFile);

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

  private void compressTree(Node root, FileManager newFile) {
		if (root != null) {
			if (root.getLeft() == null && root.getRight() == null) {
				newFile.writeByte((byte) 0);
				newFile.writeByte(root.getByte());
			} else {
				newFile.writeByte((byte) 1);
			}
			compressTree(root.getLeft(), newFile);
			compressTree(root.getRight(), newFile);
		}
  }

  public void decompressTree(FileManager compressedFile) {
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

  private void decompressTree(FileManager data, Node root){
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