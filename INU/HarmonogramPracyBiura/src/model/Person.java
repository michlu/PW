package model;

/**
 * @author Michlu
 * @sience 2017-01-07
 */
public class Person {
    private String imie;
    private String nazwisko;
    private String pokoj;
    private String pracujeOd;
    private String pracujeDo;

    public Person(String imie, String nazwisko, String pokoj, String pracujeOd, String pracujeDo) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pokoj = pokoj;
        this.pracujeOd = pracujeOd;
        this.pracujeDo = pracujeDo;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getPokoj() {
        return pokoj;
    }

    public void setPokoj(String pokoj) {
        this.pokoj = pokoj;
    }

    public String getPracujeOd() {
        return pracujeOd;
    }

    public void setPracujeOd(String pracujeOd) {
        this.pracujeOd = pracujeOd;
    }

    public String getPracujeDo() {
        return pracujeDo;
    }

    public void setPracujeDo(String pracujeDo) {
        this.pracujeDo = pracujeDo;
    }

    public int podajCzasPracy(){
        int czasPracy;
        if(Integer.parseInt(pracujeDo)-Integer.parseInt(pracujeOd)<=0){
            czasPracy=0;
        }
        else{
            czasPracy=Integer.parseInt(pracujeDo)-Integer.parseInt(pracujeOd);
        }
        return czasPracy;
    }

    @Override
    public String toString() {
        return "Person{" +
                "imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", pokoj='" + pokoj + '\'' +
                ", pracujeOd='" + pracujeOd + '\'' +
                ", pracujeDo='" + pracujeDo + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true; // Szybkie sprawdzenie, czy obiekty są identyczne.
        // Musi zwrócić false, jeśli parametr jawny ma wartość null.
        // Jeśli klasy nie pasują, nie mogą być równe.
        if (otherObject == null || getClass() != otherObject.getClass()) return false;

        Person person = (Person) otherObject;

        // Sprawdzenie, czy pola mają identyczne wartości.
        // (wyrażenie) ? zwróć_jeżeli_wyrażenie_true : zwróć_jeżeli_wyrażenie_false
        if (imie != null ? !imie.equals(person.imie) : person.imie != null) return false;
        if (nazwisko != null ? !nazwisko.equals(person.nazwisko) : person.nazwisko != null) return false;
        if (pokoj != null ? !pokoj.equals(person.pokoj) : person.pokoj != null) return false;
        if (pracujeOd != null ? !pracujeOd.equals(person.pracujeOd) : person.pracujeOd != null) return false;
        return pracujeDo != null ? pracujeDo.equals(person.pracujeDo) : person.pracujeDo == null;
    }
}
