package com.testStream;

import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        String testString1 = "11aabb  5d2bcddc";
        String testString2 = "11aabb  5d2bcddc";

        //Stream API

        Stream <String> stringStream1 = testString1.codePoints().mapToObj(c -> String.valueOf((char) c));               //Считываем строку в поток посимвольно, с последуюшим преобразованием каждого символа в строку.
                                                                                                                        //Преобразование необходимо для возможности дальнейших пометок дублей и уникальных символов.

        String result1 = stringStream1.reduce((c1,c2) ->{                                                                // Суть алгоритма это найти первый уникальный символ, дорисовать ему точку
                                                    if (c1.equals(c2) && c1.endsWith("."))  return c1;                   // и затереть им все оставшиеся. Алгоритм отрабатывает за один проход.
                                                        else if (c1.equals(c2) && !c1.endsWith(".")) return c2+"!";      // На выходе мы получаем либо первый уникальный + точка, либо последний дубль + !
                                                            else if (!c1.equals(c2) && c1.endsWith("!")) return c2;
                                                                else if (!c1.equals(c2) && !c1.endsWith(".")) return c1+".";
                                                                    else return c1;
                                                        }).get();                                                        //получаем значение Optional
                                                                                                                         //ОГРАНИЧЕНИЕ - использование ! и . в последовательности.

        if (result1.endsWith(".")) result1 = result1.replace(".","");
        if (result1.endsWith("!")) result1 = "0";

        //Циклы
        String result2 = "0";                                                                                           //Просто в цикле проверяем на схожесть с ближайшими соседями.
        int i = testString2.length();                                                                                   //Если совпадений нет - то завершаем, мы нашли искомое.

        for(int x = 0; x < i; x++)
        {
            if (!testString2.regionMatches(x, testString2, x+1, 1) & x == 0)    result2 = String.valueOf(testString2.charAt(x));
            if (!testString2.regionMatches(x, testString2, x-1, 1) &
                !testString2.regionMatches(x, testString2, x+1, 1) &
                 x != 0 && x < i-1)                                                        result2 = String.valueOf(testString2.charAt(x));
            if (!testString2.regionMatches(x, testString2, x-1, 1) & x == i-1)  result2 = String.valueOf(testString2.charAt(x));
            if (!result2.equals("0")) break;
        }

        System.out.println();
        System.out.println("Результат работы с использованием Stream API");
        System.out.println(testString1 + "-> " + result1);
        System.out.println();
        System.out.println("Результат работы без использования Stream API");
        System.out.println(testString2 + "-> " + result2);
    }

}
