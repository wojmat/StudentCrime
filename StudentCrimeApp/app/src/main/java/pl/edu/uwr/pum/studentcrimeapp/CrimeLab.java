package pl.edu.uwr.pum.studentcrimeapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {

    private static CrimeLab crimeLab;
    private List<Crime> crimes;


    public static CrimeLab get(){
        if(crimeLab == null)
            crimeLab = new CrimeLab();
        return  crimeLab;
    }

    private CrimeLab(){
        crimes = new ArrayList<>();
        for(int i = 1; i < 21; i++){
            Crime crime = new Crime();
            crime.setTitle("Crime: " + i);
            crime.setSolved(i % 2 == 0);
            crimes.add(crime);
        }
    }

    public void addCrime(Crime c) {
        crimes.add(c);
    }

    public List<Crime> getCrimes() {
        return crimes;
    }

    public Crime getCrime(UUID id) {
        for (Crime crime : crimes) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }
        return null;
    }

    public void removeCrime(Crime crime){
        crimes.remove(crime);
    }
}
