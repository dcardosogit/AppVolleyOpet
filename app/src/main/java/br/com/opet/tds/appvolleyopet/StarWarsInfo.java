package br.com.opet.tds.appvolleyopet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diego on 21/08/2017.
 */

public class StarWarsInfo {
    private String movieTitle;
    private List<Chars> characters;

    public StarWarsInfo(String movieTitle, List<Chars> characters) {
        this.movieTitle = movieTitle;
        this.characters = characters;
    }

    public StarWarsInfo() {
        characters = new ArrayList<>();
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public List<Chars> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Chars> characters) {
        this.characters = characters;
    }

    @Override
    public String toString() {
        return "Filme\n["  +
                "Titulo =" + movieTitle + "\n" +
                "Personagens = " + characters +
                ']';
    }
}
