package cinema;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private ModelMapper modelMapper;

    private AtomicLong idGenerator = new AtomicLong();

    private List<Movie> movies = new ArrayList<>();

    public MovieService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<MovieDTO> listMovies(Optional<String> title) {
        Type targetListType = new TypeToken<List<MovieDTO>>() {
        }.getType();
        List<Movie> filtered = movies.stream()
                .filter(m -> title.isEmpty() || m.getTitle().equalsIgnoreCase(title.get()))
                .collect(Collectors.toList());

        return modelMapper.map(filtered, targetListType);
    }

    public MovieDTO findMovieById(long id) {
        return modelMapper.map(movies.stream().filter(m -> m.getId() == id).findAny()
                .orElseThrow(() -> new IllegalArgumentException("Movies not found: " + id)), MovieDTO.class);
    }

    public MovieDTO createMovie(CreateMovieCommand command) {
        Movie movie = new Movie(idGenerator.incrementAndGet(), command.getTitle(), command.getDate(), command.getMaxSpaces(), command.getMaxSpaces());
        movies.add(movie);
        return modelMapper.map(movie, MovieDTO.class);
    }

    public MovieDTO reserveSpaces(long id, CreateReservationCommand command) {

        Movie movie = movies.stream().filter(m -> m.getId() == id).findAny()
                .orElseThrow(() -> new IllegalArgumentException("Movies not found: " + id));


        movie.setReservedSpaces(command.getReservedSpaces());


        return modelMapper.map(movie, MovieDTO.class);
    }

    public MovieDTO updateMovie(long id, UpdateDateCommand command) {
        Movie movie = movies.stream().filter(m -> m.getId() == id).findAny()
                .orElseThrow(() -> new IllegalArgumentException("Movies not found: " + id));
        movie.setDate(command.getDate());
        return modelMapper.map(movie, MovieDTO.class);
    }

    public void deleteAllMovies() {
        idGenerator = new AtomicLong();
        movies.clear();
    }
}
