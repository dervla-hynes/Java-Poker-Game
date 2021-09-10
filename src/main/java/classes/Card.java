package classes;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Card {
    private String suit;
    private String value;

    @Override
    public String toString() {
        return value + suit;
    }
}
