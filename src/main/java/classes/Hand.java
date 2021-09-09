package classes;

import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@Data
@Builder
public class Hand {
    private ArrayList<String> cards;
}
