package domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@RequiredArgsConstructor
public class Dish {
    public enum Type { MEAT, FISH, OTHER }

    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final Type type;
}
