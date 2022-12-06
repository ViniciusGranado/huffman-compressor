import java.util.Objects;

public class Node implements Comparable<Node>, Cloneable {
  private Integer frequency;
  private Byte byt;
  private Node left;
  private Node right;

  public Node () {
    this.frequency = null;
    this.byt = null;
    this.left = null;
    this.right = null;
  }

  public Node (Byte byt) {
    this.frequency = null;
    this.byt = byt;
    this.left = null;
    this.right = null;
  }

  public Node (Integer frequency, Byte byt) {
    this.frequency = frequency;
    this.byt = byt;
    this.left = null;
    this.right = null;
  }

  public Node (Integer frequency, Byte byt, Node left, Node right) {
    this.frequency = frequency;
    this.byt = byt;
    this.left = left;
    this.right = right;
  }

  public Integer getFrequency() {
    return frequency;
  }

  public void setFrequency(Integer frequency) {
    this.frequency = frequency;
  }

  public Byte getByte() {
    return byt;
  }

  public void setByte(Byte byt) {
    this.byt = byt;
  }

  public Node getLeft() {
    return left;
  }

  public void setLeft(Node left) {
    this.left = left;
  }

  public Node getRight() {
    return right;
  }

  public void setRight(Node right) {
    this.right = right;
  }

  public boolean isLeaf() {
    return (left == null && right == null);
  }

  @Override
  public int compareTo(Node comparedNode) {
    return this.frequency - comparedNode.frequency;
  }

  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;
    if (!super.equals(object)) return false;
    Node node = (Node) object;
    return java.util.Objects.equals(frequency, node.frequency)
            && java.util.Objects.equals(byt, node.byt)
            && java.util.Objects.equals(left, node.left)
            && java.util.Objects.equals(right, node.right);
  }

  public Node clone() {
    Node node = new Node(frequency, byt);

    if (node.getLeft() != null) {
      node.setLeft(this.getLeft().clone());
    }

    if (node.getRight() != null) {
      node.setRight(this.getRight().clone());
    }

    return node;
  }

  public int hashCode() {
    return Objects.hash(super.hashCode(), frequency, byt, left, right);
  }

  @Override
  public java.lang.String toString() {
    return "Node{" +
            "frequency=" + frequency +
            ", byt=" + byt +
            ", left=" + left +
            ", right=" + right +
            '}';
  }
}
