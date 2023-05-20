enum RomanNumbers {
    I(1),
    V(5),
    X(10),
    L(50),
    C(100);

    private int value;

    RomanNumbers(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static RomanNumbers fromChar(char c) {
        for (RomanNumbers numeral : RomanNumbers.values()) {
            if (numeral.name().equals(String.valueOf(c))) {
                return numeral;
            }
        }
        return null;
    }
}

