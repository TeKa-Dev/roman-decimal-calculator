package org.teka;

public class Main {

    private static final String[] operators = {"\\+", "-", "\\*", "/"};
    private static final String[] ramanNums =
            {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC", "C"};

    public static String calc(String input) throws Exception {

        int a;
        int b;
        int result;
        boolean isRomanA;
        boolean isRomanB;
        int indexOpr = getIndexOpr(input);                                // ищем во входящем выражении математический оператор

        if (indexOpr == -1) {                                             // если не нашли мат. оператор то бросаем исключение
            throw new Exception("строка не является математической операцией");
        }
        else {                                                            // если нашли то
            String[] nums = input.trim().split(operators[indexOpr]);      // делим выражение по оператору
                                                                          // если больше двух операндов или есть еще оператор(ы) то бросаем исключение
            if (nums.length != 2 || getIndexOpr(nums[0]) != -1 || getIndexOpr(nums[1]) != -1) {
                throw new Exception("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
            } else {

                isRomanA = romToAr(nums[0].trim()) != 0;         // проверяем является ли первое число римским
                isRomanB = romToAr(nums[1].trim()) != 0;         // проверяем является ли второе число римским

                if (isRomanA && isRomanB) {
                    a = romToAr(nums[0].trim());                 // если оба римские, то конвертируем в арабские и присваеваем переменным
                    b = romToAr(nums[1].trim());
                }
                else if (!isRomanA && !isRomanB) {              // если же оба арабские, то присваеваем через парсинг
                    a = Integer.parseInt(nums[0].trim());
                    b = Integer.parseInt(nums[1].trim());
                }                                               // иначе получается, что один операнд римский а другой нет, бросаем исключение
                else throw new Exception("используются одновременно разные системы счисления");

                if (a > 10 && b > 10)                           // проверяем чтобы числа на входе были не больше 10, если меньше то считаем
                    throw new Exception("Калькулятор должен принимать на вход числа от 1 до 10 включительно, не более");
                else if (indexOpr == 0) result = a + b;
                else if (indexOpr == 1) result = a - b;
                else if (indexOpr == 2) result = a * b;
                else result = a / b;

            }
        }
                                                                // если числа на входе были римские, то конвертируем результат обратно в римский
        if (isRomanA) {                                         // и проверяем чтобы были не меньше 1
            if (result < 1) throw new Exception("в римской системе нет отрицательных чисел и нуля");
            else return arToRom(result);
        } else return String.valueOf(result);
    }

    // метод возвращает индекс массива String[] operators, в котором есть оператор из мат. выражения который принимается в параметрах
    private static int getIndexOpr(String exp) {

        int mathOp = -1;

        for (int i = 0; i < operators.length; i++) {

            if (exp.matches(".*" + operators[i] + ".*")) mathOp = i;
        }
        return mathOp;
    }


    // конвертирует римские числа в арабские
    private static int romToAr(String rom) {

        int ar = 0;

        for (int i = 0; i < 10; i++) {

            if (rom.equals(ramanNums[i])) {
                ar = i + 1;
            }
        }
        return ar;
    }

    // конвертирует арабские числа в римские
    private static String arToRom(int ar) {

        String rom;

        if (ar <= 10) rom = ramanNums[ar - 1];
        else if (ar == 100) rom = ramanNums[18];
        else {
            rom = ramanNums[10 + (ar / 10) - 2] + (ar % 10 == 0 ? "" : ramanNums[(ar % 10) - 1]);
        }
        return rom;
    }
}
