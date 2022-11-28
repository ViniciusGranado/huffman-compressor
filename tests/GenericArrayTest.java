package tests;
import shared.GenericArray;

public class GenericArrayTest {
  public static void main (String[] args) {
    GenericArray<Integer> testArray = new GenericArray<>(5);

    System.out.println("--------------------------------------------");
    System.out.println("DEVE INSTANCIAR ARRAY VAZIO COM VALORES NULL");
    System.out.println("--------------------------------------------");

    Boolean passou = true;

    for(int i = 0; i < testArray.size(); i++) {
      if (testArray.get(i) != null) {
        passou = false;
      }
    }

    System.out.print("RESULTADO: ");
    System.out.print(passou ? "PASSOU\n\n" : "NAO PASSOU\n\n");

    System.out.println("--------------------------------------------");
    System.out.println("    DEVE ADICIONAR VALORES CORRETAMENTE     ");
    System.out.println("--------------------------------------------");

    testArray.add(0, 2);

    System.out.print("RESULTADO: ");
    System.out.print(testArray.get(0) == 2 ? "PASSOU\n\n" : "NAO PASSOU\n\n");

    System.out.println("--------------------------------------------");
    System.out.println("        DEVE LIMPAR ARRAY CORRETAMENTE      ");
    System.out.println("--------------------------------------------");

    testArray.clear();

    System.out.print("RESULTADO: ");
    System.out.print(testArray.toString().equals("[null, null, null, null, null]") ? "PASSOU\n\n" : "NAO PASSOU\n\n");

    System.out.println("--------------------------------------------");
    System.out.println("      DEVE REMOVER VALORES CORRETAMENTE     ");
    System.out.println("--------------------------------------------");

    testArray.clear();
    passou = null;

    testArray.add(0, 0);
    testArray.add(1, 1);
    testArray.add(2, 2);
    testArray.add(3, 3);

    testArray.remove(0);

    if (!testArray.toString().equals("[1, 2, 3, null, null]")) passou = false;

    testArray.remove(1);

    if (!testArray.toString().equals("[1, 3, null, null, null]")) passou = false;

    testArray.remove(0);

    if (!testArray.toString().equals("[3, null, null, null, null]")) passou = false;

    testArray.remove(0);

    if (!testArray.toString().equals("[null, null, null, null, null]")) passou = false;

    System.out.print("RESULTADO: ");
    System.out.print(passou == null ? "PASSOU\n\n" : "NAO PASSOU\n\n");
  }
}
