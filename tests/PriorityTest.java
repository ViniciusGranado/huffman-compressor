package tests;
import shared.Priority;

public class PriorityTest {
  public static void main (String[] args) {
    Priority<Integer> testPriority = new Priority<>();

    System.out.println("--------------------------------------------");
    System.out.println("    DEVE ADICIONAR VALORES CORRETAMENTE     ");
    System.out.println("--------------------------------------------");

    testPriority.add(2);
    testPriority.add(5);
    testPriority.add(3);

    System.out.print("RESULTADO: ");
    System.out.print(testPriority.toString().equals("[2, 3, 5]") ? "PASSOU\n\n" : "NAO PASSOU\n\n");

    System.out.println("--------------------------------------------");
    System.out.println("      DEVE REMOVER VALORES CORRETAMENTE     ");
    System.out.println("--------------------------------------------");

    Boolean passou = null;

    testPriority.add(0);
    testPriority.add(7);
    testPriority.add(12);
    testPriority.add(2);

    if (!testPriority.toString().equals("[0, 2, 2, 3, 5, 7, 12]")) passou = false;

    testPriority.poll();

    if (!testPriority.toString().equals("[2, 2, 3, 5, 7, 12]")) passou = false;

    testPriority.poll();

    if (!testPriority.toString().equals("[2, 3, 5, 7, 12]")) passou = false;

    testPriority.poll();

    if (!testPriority.toString().equals("[3, 5, 7, 12]")) passou = false;

    testPriority.poll();

    if (!testPriority.toString().equals("[5, 7, 12]")) passou = false;

    testPriority.poll();

    if (!testPriority.toString().equals("[7, 12]")) passou = false;

    testPriority.poll();

    if (!testPriority.toString().equals("[12]")) passou = false;

    testPriority.poll();

    if (!testPriority.toString().equals("[]")) passou = false;

    System.out.print("RESULTADO: ");
    System.out.print(passou == null ? "PASSOU\n\n" : "NAO PASSOU\n\n");
  }
}
