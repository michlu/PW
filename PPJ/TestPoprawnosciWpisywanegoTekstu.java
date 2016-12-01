package inne;

import java.util.Random;
import java.util.Scanner;
/**
 * Created by Michał Nowiński on 2016-10-16.
 */
public class TestPoprawnosciWpisywanegoTekstu {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();
        long t1, t2;

        String w1, w2, w3, w4, w5, w6, w7, w8, w9, w10;
        // Pan Tadeusz - Ksiega VIII - Zajazd
        w1 = "Zaiste, okolica była malownicza";
        w2 = "Dwa stawy pochyliły ku sobie oblicza";
        w3 = "Jako para kochanków; prawy staw miał wody";
        w4 = "Gładkie i czyste jako dziewicze jagody";
        w5 = "Lewy ciemniejszy nieco, jako twarz młodziana";
        w6 = "Smagława, i już męskim puchem osypana";
        w7 = "Prawy złocistym piaskiem połyskał się wkoło";
        w8 = "Jak gdyby włosem jasnym; a lewego czoło";
        w9 = "Najeżone łozami, wierzbami czubate";
        w10 = "Oba stawy ubrane w zieloności szatę";
        String[] tabelaWierszy = {w1, w2, w3, w4, w5, w6, w7, w8, w9, w10};

        System.out.println("Wprowadź tekst poniżej starając się to zrobić jak najszybciej:");
        while (true){
            int randN = rand.nextInt(tabelaWierszy.length); // liczba losowa

            System.out.println(tabelaWierszy[randN]); // wyswietla losowy wiersz
            t1 = System.currentTimeMillis();
            String wprowadzonyText = sc.nextLine(); // wprowadzanie tekstu
            t2 = System.currentTimeMillis();

            if(wprowadzonyText.equals("")){
                System.out.println("Koniec programu!");
                break;
            }
            else if (wprowadzonyText.equals(tabelaWierszy[randN])) {
                System.out.println("OK, czas wprowadzania: " + (t2-t1) + " [ms]");
            }
            else {
                System.out.println("ER, czas wprowadzania: " + (t2-t1) + " [ms]");
            }
            System.out.println("");
        }
        sc.close();
    }
}
