package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        Map<LocalDate, Integer> sumDayCaloriese = new HashMap<>();
        for (UserMeal m: meals
             ) {
//            sumDayCaloriese.merge(m.getDateTime().toLocalDate(), m.getCalories(), (c1, c2) -> c1 + c2);
            sumDayCaloriese.merge(m.getDateTime().toLocalDate(), m.getCalories(), Integer::sum);
        }
        List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();
        for (UserMeal x: meals
             ) {
            LocalDateTime localDateTime = x.getDateTime();
            if (TimeUtil.isBetweenHalfOpen(localDateTime.toLocalTime(), startTime, endTime)) {
                userMealWithExcessList.add(new UserMealWithExcess(localDateTime, x.getDescription(), x.getCalories(),
                        sumDayCaloriese.get(localDateTime.toLocalDate()) > caloriesPerDay));
            }
        }

        return userMealWithExcessList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
//        List<UserMealWithExcess> userMealWithExcessList =
//        int sumDayCaloriese = meals.stream().reduce(0, Integer::sum);
        List<UserMeal> userMealList =
//        IntSummaryStatistics calSummary =
        meals
                .stream()
                .filter(p -> TimeUtil.isBetweenHalfOpen(p.getDateTime().toLocalTime(), startTime, endTime))
//                .forEach(x -> System.out.println(x.toString()));
                .collect(Collectors.toCollection(ArrayList::new));
//        System.out.println(calSummary);
        return null;
    }
}
