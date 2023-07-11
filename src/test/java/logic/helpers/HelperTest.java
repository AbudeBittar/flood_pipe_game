package logic.helpers;


import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class HelperTest {



    @Test
    public void TestGetRandomNumber() {

        ArrayList<Integer> integers = new ArrayList<>();

        for (int i = 0; i < 1000; i++){
            integers.add(Helper.generateRandomNo(1,3));
        }

        boolean expected1 = integers.stream().filter(integer -> integer > 3 || integer < 1).toList().size() == 0;
        boolean expected2 = integers.stream().filter(integer -> integer <= 3 || integer >= 1).toList().size() == 1000;
        boolean expected3 = integers.stream().filter(integer -> integer == 3).toList().size() > 0;
        boolean expected4 = integers.stream().filter(integer -> integer == 2).toList().size() > 0;
        boolean expected5 = integers.stream().filter(integer -> integer == 1).toList().size() > 0;


        assertTrue(expected1);
        assertTrue(expected2);
        assertTrue(expected3);
        assertTrue(expected4);
        assertTrue(expected5);

    }
}
