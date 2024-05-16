package enums;

public enum CleaningHabit {
    ONCE_IN_A_WHILE(0), 
    OFTEN(1), 
    EVERYDAY(2);

    private final int value;

    CleaningHabit(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
