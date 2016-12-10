import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class zd2BilansNawiasow2 {
	static int nawiasOo = 0;
    static int nawiasOz = 0;
    static int nawiasKo = 0;
    static int nawiasKz = 0;
    static int nawiasKLo = 0;
    static int nawiasKLz = 0;
    
    public static boolean czysiezgadza(){
		boolean czy = false;
		if(nawiasOo==nawiasOz && nawiasKo==nawiasKz && nawiasKLo==nawiasKLz) czy=true;		
	return czy;
	}

	public static void main(String[] args) throws FileNotFoundException {
		FileReader plik = new FileReader("ProstyKlientPogawedek.java");
		Scanner in = new Scanner(plik);
		
	    String line = "";

	    char a = '(';
	    char b = ')';
	    char c = '[';
	    char d = ']';
	    char e = '{';
	    char f = '}';

	    try{
	    while((line = in.next()) !=null){
	    	for (int i = 0; i < line.length(); i++) {
	    		if(line.charAt(i)=='(') nawiasOo++;
	    		if(line.charAt(i)==b) nawiasOz++;
	    		if(line.charAt(i)==d) nawiasKo++;
	    		if(line.charAt(i)==c) nawiasKz++;	
	    		if(line.charAt(i)==e) nawiasKLo++;
	    		if(line.charAt(i)==f) nawiasKLz++;	
			}
	    }
	    }
	    catch(NoSuchElementException e1){	    	
	    }
	    System.out.println("Nawiasów okr¹g³ych otwartych: " + nawiasOo + " Nawiasów okr¹g³ych zamknietych: " + nawiasOz);
		System.out.println("Nawiasów kwadratowych otwartych: " + nawiasKo + " Nawiasów kwadratowych zamknietych: " + nawiasKz);
		System.out.println("Nawiasów klamrowych otwartych: " + nawiasKLo + " Nawiasów klamrowych zamknietych: " + nawiasKLz);
		System.out.println("");
		
		if(czysiezgadza()==true){
			   System.out.println("Bilans nawiasów siê zgadza");
		   }
		   else{
			   System.out.println("Bilans nawiasów siê NIE zgadza");
		   }
	}

}
