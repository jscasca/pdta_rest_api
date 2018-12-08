package com.pd.api.service;

import com.pd.api.entity.Book;
import com.pd.api.entity.Club;
import com.pd.api.entity.ClubMembership;
import com.pd.api.entity.ClubReading;
import com.pd.api.entity.aux.ClubRequestWrapper;
import com.pd.api.entity.aux.ClubView;
import com.pd.api.security.CustomUserData;
import com.pd.api.service.impl.ClubServiceImplementation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by tin on 19/09/18.
 */
@Controller
@RequestMapping(value = "/api/clubs")
public class ClubService {

//    @RequestMapping(method = RequestMethod.GET)
//    @ResponseBody
//    public List<Club> findAll(@RequestParam(value="start", defaultValue="0") int start,
//                                    @RequestParam(value="limit", defaultValue="10") int limit,
//                                    final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
//        return ClubServiceImplementation.getClubs(start, limit);
//    }
//
//    @RequestMapping(method = RequestMethod.GET, value="/{id:[0-9]+}/users")
//    @ResponseBody
//    public List<User> getClubMembers(@PathVariable("id") final Long ClubId,
//                                      @RequestParam(value="start", defaultValue="0") int start,
//                                      @RequestParam(value="limit", defaultValue="10") int limit) {
//        return ClubServiceImplementation.getClubMembers(ClubId, start, limit);
//    }
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Club createClub(@ModelAttribute final CustomUserData userData,
                            @RequestBody(required=true) final ClubRequestWrapper ClubRequest) {

        return ClubServiceImplementation.createClub(userData.getUsername(), ClubRequest);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id:[0-9]+}")
    @ResponseBody
    public Club getClub(@PathVariable("id") final Long id) {
        return ClubServiceImplementation.getClubById(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{name:[a-zA-Z][a-zA-Z0-9_]+}")
    @ResponseBody
    public Club getClub(@PathVariable("name") final String name) {
        return ClubServiceImplementation.getClubByName(name);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id:[0-9]+}/views")
    @ResponseBody
    public ClubView getClubView(@PathVariable("id") final Long id) {
        return ClubServiceImplementation.getClubViewById(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{name:[a-zA-Z][a-zA-Z0-9_]+}/views")
    @ResponseBody
    public ClubView getClubView(@PathVariable("name") final String name) {
        return ClubServiceImplementation.getClubViewByName(name);
    }

    @RequestMapping(method = RequestMethod.POST, value="/{id:[0-9]+}/memberships")
    @ResponseBody
    public ClubMembership joinClub(@ModelAttribute final CustomUserData userData,
                                    @PathVariable("id") final Long ClubId) {
        return ClubServiceImplementation.joinClub(userData.getUsername(), ClubId);
    }

    @RequestMapping(method = RequestMethod.GET, value="/{id:[0-9]+}/memberships")
    @ResponseBody
    public ClubMembership getMembership(@ModelAttribute final CustomUserData userData,
                                   @PathVariable("id") final Long clubId) {
        return ClubServiceImplementation.getMembership(userData.getUsername(), clubId);
    }

    @RequestMapping(method = RequestMethod.GET, value="/{name:[a-zA-Z][a-zA-Z0-9_]+}/memberships")
    @ResponseBody
    public ClubMembership getMembership(@ModelAttribute final CustomUserData userData,
                                        @PathVariable("name") final String clubName) {
        return ClubServiceImplementation.getMembership(userData.getUsername(), clubName);
    }

    // TODO: implement
    @RequestMapping(method = RequestMethod.GET, value="/{id:[0-9]+}/readings")
    @ResponseBody
    public List<Book> getClubWishlist() {
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, value="/{id:[0-9]+}/readings")
    @ResponseBody
    public ClubReading wishlist(@ModelAttribute final CustomUserData userData,
                         @PathVariable("id") final Long clubId,
                         @RequestBody final Long bookId) {
        //
        return ClubServiceImplementation.wishlist(clubId, userData.getUsername(), bookId);
    }

    @RequestMapping(method = RequestMethod.POST, value="/{name:[a-zA-Z][a-zA-Z0-9_]+}/readings")
    @ResponseBody
    public ClubReading wishlist(@ModelAttribute final CustomUserData userData,
                                @PathVariable("name") final String clubName,
                                @RequestBody final Long bookId) {
        return ClubServiceImplementation.wishlist(clubName, userData.getUsername(), bookId);
    }

    @RequestMapping(method = RequestMethod.POST, value="/{id:[0-9]+}/readings/start")
    @ResponseBody
    public ClubReading startReading(@ModelAttribute final CustomUserData userData,
                                    @PathVariable("id") final Long clubId,
                                    @RequestBody final Long readingId) {
        return ClubServiceImplementation.startReading(clubId, userData.getUsername(), readingId);
    }

    @RequestMapping(method = RequestMethod.POST, value="/{name:[a-zA-Z][a-zA-Z0-9_]+}/readings/start")
    @ResponseBody
    public ClubReading startReading(@ModelAttribute final CustomUserData userData,
                                    @PathVariable("name") final String clubName,
                                    @RequestBody final Long readingId) {
        return ClubServiceImplementation.startReading(clubName, userData.getUsername(), readingId);
    }

    @RequestMapping(method = RequestMethod.POST, value="/{id:[0-9]}/readings/finish")
    @ResponseBody
    public ClubReading finishReading(@ModelAttribute final CustomUserData userData,
                                    @PathVariable("id") final Long clubId,
                                    @RequestBody final Long readingId) {
        return ClubServiceImplementation.finishReading(clubId, userData.getUsername(), readingId);
    }

    @RequestMapping(method = RequestMethod.POST, value="/{name:[a-zA-Z][a-zA-Z0-9_]+}/readings/finish")
    @ResponseBody
    public ClubReading finishReading(@ModelAttribute final CustomUserData userData,
                                     @PathVariable("name") final String clubName,
                                     @RequestBody final Long readingId) {
        return ClubServiceImplementation.finishReading(clubName, userData.getUsername(), readingId);
    }

//    @RequestMapping(method = RequestMethod.GET, value="/{id:[0-9]+}/testing")
//    @ResponseBody
//    public String testSimilar(@PathVariable("id") final Long id) {
//        return "numeric " + id.toString();
//    }
//
//    @RequestMapping(method = RequestMethod.GET, value="/{id:[a-zA-Z][a-zA-Z0-9_]+}/testing")
//    @ResponseBody
//    public String testSimilar(@PathVariable("id") final String name) {
//        return "string " + name;
//    }

    // TODO: implement addToClubWishlist
    // TODO: implement addToClubReading
    // TODO: implement getClubReading
    // TODO: implement startClubThread
}
