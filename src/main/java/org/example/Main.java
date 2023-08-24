package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Main {

    public static void main(String[] args) {
        List<String> animalsFromFile = new ArrayList<>();
        List<String> conditionsFromFile = new ArrayList<>();

        Utils.readAnimalParametersAndConditions(animalsFromFile, conditionsFromFile);

        List<Animal> animals = new ArrayList<>();
        List<List<String>> conditions = new ArrayList<>();

        Utils.parseAnimals(animalsFromFile, animals);
        Utils.parseConditions(conditionsFromFile, conditions);

        for (int i = 0; i < conditions.size(); i++) {
            Predicate<Animal> conditionPredicate = Utils.calculateCondition(conditions.get(i));

            long count = animals.stream().filter(conditionPredicate).count();
            System.out.println(conditionsFromFile.get(i) + ": " + count);
        }
    }

}