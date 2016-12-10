import java.math.BigInteger;
import java.util.Scanner;

public class zd3WielkieLiczby {

	public static long silnia(int n){
		long s = 1;
		for (int i = 1; i <= n; i++) {
			s = s*i;
		}
		return s;
	}
	
	public static BigInteger silniaBig(int m){
		BigInteger s = BigInteger.valueOf(1);
		for (int i = 1; i <= m; i++) {
			s = s.multiply(BigInteger.valueOf(i));
		}
		return s;
	}
	
	
	public static void main(String[] args) {
		
		Scanner skaner = new Scanner(System.in);
		System.out.println("Podaj dodatnia liczbe ca³kowit¹ (poni¿ej 20): ");
		int n = skaner.nextInt();
		System.out.printf("Silania z %d wynosi %,d", n, silnia(n));
		
		System.out.println("\nPodaj dodatnia liczbe ca³kowit¹ (wiecej ni¿ 20): ");
		int m = skaner.nextInt();
		System.out.printf("Silania z %d wynosi %,d", m, silniaBig(m));

	}
}
