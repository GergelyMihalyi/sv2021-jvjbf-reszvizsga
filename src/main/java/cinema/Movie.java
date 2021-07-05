package cinema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    private Long id;
    private String title;
    private LocalDateTime date;
    private int maxSpaces;
    private int freeSpaces;

    public void setReservedSpaces(int numberOfReserve){
        if(numberOfReserve <= freeSpaces){
            freeSpaces -= numberOfReserve;
        }else{
            throw new IllegalStateException("There is not enough free space");
        }
    }
}
