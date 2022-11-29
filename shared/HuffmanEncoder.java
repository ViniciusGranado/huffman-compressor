package shared;

public class HuffmanEncoder {
  public CompressResult compress (FileManager data) {
    int[] frequency = getFrequencyArray(data);
    Node root = getHuffmanTree(frequency);
    GenericArray<CharCodeMap> charCodeMap = buildCharCodeMap(root);

    CompressResult compressedData = getCompressedData(data, charCodeMap, root);
    return compressedData;
  }

  public String decompress(CompressResult compressResult) {
    Node root = compressResult.root;
    String compressedData = compressResult.compressedData;
    String ret = "";

    Node node = root;

    for (int i = 0; i < compressedData.length();) {
      while (!node.isLeaf()) {
        char character = compressedData.charAt(i);

        if (character == '0') {
          node = node.getLeft();
        } else {
          node = node.getRight();
        }

        i++;
      }

      ret += (char) node.getCharacter();
      node = root;
    }

    return ret;
  }

  public static int[] getFrequencyArray(FileManager data) {
    int[] frequency = new int[256]; // Valor 256 pois é o número de caracteres possíveos na tabela ASCII
    boolean isEndOfFile = false;

    while (!isEndOfFile) {
      char c = data.readChar();
      frequency[c]++;

      isEndOfFile = data.gotToEndOfFile();
    }


    return frequency;
  }  

  public static Node getHuffmanTree(int[] frequency) {
    Priority<Node> queue = new Priority<>();

    for (char i = 0; i < 256; i++) {
      if (frequency[i] > 0) {
        queue.add(new Node(frequency[i], i));
      }
    }

    if (queue.size() == 1) {
      queue.add(new Node(1, '\0', null, null));
    }

    while(queue.size() > 1) {
      Node left = queue.poll();
      Node right = queue.poll();

      Node newNode = new Node((left.getFrequency() + right.getFrequency()), '\0', left, right);

      queue.add(newNode);
    }

    return queue.poll();
  }

  public GenericArray<CharCodeMap> buildCharCodeMap(Node root) {
    GenericArray<CharCodeMap> codeMap = new GenericArray<CharCodeMap>(root.getFrequency());

    getCodes(root, "", codeMap);

    return codeMap;
  }

  public void getCodes(Node node, String code, GenericArray<CharCodeMap> codeMap) {
    if (!node.isLeaf()) {
      getCodes(node.getLeft(), code + "0", codeMap);
      getCodes(node.getRight(), code + "1", codeMap);
    } else {
      codeMap.push(new CharCodeMap((char) node.getCharacter(), code));
    }
  }

  public CompressResult getCompressedData(FileManager data, GenericArray<CharCodeMap> charCodeMap, Node root) {
    data.resetReading();
    String ret = "";
    boolean isEndOfFile = false;

    while (!isEndOfFile) {
      char c = data.readChar();
      
      for (int i = 0; i < charCodeMap.size(); i++) {
        if (charCodeMap.get(i).character == c) {
          ret += charCodeMap.get(i).code;
          break;
        }
      }

      isEndOfFile = data.gotToEndOfFile();
    }

    return new CompressResult(ret, root);
  }

  static class CompressResult {
    String compressedData;
    Node root;

    public CompressResult(String compressedData, Node root) {
      this.compressedData = compressedData;
      this.root = root;
    }

    public String toString() {
      return compressedData;
    }
  }
}