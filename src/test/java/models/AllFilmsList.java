package models;

import java.io.Serializable;
import java.util.ArrayList;

public class AllFilmsList implements Serializable {
    public int count;
    public String next;
    public String previous;
    public ArrayList<Film> results;

    public boolean hasMore() {
        return (next != null && next.length() != 0);
    }
}
