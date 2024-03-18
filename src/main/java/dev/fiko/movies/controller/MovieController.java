package dev.fiko.movies.controller;

import dev.fiko.movies.entity.Movie;
import dev.fiko.movies.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<List<Movie>> getMovies() {
        return new ResponseEntity<List<Movie>>(movieService.findAllMovies(), HttpStatus.OK);
    }

    @GetMapping("/{imdbId}")
    public ResponseEntity<Optional<Movie>> getSingleMovie(@PathVariable String imdbId){
        return new ResponseEntity<Optional<Movie>>(movieService.findMovieByImdbId(imdbId), HttpStatus.OK);
    }

    @PostMapping("/{imdbId}/{userId}/add-to-watchlist")
    public ResponseEntity<Movie> addToWatchlist(@PathVariable String imdbId, @PathVariable String userId) {
        return new ResponseEntity<>(movieService.addMovieToWatchlist(imdbId, userId), HttpStatus.OK);
    }

    @GetMapping("/watchlist/{userId}")
    public ResponseEntity<List<Movie>> getWatchlistMovies(@PathVariable String userId) {
        return new ResponseEntity<>(movieService.getWatchlistMovies(userId), HttpStatus.OK);
    }

    @DeleteMapping("/{imdbId}/{userId}/remove-from-watchlist")
    public ResponseEntity<Movie> removeFromWatchlist(@PathVariable String imdbId, @PathVariable String userId) {
        try {
            Movie removedMovie = movieService.removeMovieFromWatchlist(imdbId, userId);
            return new ResponseEntity<>(removedMovie, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
