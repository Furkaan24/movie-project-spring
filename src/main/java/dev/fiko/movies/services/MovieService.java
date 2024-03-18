package dev.fiko.movies.services;

import dev.fiko.movies.entity.Movie;
import dev.fiko.movies.entity.WatchList;
import dev.fiko.movies.repository.MovieRepository;
import dev.fiko.movies.repository.WatchlistRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private WatchlistRepository watchlistRepository;

    public List<Movie> findAllMovies() {
        return movieRepository.findAll();
    }
    public Optional<Movie> findMovieByImdbId(String imdbId) {
        return movieRepository.findMovieByImdbId(imdbId);
    }

    public Movie addMovieToWatchlist(String imdbId, String userId) {
        Optional<Movie> optionalMovie = movieRepository.findMovieByImdbId(imdbId);
        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            WatchList watchlist = watchlistRepository.findByUserId(userId);
            if (watchlist == null) {
                watchlist = new WatchList(ObjectId.get(), userId, new ArrayList<>());
            }
            watchlist.getMovies().add(movie);
            watchlistRepository.save(watchlist);
            return movie;
        } else {
            throw new RuntimeException("Movie not found with IMDb ID: " + imdbId);
        }
    }

    public List<Movie> getWatchlistMovies(String userId) {
        WatchList watchlist = watchlistRepository.findByUserId(userId);
        if (watchlist != null) {
            return watchlist.getMovies();
        } else {
            return Collections.emptyList();
        }
    }

    public Movie removeMovieFromWatchlist(String imdbId, String userId) {
        Optional<Movie> optionalMovie = movieRepository.findMovieByImdbId(imdbId);
        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            WatchList watchlist = watchlistRepository.findByUserId(userId);
            if (watchlist != null) {
                if (watchlist.getMovies().remove(movie)) {
                    watchlistRepository.save(watchlist);
                    return movie;
                } else {
                    throw new RuntimeException("Movie not found in watchlist with IMDb ID: " + imdbId);
                }
            } else {
                throw new RuntimeException("Watchlist not found for user: " + userId);
            }
        } else {
            throw new RuntimeException("Movie not found with IMDb ID: " + imdbId);
        }
    }
}
