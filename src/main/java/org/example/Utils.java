package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Utils {

    public static void readAnimalParametersAndConditions(List<String> animalsFromFile, List<String> conditionsFromFile) {
        try (BufferedReader animalsReader = new BufferedReader(new FileReader("src/main/resources/animals"));
             BufferedReader conditionsReader = new BufferedReader(new FileReader("src/main/resources/conditions")))
        {
            String line1 = animalsReader.readLine();
            while (line1 != null) {
                animalsFromFile.add(line1);
                line1 = animalsReader.readLine();
            }

            String line2 = conditionsReader.readLine();
            while (line2 != null) {
                conditionsFromFile.add(line2);
                line2 = conditionsReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Predicate<Animal> calculateCondition(List<String> currentConditionLine) {
        Predicate<Animal> conditionPredicate = animal -> true;

        for (int j = 0; j < currentConditionLine.size(); j++) {
            String condition = currentConditionLine.get(j);
            if (j == 0 && !condition.equals("не")) {
                conditionPredicate = animal -> animal.getAllNormalizeParameters().contains(condition);
            } else if (j == 0) {
                String nextCond = currentConditionLine.get(j + 1);
                conditionPredicate = Predicate.not(animal -> animal.getAllNormalizeParameters().contains(nextCond));
                j++;
            } else {
                if (condition.equals("и")) {
                    String secondCond = currentConditionLine.get(j + 1);
                    if (secondCond.equals("не")) {
                        String thirdCond = currentConditionLine.get(j + 2);
                        conditionPredicate = conditionPredicate.and(Predicate.not(animal -> animal.getAllNormalizeParameters().contains(thirdCond)));
                        j = j + 2;
                    } else {
                        conditionPredicate = conditionPredicate.and(animal -> animal.getAllNormalizeParameters().contains(secondCond));
                        j++;
                    }
                } else if (condition.equals("или")) {
                    String secondCond = currentConditionLine.get(j + 1);
                    if (secondCond.equals("не")) {
                        String thirdCond = currentConditionLine.get(j + 2);
                        conditionPredicate = conditionPredicate.or(Predicate.not(animal -> animal.getAllNormalizeParameters().contains(thirdCond)));
                        j = j + 2;
                    } else {
                        conditionPredicate = conditionPredicate.or(animal -> animal.getAllNormalizeParameters().contains(secondCond));
                        j++;
                    }
                }
            }
        }

        return conditionPredicate;
    }

    public static void parseAnimals(List<String> animalsSource, List<Animal> animalsResult) {
        for (String str : animalsSource) {
            String[] parameters = str.split(",");
            animalsResult.add(new Animal(parameters[0], parameters[1], parameters[2]));
        }
    }

    public static void parseConditions(List<String> conditionsSource, List<List<String>> conditionsResult) {
        for (int i = 0; i < conditionsSource.size(); i++) {
            conditionsResult.add(new ArrayList<>());
            String[] parameters = conditionsSource.get(i).split(" ");
            for (String parameter : parameters) {
                if (parameter.equals("–")) {
                    conditionsResult.get(i).clear();
                } else {
                    conditionsResult.get(i).add(normalizeConditionParameter(parameter));
                }
            }
        }
    }

    public static String normalizeConditionParameter(String parameter) {
        if (!parameter.equals("и") && !parameter.equals("или") && !parameter.equals("не")) {
            if (parameter.length() > 3) {
                return parameter.substring(0, 4).toUpperCase();
            }
        }
        return parameter;
    }

}
