
public class zd1GenerowanieIPrezentacjaTablic2Wymiarowych {

	public static void main(String[] args) {

        //tworzenie tablicy
		int wysokoscTablicy = 3;
		int szerokoscTablicy = 4;
		
        int tab[][] = new int[wysokoscTablicy][szerokoscTablicy];
        

        //wypelnianie tablicy od 10 do 1
        int listaNr[] = {7, -123, 1, 257, 1234, 555, 3, 15, 4, -15, 0, 0};

        int y = 0;
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                tab[i][j] = listaNr[y++];
            }
        }



        //drukowanie tablicy

        for (int i = 0; i < tab.length; i++) {
            System.out.print("tab[" + i + "] = ");
            for(int j = 0; j < tab[i].length; j++){
                System.out.print(tab[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
        for (int i = 0; i < tab.length; i++) {
            for(int j = 0; j < tab[i].length; j++){
                System.out.printf("%6d", tab[i][j], " ");
            }
            System.out.println("");
        }

    }
}
