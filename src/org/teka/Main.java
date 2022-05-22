package org.teka;

import java.util.Scanner;

public class Main {

    private static final char[] operators = {'+', '-', '*', '/'};
    private static final int[] values =       {100, 90, 50, 40,  10,  9,   5,   4,  1 };
    private static final String[] romanNums = {"C","XC","L","XL","X","IX","V","IV","I"};
    private static final String[] excMessages = {
            "строка не является математической операцией",
            "используются одновременно разные системы счисления",
            "формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)",
            "калькулятор должен принимать на вход числа от 1 до 10 включительно, не более",
            "в римской системе нет отрицательных чисел и нуля"
    };

    public static void main(String[] args) throws Exception {
        System.out.println(calc(new Scanner(System.in).nextLine()));
    }

    /**
     * Основной метод калькулятор
     **/
    public static String calc(String input) throws Exception {

        int result;
        char operator = '!';
        boolean isRoman;

        // ищем во входящем тексте математический оператор
        for (char op : operators) {
            if (input.contains(String.valueOf(op))) operator = op;
        }
        // если не нашли мат. оператор то бросаем исключение
        if (operator == '!') throw new Exception(excMessages[0]);

        else {
            // если нашли то делим выражение по оператору убирая пробелы и присваиваем
            final String A = input.substring(0, input.indexOf(operator)).trim();
            final String B = input.substring(input.indexOf(operator) + 1).trim();

            // пробуем конвертировать строку в арабские числа, если возвращается 0, то числа были не римские
            int a = romanToDecimal(A);
            int b = romanToDecimal(B);
            if (a != 0 && b != 0) isRoman = true;

            // проверяем являются ли числа арабскими, если оба арабские, то парсим
            else if (isDecimal(A) && isDecimal(B)) {
                a = Integer.parseInt(A);
                b = Integer.parseInt(B);
                isRoman = false;
            }
            // если одно число арабское а другое римское бросаем соответствующее исключение
            else if (a != 0 && isDecimal(B) || b != 0 && isDecimal(A)) throw new Exception(excMessages[1]);

                // иначе бросаем общее исключение на несоответствие формату
            else throw new Exception(excMessages[2]);

            // проверяем чтобы числа на входе были не больше 10, если меньше то считаем
            if (a > 10 || b > 10) throw new Exception(excMessages[3]);
            else if (operator == operators[0]) result = a + b;
            else if (operator == operators[1]) result = a - b;
            else if (operator == operators[2]) result = a * b;
            else result = a / b;
        }

        // если числа на входе были римские, то конвертируем результат обратно в римский
        if (isRoman) {
            // проверяем чтобы результат был больше нуля иначе бросаем соответствуещее исключение
            if (result > 0) return decimalToRoman(result);
            else throw new Exception(excMessages[4]);
        } else return String.valueOf(result);
    }

    /**
     * Вспомогательные методы
     **/

    private static boolean isDecimal(String str) {
        if (str.isEmpty()) return false;
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    private static int romanToDecimal(String roman) {
        int decimal = 0;
        String rest = roman;
        for (int i = 0; i < romanNums.length; i++) {
            while (rest.startsWith(romanNums[i])) {
                decimal += values[i];
                rest = rest.substring(romanNums[i].length());
            }
        }
        if (roman.equals(decimalToRoman(decimal))) return decimal;
        return 0;
    }

    private static String decimalToRoman(int decimal) {
        StringBuilder roman = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            while (decimal >= values[i]) {
                decimal -= values[i];
                roman.append(romanNums[i]);
            }
        }
        return String.valueOf(roman);
    }
}
