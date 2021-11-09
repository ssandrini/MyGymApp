package ar.edu.itba.mygymapp.backend.store;

import java.util.ArrayList;

import ar.edu.itba.mygymapp.backend.models.Category;
import ar.edu.itba.mygymapp.backend.models.Routine;
import ar.edu.itba.mygymapp.backend.models.User;

public class RoutineStore {
    private ArrayList<Routine> routines;

//    public RoutineStore() {
//        routines.add(new Routine(0,"Calistenia", "Rutina de calistenia", 0.0,true,"Rookie", new User(0,"Santi","Indefinido", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.tsensor.online%2Ftestimonial%2Fagrosuperjorge-ruz-cordero-quality-assurance-specialist-supply-chain%2Favatar-icon-png-105-images-in-collection-page-3-avatarpng-512_512%2F&psig=AOvVaw2UH3Q2hTJ8-AEOAfVqv61t&ust=1636147412468000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCKj9ip3S__MCFQAAAAAdAAAAABAD"),new Category(0,"Calistenia", "La mejor categoría"),null, false));
//    }

    public ArrayList<Routine> getRoutines() {
        return routines;
    }
}
