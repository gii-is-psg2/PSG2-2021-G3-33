package org.springframework.samples.petclinic.util;

import java.util.stream.Collectors;

import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Visit;

public class VisitValidator {


    private VisitValidator(){
        new VisitValidator();
    }
    

    public static boolean validateDate(Pet pet, Visit visit){
        return pet.getVisits().stream().filter(i -> i.getDate().equals(visit.getDate())).collect(Collectors.toList()).size() != 1 ? true: false;
        
    }
}
