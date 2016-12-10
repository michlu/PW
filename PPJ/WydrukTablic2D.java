
import java.util.Random;
import java.util.Scanner;

public class WydrukTablic2D {  

  public static void main(String[] args) {
    Scanner scn = new Scanner(System.in);
    Random rnd = new Random();
    
    System.out.println("Druk tablic z wyrównaniem w kolumnach\n");
    while(true) { // Konwersacja
      System.out.print("\nLiczba wierszy: m = ");
      int m = scn.nextInt();
      
      if(m<=0) break;
      
      int[][] T = new int[m][];
      
      // Szerokoœci wierszy
      System.out.println("Szerokoœci wierszy (" + m + " liczb dodatnich)");
      for(int i=0; i<m; ++i)
        T[i] = new int[scn.nextInt()];
      
      // Generuj treœæ tablicy
      System.out.print("Zakres generacji (-z..z): z = ");
      int z = scn.nextInt();
      
      for(int i=0; i<m; ++i)
        for(int j=0; j<T[i].length; ++j)
          T[i][j] = rnd.nextInt(2*z)-z;
      
      for(int i=0; i<m; ++i) {
        for(int j=0; j<T[i].length; ++j)
          System.out.print(T[i][j] + " ");
        System.out.println();
      }
      
      System.out.println("\nWydruk z wyrównaniem kolumn");
      nicePrint(T);
    }
    scn.close();
    System.out.println("\nKONIEC PROGRAMU");
  } // main()
  
  static void nicePrint(int[][] T) {
    int m = T.length; // Liczba wierszy
    int maxcol = 0;   // Maksymalna liczba kolumn w wierszach
    for(int i=0; i<m; ++i)
      if(T[i].length > maxcol)
        maxcol = T[i].length;
    
    int[] kolumny = new int[maxcol];
    int totalwidth = 0;
    for(int j=0; j<maxcol; ++j) {
      int wid = 0;
      for(int i=0; i<m; ++i) {
        if(T[i].length>j) {
          int width = (""+T[i][j]).length();
          if(width>wid) wid = width;
        }
      }
      kolumny[j] = wid;
      totalwidth += wid;     
    }
    totalwidth += maxcol-1; // Uwzglêdnij odstêpy miêdzy kolumnami
    
    // Wydruk
    
    // Nag³ówek z numerami kolumn
    System.out.print("    ");
    for(int j=0; j<maxcol; ++j)
      System.out.printf("%" + kolumny[j] + "d ", j);
    System.out.println();
    
    String separator = new String(new char[totalwidth]).replace('\0', '-');
    System.out.println("    "+separator);
    
    for(int i=0; i<m; ++i) {
      System.out.printf("%2d: ", i);     // Numer wiersza
      for(int j=0; j<T[i].length; ++j)
        System.out.printf("%" + kolumny[j] + "d ", T[i][j]);
      System.out.println();
    }   
  }
}
