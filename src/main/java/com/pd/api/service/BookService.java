package com.pd.api.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.pd.api.entity.Book;
import com.pd.api.entity.User;
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
    @RequestMapping(value="/{id:[0-9]}/favorites", method = RequestMethod.POST)
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
    @RequestMapping(value="/{id:[0-9]}/favorites", method = RequestMethod.DELETE)
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
    @RequestMapping(value="/{id:[0-9]}/unfavorites", method = RequestMethod.POST)
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
    @RequestMapping(value="/{id:[0-9]}/wishlists", method = RequestMethod.POST)
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
    @RequestMapping(value="/{id:[0-9]}/wishlists", method = RequestMethod.DELETE)
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
    @RequestMapping(value="/{id:[0-9]}/unwishlists", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void unwishlistBook(@ModelAttribute CustomUserData userData,
            @PathVariable("id") final Long bookId) {
        BookServiceImplementation.unwishlistBook(userData.getUsername(), bookId);
    }
    
    //start reading
    
    //finihs reading
    
    //get posdtas
    
    //get users favorited
}
