package shared;

public class HuffmanEncoder {
  public void compress(FileManager data, String newFilename) {
    int[] frequency = getFrequencyArray(data);
    Node root = getHuffmanTree(frequency);
    GenericArray<CharCodeMap> charCodeMap = buildCharCodeMap(root);

    CompressResult compressedData = getCompressedData(data, charCodeMap, root);

    FileManager compressedFile = null;

    try {
      compressedFile = new FileManager(newFilename + ".edd", "rw");
    } catch (Exception e) {
      System.out.println(e);
      System.exit(0);
    }

    StringBuilder stringBuilder = new StringBuilder();
    compressCodeMap(charCodeMap, stringBuilder);
    String newFileContent = stringBuilder.toString() + compressedData.getCompressedData();
    compressedFile.generateCompressedFile(newFileContent);
  }

  public void decompress(FileManager data, String newFilename) {
    Node root = new Node();
    decompressMap(data, root);

    String compressedData = getCompressedData(data);
    String decompressedString = "";
    Node node = root;

    FileManager decompressedFile = null;

    try {
      decompressedFile = new FileManager(newFilename, "rw");
    } catch (Exception e) {
      System.out.println(e);
      System.exit(0);
    }

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

      decompressedFile.writeChar((char) node.getCharacter());
      node = root;
    }

    for (char c : decompressedString.toCharArray()) {
      decompressedFile.writeChar(c);
    }
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

    while (queue.size() > 1) {
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

  public String getCompressedData(FileManager data) {
    String ret = "";
    boolean isEndOfFile = false;

    while (!isEndOfFile) {
      char c = data.readChar();

      ret += c;

      isEndOfFile = data.gotToEndOfFile();
    }

    return ret;
  }

  private void compressCodeMap(GenericArray<CharCodeMap> codeMap, StringBuilder stringBuilder) {
    for (int i = 0; i < codeMap.size(); i++) {
      CharCodeMap cm = codeMap.get(i);
      stringBuilder.append(cm.character);
      stringBuilder.append(cm.code);
      stringBuilder.append((char) 0);
    }
    stringBuilder.append((char) 4);
  }

  private void decompressMap(FileManager data, Node root) {
    Node node = root;

    while (true) {
      char character = data.readChar();
      Character c = null;

      if (character == (char) 4) {
        break;
      }

      String code = "";
      while (true) {
        c = data.readChar();

        if (c == (char) 0) {
          break;
        }

        code += c;
      }

      for (int i = 0; i < code.length(); i++) {
        if (code.charAt(i) == '0') {
          Node left = node.getLeft();

          if (left == null) {
            node.setLeft(new Node());
          }
          
          node = node.getLeft();

          if (i == code.length() - 1) {
            node.setCharacter(character);
          }
        }

        if (code.charAt(i) == '1') {
          Node right = node.getRight();

          if (right == null) {
            node.setRight(new Node());
          }
          
          node = node.getRight();

          if (i == code.length() - 1) {
            node.setCharacter(character);
          }
        }
      }
      
      node = root;
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