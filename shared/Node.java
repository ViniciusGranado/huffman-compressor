package shared;
public class Node implements Comparable<Node> {
  private Byte frequency;
  private Byte byt;
  private Node left;
  private Node right;

  public Node () {
    this.frequency = null;
    this.byt = null;
    this.left = null;
    this.right = null;
  }

  public Node (byte byt) {
    this.frequency = null;
    this.byt = byt;
    this.left = null;
    this.right = null;
  }

  public Node (byte frequency, byte byt) {
    this.frequency = frequency;
    this.byt = byt;
    this.left = null;
    this.right = null;
  }

  public Node (byte frequency, byte byt, Node left, Node right) {
    this.frequency = frequency;
    this.byt = byt;
    this.left = left;
    this.right = right;
  }

  public byte getFrequency() {
    return frequency;
  }

  public void setFrequency(byte frequency) {
    this.frequency = frequency;
  }

  public byte getByte() {
    return byt;
  }

  public void setByte(byte byt) {
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

  public void addFrequency() {
    frequency++;
  }

  @Override
  public int compareTo(Node comparedNode) {
    return this.frequency - comparedNode.frequency;
  }
}
