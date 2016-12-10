import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class zd2BilansNawiasow {
	static int nawiasOo = 0;
    static int nawiasOz = 0;
    static int nawiasKo = 0;
    static int nawiasKz = 0;
    static int nawiasKLo = 0;
    static int nawiasKLz = 0;
	
	public static int liczbanawiasow(String napis, char znak){
		int ilosc_nawiasow = 0;
		char znakZnapisu;
		for (int i = 0; i < napis.length(); i++) {
			znakZnapisu = napis.charAt(i);
			if(znakZnapisu == znak){
				ilosc_nawiasow++;
			}
		}
		return ilosc_nawiasow;
	}
	
	public static boolean czysiezgadza(){
		boolean czy = false;
		if(nawiasOo==nawiasOz){
			if(nawiasKo==nawiasKz){
				if(nawiasKLo==nawiasKLz){
					czy=true;
				}
			}
		}
	return czy;
	}

	public static void main(String[] args) throws FileNotFoundException {
		FileReader plik = new FileReader("ProstyKlientPogawedek.java");
		Scanner in = new Scanner(plik);
		
	    String line = "";
	    
	    try{
	    	while((line = in.next()) !=null){
				System.out.println(line);
				
				nawiasOo = nawiasOo + zd2BilansNawiasow.liczbanawiasow(line, '(');
				nawiasOz = nawiasOz + zd2BilansNawiasow.liczbanawiasow(line, ')');
				
				nawiasKo = nawiasKo + zd2BilansNawiasow.liczbanawiasow(line, '[');
				nawiasKz = nawiasKz + zd2BilansNawiasow.liczbanawiasow(line, ']');
				
				nawiasKLo = nawiasKLo + zd2BilansNawiasow.liczbanawiasow(line, '{');
				nawiasKLz = nawiasKLz + zd2BilansNawiasow.liczbanawiasow(line, '}');
				
			}
	    }
	    catch(NoSuchElementException e){	    	
	    }
	    
	    
	   System.out.println("Nawiasów okr¹g³ych otwartych: " + nawiasOo + " Nawiasów okr¹g³ych zamknietych: " + nawiasOz);
	   System.out.println("Nawiasów kwadratowych otwartych: " + nawiasKo + " Nawiasów kwadratowych zamknietych: " + nawiasKz);
	   System.out.println("Nawiasów klamrowych otwartych: " + nawiasKLo + " Nawiasów klamrowych zamknietych: " + nawiasKLz);
	   System.out.println("");
	   
	   if(czysiezgadza()==true){
		   System.out.println("Bilans nawasów siê zgadza");
	   }
	   else{
		   System.out.println("Bilans nawasów siê NIE zgadza");
	   }

}
}
