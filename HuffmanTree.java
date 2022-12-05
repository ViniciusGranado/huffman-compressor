import java.util.Objects;

public class HuffmanTree implements Cloneable {
  Node root;

  public HuffmanTree(Priority<Node> queue) {
    if (queue.size() == 1) {
      queue.add(new Node(1, (byte) 0, null, null));
    }

    while (queue.size() > 1) {
      Node left = queue.poll();
      Node right = queue.poll();

      Node newNode = new Node((left.getFrequency() + right.getFrequency()), null, left, right);

      queue.add(newNode);
    }

    root = queue.poll();
  }

  public HuffmanTree(Node root) {
    this.root = root;
  }

  public Node getRoot() {
    return root;
  }

  public void setRoot(Node root) {
    this.root = root;
  }

  @java.lang.Override
  public java.lang.String toString() {
    return "HuffmanTree{" +
            "root=" + root +
            '}';
  }

  public HuffmanTree clone() {
    Node node = new Node();

    node = root.clone();

    return new HuffmanTree(node);
  }

  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    if (!super.equals(object)) return false;
    HuffmanTree that = (HuffmanTree) object;
    return java.util.Objects.equals(root, that.root);
  }

  public int hashCode() {
    return Objects.hash(super.hashCode(), root);
  }
}
