import java.io.IOException;
import java.util.Scanner;

class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String result = calc(input);
        System.out.println(result);
    }

    public static String calc(String input) throws IOException {
        // Проверяем, введены ли 3 элемента в строке или нет
        String[] arrayFromInput = input.split(" ");
        if (arrayFromInput.length < 3) {
            throw new IOException("Строка не является математической операцией");
        } else if (arrayFromInput.length > 3) {
            throw new IOException("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }

        // Разбиваем введённую строку на 2 числа и оператор
        String firstNumber = arrayFromInput[0];
        String operator = arrayFromInput[1];
        String secondNumber = arrayFromInput[2];

        // Проверяем, одной ли системы счисления введённые числа
        boolean isArabic = isArabicNumber(firstNumber) && isArabicNumber(secondNumber);
        boolean isRoman = isRomanNumber(firstNumber) && isRomanNumber(secondNumber);
        if (!isArabic && !isRoman) {
            throw new IOException("Используются одновременно разные системы счисления");
        }

        // Проверяем, одной корректность диапазона введённых чисел
        boolean isCorrect;
        if (isArabic) {
            isCorrect = isCorrectArabicNumber(firstNumber) && isCorrectArabicNumber(secondNumber);
        } else {
            isCorrect = isCorrectRomanNumber(firstNumber) && isCorrectRomanNumber(secondNumber);
        }
        if (!isCorrect) {
            throw new IOException("Введены числа не в диапазоне от 1 до 10");
        }

        // Приводим числа к типу int
        int num1, num2;
        if (isArabic) {
            num1 = Integer.parseInt(firstNumber);
            num2 = Integer.parseInt(secondNumber);
        } else {
            num1 = romanToArabic(firstNumber);
            num2 = romanToArabic(secondNumber);
        }

        // Подсчитываем результат одновременно с проверкой корректности операции
        int result = switch (operator) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            case "/" -> num1 / num2;
            default -> throw new IOException("Используется некорректный оператор");
        };


        // Формируем вывод числа, проверяем отрицательность в случае с римскими цифрами
        if (isArabic) {
            return String.valueOf(result);
        } else {
            if (result <= 0) {
                throw new IllegalArgumentException("В римской системе нет отрицательных чисел");
            }
            return arabicToRoman(result);
        }
    }

    private static boolean isArabicNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isRomanNumber(String input) {
        String romanNumbers = "IVXLCDM";
        for (char c : input.toCharArray()) {
            if (romanNumbers.indexOf(c) == -1) {
                return false;
            }
        }
        return true;
    }

    private static boolean isCorrectArabicNumber(String arabicNumber) {
        int number = Integer.parseInt(arabicNumber);
        return number >= 1 && number <= 10;
    }

    private static boolean isCorrectRomanNumber(String romanNumber) {
        String[] romanNumbers = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        for (String number : romanNumbers) {
            if (romanNumber.equals(number)) {
                return true;
            }
        }
        return false;
    }

    private static int romanToArabic(String romanNumber) {
        int result = 0;
        int previousValue = 0;
        int lastIndex = romanNumber.length() - 1;

        for (int i = lastIndex; i >= 0; i--) {
            char c = romanNumber.charAt(i);
            RomanNumbers currentSymbol = RomanNumbers.fromChar(c);

            int value = currentSymbol.getValue();

            if (value >= previousValue) {
                result += value;
            } else {
                result -= value;
            }

            previousValue = value;
        }

        return result;
    }

    private static String arabicToRoman(int arabicNumber) {
        int[] arabicNumbers = {100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanNumbers = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder result = new StringBuilder();
        int remaining = arabicNumber;

        for (int i = 0; i < arabicNumbers.length; i++) {
            int currentNumber = arabicNumbers[i];
            while (remaining >= currentNumber) {
                result.append(romanNumbers[i]);
                remaining -= currentNumber;
            }
        }

        return result.toString();
    }
}
