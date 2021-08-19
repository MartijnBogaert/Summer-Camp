package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.Camp;

public class SummerCampServiceImpl implements CampService {
	private Map<Integer, Camp> mapSummerCamp = new HashMap<>();

    // without database!
    public SummerCampServiceImpl()
    {
        mapSummerCamp.put(1, new Camp(1, "Christof", 8400, 2));
        mapSummerCamp.put(2, new Camp(2, "Dean", 8430, 1));
        mapSummerCamp.put(3, new Camp(3, "Joke", 8620 , 3));
        mapSummerCamp.put(4, new Camp(4, "Thisbe", 5500, 4));
        mapSummerCamp.put(5, new Camp(5, "Ann", 5580, 2));
        mapSummerCamp.put(6, new Camp(6, "Anton", 5530, 1));
        mapSummerCamp.put(7, new Camp(7, "Ruben", 5560, 3));
    }

    //without database!
    @Override
    public Camp findCamp(int id)
    {
        return mapSummerCamp.get(id);
    }

    //without database!
    @Override
    public List<Camp> findCamps(int postalCode) {
        switch (postalCode/1000) {
            case 8:
                Camp[] c = { mapSummerCamp.get(1), mapSummerCamp.get(2), mapSummerCamp.get(3)};
                return new ArrayList<>(Arrays.asList(c));
            case 5:
                Camp[] c1 = { mapSummerCamp.get(4), mapSummerCamp.get(5), mapSummerCamp.get(6), mapSummerCamp.get(7)};
                return new ArrayList<>(Arrays.asList(c1));
            default:
                return null;
        }
    }
}