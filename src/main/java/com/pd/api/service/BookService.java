package com.pd.api.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.pd.api.entity.*;
import com.pd.api.entity.aux.BookRequestWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.pd.api.entity.aux.BookInfo;
import com.pd.api.entity.aux.PosdtaWrapper;
import com.pd.api.entity.aux.UserToBook;
import com.pd.api.security.CustomUserData;
import com.pd.api.service.impl.BookServiceImplementation;

@Controller
@RequestMapping(value = "/api/books")
public class BookService {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Book> findBooks(@RequestParam(value="start", defaultValue="0") int start,
            @RequestParam(value="limit", defaultValue="10") int limit,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return BookServiceImplementation.getBooks(start, limit);
    }
    
    @RequestMapping(method = RequestMethod.GET, value="/mostRead")
    @ResponseBody
    public List<Book> getMostRead(@RequestParam(value="start", defaultValue="0") int start,
            @RequestParam(value="limit", defaultValue="10") int limit) {
        return BookServiceImplementation.getMostRead(start, limit);
    }
    
    @RequestMapping(value="/{id:[0-9]+}")
    @ResponseBody
    public Book findById(@PathVariable("id") final Long id,
            final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return BookServiceImplementation.getBookById(id);
    }
    
    //BOOKS AND FAVORITES
    //-------------------
    
    @RequestMapping(value="/{id:[0-9]+}/favorites", method = RequestMethod.GET)
    @ResponseBody
    public List<User> favoritedBook(@PathVariable("id") final Long bookId,
            @RequestParam(value="start", defaultValue="0") int start,
            @RequestParam(value="limit", defaultValue="10") int limit) {
        return BookServiceImplementation.getBookFavorites(bookId, start, limit);
    }
    
    /**
     * Favorites a book
     * returns 204: No_CONTENT
     */
    @Secured("ROLE_USER")
    @RequestMapping(value="/{id:[0-9]+}/favorites", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void favoriteBook(@ModelAttribute CustomUserData userData,
            @PathVariable("id") final Long bookId) {
        BookServiceImplementation.favoriteBook(userData.getUsername(), bookId);
    }
    
    /**
     * Deletes a favorited book (a.k.a. unfavorite)
     * returns 204: No_CONTENT
     */
    @Secured("ROLE_USER")
    @RequestMapping(value="/{id:[0-9]+}/favorites", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteFavoriteBook(@ModelAttribute CustomUserData userData,
            @PathVariable("id") final Long bookId) {
        BookServiceImplementation.unfavoriteBook(userData.getUsername(), bookId);
    }
    
    /**
     * Unfavorite a book
     * returns 204: No_CONTENT
     */
    @Secured("ROLE_USER")
    @RequestMapping(value="/{id:[0-9]+}/unfavorites", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void unfavoriteBook(@ModelAttribute CustomUserData userData,
            @PathVariable("id") final Long bookId) {
        BookServiceImplementation.unfavoriteBook(userData.getUsername(), bookId);
    }
    
    //BOOKS AND WISHLISTS
    //--------------------

    @RequestMapping(value="/{id:[0-9]+}/wishlists", method = RequestMethod.GET)
    @ResponseBody
    public List<User> wishlistedBook(@PathVariable("id") final Long bookId,
            @RequestParam(value="start", defaultValue="0") int start,
            @RequestParam(value="limit", defaultValue="10") int limit) {
        return BookServiceImplementation.getBookWishlists(bookId, start, limit);
    }
    
    /**
     * Add book to the user wishlist
     * returns 204: No_CONTENT
     */
    @Secured("ROLE_USER")
    @RequestMapping(value="/{id:[0-9]+}/wishlists", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void wishlistBook(@ModelAttribute CustomUserData userData,
            @PathVariable("id") final Long bookId) {
        BookServiceImplementation.wishlistBook(userData.getUsername(), bookId);
    }
    
    /**
     * Deletes a book from a user wishlist
     * returns 204: No_CONTENT
     */
    @Secured("ROLE_USER")
    @RequestMapping(value="/{id:[0-9]+}/wishlists", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteWishlistedBook(@ModelAttribute CustomUserData userData,
            @PathVariable("id") final Long bookId) {
        BookServiceImplementation.unwishlistBook(userData.getUsername(), bookId);
    }
    
    /**
     * Remove a book from the user wishlist
     * returns 204: No_CONTENT
     */
    @Secured("ROLE_USER")
    @RequestMapping(value="/{id:[0-9]+}/unwishlists", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void unwishlistBook(@ModelAttribute CustomUserData userData,
            @PathVariable("id") final Long bookId) {
        BookServiceImplementation.unwishlistBook(userData.getUsername(), bookId);
    }
    
    /**
     * Get the users reading a particular book
     * @param bookId
     * @param start
     * @param limit
     * @return a list of user that are reading the book
     */
    @RequestMapping(value="/{id:[0-9]+}/readings", method = RequestMethod.GET)
    @ResponseBody
    public List<User> readingBook(@PathVariable("id") final Long bookId,
            @RequestParam(value="start", defaultValue="0") int start,
            @RequestParam(value="limit", defaultValue="10") int limit) {
        return BookServiceImplementation.getBookReading(bookId, start, limit);
    }
    
    /**
     * Start reading a book
     * returns 204: No_CONTENT
     */
    @Secured("ROLE_USER")
    @RequestMapping(value="/{id:[0-9]+}/readings", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void startReading(@ModelAttribute CustomUserData userData,
            @PathVariable("id") final Long bookId) {
        BookServiceImplementation.startReadingBook(userData.getUsername(), bookId);
    }
    
    /**
     * Stop reading the book (should only apply after you have left a posdta of the book)
     * This means that the first time you stop reading a book you have to leave a posdta
     * @param userData
     * @param bookId
     */
    @Secured("ROLE_USER")
    @RequestMapping(value="/{id:[0-9]+}/readings", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void stopReading(@ModelAttribute final CustomUserData userData,
            @PathVariable("id") final Long bookId) {
        BookServiceImplementation.stopReadingBook(userData.getUsername(), bookId);
    }

    /**
     *
     * @param userData
     * @param bookId
     * @param posdtaWrapper
     * @return the created posdta
     */
    @Secured("ROLE_USER")
    @RequestMapping(value="/{id:[0-9]+}/posdta", method = RequestMethod.POST)
    @ResponseBody
    public Posdta posdta(@ModelAttribute final CustomUserData userData,
                              @PathVariable("id") final Long bookId,
                              @RequestBody (required=false) final PosdtaWrapper posdtaWrapper) {
        return BookServiceImplementation.savePosdta(userData.getUsername(), bookId, posdtaWrapper);
    }

    /**
     *
     * @param userData
     * @param bookId

     * @return the created posdta
     */
    @Secured("ROLE_USER")
    @RequestMapping(value="/{id:[0-9]+}/posdtas", method = RequestMethod.POST)
    @ResponseBody
    public Posdta submitPosdta(@ModelAttribute final CustomUserData userData,
            @PathVariable("id") final Long bookId,
            @RequestBody(required=false) final PosdtaWrapper posdtaWrapper) {
        return BookServiceImplementation.savePosdta(userData.getUsername(), bookId, posdtaWrapper);
    }
    
    /**
     * Get all the Posdtas for a book
     * @param bookId
     * @param start
     * @param limit
     * @return a list of Posdtas
     */
    @RequestMapping(value="/{id:[0-9]+}/posdtas", method = RequestMethod.GET)
    @ResponseBody
    public List<Posdta> posdtas(@PathVariable("id") final Long bookId,
            @RequestParam(value="start", defaultValue="0") final int start,
            @RequestParam(value="limit", defaultValue="10") final int limit) {
        return BookServiceImplementation.getBookPosdtas(bookId, start, limit);
    }
    
    @RequestMapping(value="/{id:[0-9]+}/posdtas/votes", method = RequestMethod.GET)
    @ResponseBody
    public List<Posdta> posdtasWithUserVote(@ModelAttribute final CustomUserData userData,
            @PathVariable("id") final Long bookId,
            @RequestParam(value="start", defaultValue="0") final int start,
            @RequestParam(value="limit", defaultValue="10") final int limit) {
        return BookServiceImplementation.getBookPosdtasWithUserVotes(userData.getUsername(), bookId, start, limit);
    }
    
    /**
     * A map of interactions between the user and the book
     * @param bookId
     * @param userData
     * @return
     */
    @Secured("ROLE_USER")
    @RequestMapping(value="/{id:[0-9]+}/interactions", method = RequestMethod.GET)
    @ResponseBody
    public UserToBook getInteractions(@PathVariable("id") final Long bookId,
            @ModelAttribute final CustomUserData userData) {
        return BookServiceImplementation.getUserToBookInteraction(userData.getUsername(), bookId);
    }
    
    /**
     * Get the book info and statistics
     * @param bookId
     * @return
     */
    @RequestMapping(value="/{id:[0-9]+}/info", method = RequestMethod.GET)
    @ResponseBody
    public BookInfo getInfo(@PathVariable("id") final Long bookId) {
        return BookServiceImplementation.getBookInfo(bookId);
    }
    
    @Secured("ROLE_USER")
    @RequestMapping(value="/requests", method = RequestMethod.POST)
    @ResponseBody
    public Book requestNewBook(@ModelAttribute final CustomUserData userData, @RequestBody final BookRequestWrapper newRequestWrapper) {
        return BookServiceImplementation.requestNewBook(newRequestWrapper);
    }

    @Secured("ROLE_USER")
    @RequestMapping(value="/requests/custom", method = RequestMethod.POST)
    @ResponseBody
    public Book requestCustomBook(@ModelAttribute final CustomUserData userData,
                                  @RequestBody final BookRequestWrapper newRequestWrapper) {
        return BookServiceImplementation.requestCustomBook(userData.getUsername(), newRequestWrapper);
    }
    
    /*@Secured("ROLE_ADMIN")
    @RequestMapping(value="/requests", method = RequestMethod.GET)
    @ResponseBody
    public List<NewBookRequest> getBookRequests(@ModelAttribute final CustomUserData userData,
            @RequestParam(value="start", defaultValue="0") int first,
            @RequestParam(value="limit", defaultValue="10") int limit) {
        return BookServiceImplementation.getBookRequests(first, limit);
    }*/

    @Secured("ROLE_USER")
    @RequestMapping(value="/suggestions", method = RequestMethod.GET)
    @ResponseBody
    public List<Book> getSuggestions(@ModelAttribute final CustomUserData userData,
            @RequestParam(value="start", defaultValue="0") int first,
            @RequestParam(value="limit", defaultValue="10") int limit) {
        //TODO: implement
        if(first == -1) {
            return BookServiceImplementation.getRandomSuggestions(userData.getUsername(), limit);
        }
        return BookServiceImplementation.browseSuggestions(userData.getUsername(), first, limit);
    }

    @RequestMapping(value="/{id:[0-9]+}/sameAuthor", method = RequestMethod.GET)
    @ResponseBody
    public List<Book> getBooksWithTheSameAuthor(@PathVariable("id") final Long bookId,
            @RequestParam(value="start", defaultValue="0") final int start,
            @RequestParam(value="limit", defaultValue="10") final int limit) {
        return BookServiceImplementation.getBooksWithTheSameAuthor(bookId, start, limit);
    }

    @Secured("ROLE_USER")
    @RequestMapping(value="/{id:[0-9]+}/similar", method = RequestMethod.POST)
    @ResponseBody
    public BookSimilarity suggestBookSimilarity(@PathVariable("id") final Long original,
                                                @RequestBody final Long similar,
                                                @ModelAttribute final CustomUserData userData) {
        return BookServiceImplementation.suggestSimilairty(userData.getUsername(), original, similar);
    }

    @RequestMapping(value="/{id:[0-9]+}/similarBooks", method = RequestMethod.GET)
    @ResponseBody
    public List<BookSimilarity> getSimilarBooks(@PathVariable("id") final Long bookId,
                                                @ModelAttribute final CustomUserData userData) {
        if(userData == null) {
            return BookServiceImplementation.getSimilarBooks(bookId);
        } else {
            return BookServiceImplementation.getSimilarBooks(bookId, userData.getUsername());
        }
    }

    @RequestMapping(value="/{id:[0-9]+}/similarBooks/{target:[0-9]+}/vote", method = RequestMethod.POST)
    @ResponseBody
    public BookSimilarity voteBookSimilarity() {
        return null;
    }

    @RequestMapping(value="/similar/{id:[0-9]+}", method = RequestMethod.POST)
    @ResponseBody
    public BookSimilarityVote voteSimilarity(@PathVariable("id") final Long similarity,
                                             @RequestParam(value="vote") final Boolean vote,
                                             @ModelAttribute final CustomUserData userData) {
        return BookServiceImplementation.voteBookSimilarity(userData.getUsername(), similarity, vote);
    }

    @RequestMapping(value="/similar/{id:[0-9]+}/up", method = RequestMethod.POST)
    @ResponseBody
    public BookSimilarityVote voteSimilarityUp(@PathVariable("id") final Long similarity,
                                             @ModelAttribute final CustomUserData userData) {
        return BookServiceImplementation.voteBookSimilarity(userData.getUsername(), similarity, true);
    }

    @RequestMapping(value="/similar/{id:[0-9]+}/down", method = RequestMethod.POST)
    @ResponseBody
    public BookSimilarityVote voteSimilarityDown(@PathVariable("id") final Long similarity,
                                             @ModelAttribute final CustomUserData userData) {
        return BookServiceImplementation.voteBookSimilarity(userData.getUsername(), similarity, false);
    }
}
