package ar.edu.itba.mygymapp.backend.store;

import java.util.ArrayList;

import ar.edu.itba.mygymapp.backend.models.Routine;

public class RoutineStore {
    private static ArrayList<Routine> routines;

    public static void setRoutines(ArrayList<Routine> routines) { RoutineStore.routines = routines;
    }

    public static ArrayList<Routine> getRoutines() {
        return RoutineStore.routines;
    }
}
