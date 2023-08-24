import org.example.Animal;
import org.example.Utils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class AnimalTest {

    @Test
    public void calculateConditionTest() {
        List<String> animalsSource = new ArrayList<>();
        animalsSource.add("ЛЕГКОЕ,МАЛЕНЬКОЕ,ВСЕЯДНОЕ");
        animalsSource.add("ТЯЖЕЛОЕ,МАЛЕНЬКОЕ,ТРАВОЯДНОЕ");
        animalsSource.add("ТЯЖЕЛОЕ,НЕВЫСОКОЕ,ТРАВОЯДНОЕ");

        List<String> conditionsSource = new ArrayList<>();
        conditionsSource.add("Сколько животных – всеядных и не высоких");

        List<Animal> animals = new ArrayList<>();
        List<List<String>> conditions = new ArrayList<>();

        Utils.parseAnimals(animalsSource, animals);
        Utils.parseConditions(conditionsSource, conditions);


        Predicate<Animal> conditionPredicate = Utils.calculateCondition(conditions.get(0));

        long count = animals.stream().filter(conditionPredicate).count();

        Assert.assertEquals(1, count);
    }

    @Test
    public void parseConditionTest() {
        List<String> conditionsSource = new ArrayList<>();
        conditionsSource.add("Сколько животных – всеядных и не высоких");

        List<List<String>> conditions = new ArrayList<>();

        Utils.parseConditions(conditionsSource, conditions);

        List<String> res = new ArrayList<>();
        res.add("ВСЕЯ");
        res.add("и");
        res.add("не");
        res.add("ВЫСО");
        Assert.assertEquals(res.toString(), conditions.get(0).toString());
    }

    @Test
    public void parseAnimalsTest() {
        List<String> animalsSource = new ArrayList<>();
        animalsSource.add("ТЯЖЕЛОЕ,НЕВЫСОКОЕ,ТРАВОЯДНОЕ");

        List<Animal> animals = new ArrayList<>();

        Utils.parseAnimals(animalsSource, animals);

        Assert.assertEquals("ТЯЖЕ,НЕВЫ,ТРАВ", animals.get(0).getAllNormalizeParameters());
    }

}
