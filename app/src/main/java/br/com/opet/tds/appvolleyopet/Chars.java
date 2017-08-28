package br.com.opet.tds.appvolleyopet;

/**
 * Created by Diego on 21/08/2017.
 */

public class Chars {
    private String name;
    private String planet;

    public Chars(String name, String planet) {
        this.name = name;
        this.planet = planet;
    }

    public Chars() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    @Override
    public String toString() {
        return "\nPersonagem:\n[" +
                "Nome = " + name + " , " +
                "Planeta = " + planet +
                "]\n";
    }
}
