

import java.util.HashMap;
import java.util.Map;

public class Compressor {
  public static void compress(FileManager data, String newFilename) {
    Map<Byte, Integer> frequency = getFrequencyMap(data.getBytesArr());
    Priority<Node> priorityQueue = getPriorityQueue(frequency);
    HuffmanTree tree = new HuffmanTree(priorityQueue);
    Map<Byte, String> charCodeMap = buildCharCodeMap(tree.getRoot());

    String compressedData = getCompressedData(data, charCodeMap);

    FileManager compressedFile = null;

    try {
      compressedFile = new FileManager(newFilename + ".edd", "rw");
    } catch (Exception e) {
      System.out.println(e);
      System.exit(0);
    }

    generateCompressedFile(tree, compressedData, compressedFile);
  }

  private static Map<Byte, Integer> getFrequencyMap(byte[] byteArr) {
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

  private static Priority<Node> getPriorityQueue(Map<Byte, Integer> frequencyMap) {
    Priority<Node> queue = new Priority<>();

    for (Map.Entry<Byte, Integer> frequency : frequencyMap.entrySet()) {
      if (frequency.getValue() > 0) {
        queue.add(new Node(frequency.getValue(), frequency.getKey()));
      }
    }

    return queue;
  }

  private static Map<Byte, String> buildCharCodeMap(Node root) {
    Map<Byte, String> codeMap = new HashMap<Byte, String>();

    getCodes(root, "", codeMap);

    return codeMap;
  }

  private static void getCodes(Node node, String code, Map<Byte, String> codeMap) {
    if (!node.isLeaf()) {
      getCodes(node.getLeft(), code + "0", codeMap);
      getCodes(node.getRight(), code + "1", codeMap);
    } else {
      codeMap.put(node.getByte(), code);
    }
  }

  private static String getCompressedData(FileManager data, Map<Byte, String> charCodeMap) {
    String ret = "";

    for (byte b : data.getBytesArr()) {
      String code = charCodeMap.get(b);
      ret += code;
    }

    return ret;
  }

  private static void generateCompressedFile(HuffmanTree tree, String newFileContent, FileManager newFile) {
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

  private static void compressTree(Node root, FileManager newFile) {
		if (root != null) {
			if (root.isLeaf()) {
				newFile.writeByte((byte) 0);
				newFile.writeByte(root.getByte());
			} else {
				newFile.writeByte((byte) 1);
			}
			compressTree(root.getLeft(), newFile);
			compressTree(root.getRight(), newFile);
		}
  }
}
