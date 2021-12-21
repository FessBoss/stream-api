package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trader {
    private String name;
    private String city;

    @Override
    public String toString() {
        return "Trader:" + this.name + " in " + this.city;
    }
}
