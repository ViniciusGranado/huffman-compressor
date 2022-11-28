package shared;

public class HuffmanEncoder {
  public CompressResult compress (String data) {
    int[] frequency = getFrequencyArray(data);
    Node root = geHuffmanTree(frequency);
    GenericArray<CharCodeMap> charCodeMap = buildCharCodeMap(root);

    CompressResult compressedData = getCompressedData(data, charCodeMap, root);
    return compressedData;
  }

  public String decompress(CompressResult compressResult) {
    return null;
  }

  public static int[] getFrequencyArray(String data) {
    int[] frequency = new int[256]; // Valor 256 pois é o número de caracteres possíveos na tabela ASCII

    for (char c : data.toCharArray()) {
      frequency[c]++;
    }

    return frequency;
  }  

  public static Node geHuffmanTree(int[] frequency) {
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

  public CompressResult getCompressedData(String data, GenericArray<CharCodeMap> charCodeMap, Node root) {
    String ret = "";

    for (char character : data.toCharArray()) {
      for (int i = 0; i < charCodeMap.size(); i++) {
        if (charCodeMap.get(i).character == character) {
          ret += charCodeMap.get(i).code;
          break;
        }
      }
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