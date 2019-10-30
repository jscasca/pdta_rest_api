package com.pd.api.db;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import com.pd.api.entity.*;
import com.pd.api.entity.aux.CommentNode;
import com.pd.api.entity.aux.CommentTree;
import org.springframework.transaction.annotation.Transactional;

import com.pd.api.db.indexer.AuthorIndex;
import com.pd.api.db.indexer.BookIndex;
import com.pd.api.entity.aux.BookInfo;
import com.pd.api.entity.aux.LibraryView;
import com.pd.api.security.SocialLogin;
import com.pd.api.security.SocialLoginUsername;

import com.pd.api.entity.ClubReading.ReadingStatus;

@Transactional
public class DAO {

    protected static ThreadEntityManagerFactory temf = null;
    
    protected DAO() {}
    
    static {
        try{
        temf = PosdtaThreadEntityManagerFactory.getInstance();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    @Transactional
    public static void init() {
        
        //Initialize roles
        Role.getMember();
        Role.getAdmin();
    }
    
    public static String getConnectionString() {
        return ThreadEntityManagerFactory.DEFAULT_CONNECTION_STRING;
    }
    
    protected static EntityManager getEM() {
        if (temf == null) temf = new ThreadEntityManagerFactory();
        return temf.getEntityManager();
    }
    
    public static boolean ping() {
        EntityManager em = getEM();
        try {
            em.createNativeQuery("select 0").getSingleResult();
        } catch(Exception ex) {
            
            return false;
        }
        return true;
    }

    public static <T> T put(T obj) {
        return put(obj, false);
    }
    
    public static <T> T put(T obj, boolean merge) {
        EntityManager em = getEM();
        EntityTransaction tx = em.getTransaction();
        if(tx.isActive()) {
            Object o = em.merge(obj);
            em.flush();
            tx.commit();
            em.refresh(o);
            return(T) o;
        } else {
            tx.begin();
            Object o = em.merge(obj);
            em.flush();
            em.refresh(o);
            tx.commit();
            em.refresh(o);
            return (T) o;
        }
    }

    // Hibernate will not re-calculate the @Formula field after it is updated.
    // https://stackoverflow.com/questions/48641052/hibernate-formula-returns-an-old-value-in-put-response

    /*public static <T> T putAndRefresh(T obj) {
        EntityManager em = getEM();
        Object o = em.merge(obj);
        em.refresh(obj);
        return (T)o;
    }*/
    
    
    /**
    MyEntity e = new MyEntity();

// scenario 1
// tran starts
em.persist(e); 
e.setSomeField(someValue); 
// tran ends, and the row for someField is updated in the database

// scenario 2
// tran starts
e = new MyEntity();
em.merge(e);
e.setSomeField(anotherValue); 
// tran ends but the row for someField is not updated in the database
// (you made the changes *after* merging)

// scenario 3
// tran starts
e = new MyEntity();
MyEntity e2 = em.merge(e);
e2.setSomeField(anotherValue); 
// tran ends and the row for someField is updated
// (the changes were made to e2, not e)
    */
    
    /*public static <T> T put(T obj, boolean merge) {
        EntityManager em = getEM();
        em.getTransaction().begin();
        if (merge) {
            obj = em.merge(obj);
        }
        em.persist(obj);
        em.flush();
        em.getTransaction().commit();
        return obj;
    }*/
    
    public static <T> T merge(T obj) {
        EntityManager em = getEM();
        obj = em.merge(obj);
        em.flush();
        return obj;
    }
    
    public static void delete(Object obj) {
        EntityManager em = getEM();
        em.remove(obj);
    }
    /*
    TODO: fix this bullshit methods
    public static void remove(Object obj) {
        EntityManager em = getEM();
        if(obj != null) {
            em.remove(obj);
        }
    }*/
    
    public static void flush() {
        getEM().flush();
    }
    
    public static void refresh(Object obj) {
        getEM().refresh(obj);
    }

    public static void clear() {
        getEM().clear();
    }
    
    public static <T> T remove(Class<T> type, Long id) {
        T obj = null;
        EntityManager em = getEM();
        EntityTransaction et = em.getTransaction();
        et.begin();
        obj = em.find(type, id);
        if( obj != null) {
            em.remove(obj);
        }
        et.commit();
        return obj;
    }
    
    /*@Transactional
    public static <T> int remove(Class<T> type, Long id) {
        Query query = createQuery("delete " + type.getName() + " obj where obj.id = ?" + id);
        return query.executeUpdate();
    }*/
    
    /*
    
    public static <T> int removeAll(Class<T> type, Project project) {
        Query query = createQuery("delete " + type.getName() + " obj where obj.project = ?", project);
        return query.executeUpdate();
    }
    
    public static <T> int removeAll(Class<T> type, Domain domain) {
        Query query = createQuery("delete " + type.getName() + " obj where obj.domain = ?", domain.getBoxedId());
        return query.executeUpdate();
    }
     */
    
    public static <T> T get(Class<T> type, Long id) {
        EntityManager em = getEM();
        return em.find(type, id);
    }
    
    public static <T> T get(Class<T> type, String id) {
        EntityManager em = getEM();
        return em.find(type, id);
    }

    public static Book getBookById(Long bookId) { return get(Book.class, bookId); }
    
    public static Query createQuery(String query) {
        EntityManager em = getEM();
        return em.createQuery(query);
    }
    
    public static Query createQuery(String query, Object... params) {
        Query q = getEM().createQuery(query);
        for(int i = 0; i<params.length; i++)q.setParameter(i + 1, params[i]);
        return q;
    }
    
    public static Query createNativeQuery(String query) {
        EntityManager em = getEM();
        return em.createNativeQuery(query);
    }
    
    public static Query createNativeQuery(String query, Class<?> type) {
        EntityManager em = getEM();
        return em.createNativeQuery(query, type);
    }
    
    public static Query createNativeQuery(String query, Class<?> type, Object... params ) {
        Query q = getEM().createNativeQuery(query, type);
        for(int i = 0; i<params.length; i++)q.setParameter(i + 1, params[i]);
        return q;
    }
    
    public static Query createNativeQueryWithParams(String query, Object...params) {
        Query q = getEM().createNativeQuery(query);
        for(int i = 0; i<params.length; i++)q.setParameter(i + 1, params[i]);
        return q;
    }
    
    public static <T> List<T> getAll(Class<T> type, int first, int limit) {
        return getAll(type, "", " order by id desc ", first, limit);
    }
    
    @SuppressWarnings("unchecked")
    public static <T> List<T> getAll(Class<T> type, String query, String orderBy, int first, int limit) {
        Query q = createQuery("Select obj from " + type.getName() + "  obj " + query + orderBy);
        if(first > 0) {
            q.setFirstResult(first);
        }
        if(limit > 0) {
            q.setMaxResults(limit);
        }
        return q.getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public static <T> List<T> getAll(Class<T> type, String query, String orderBy, int first, int limit, Object... params) {
        Query q = createQuery("Select obj from " + type.getName() + "  obj " + query + orderBy, params);
        if(first > 0) {
            q.setFirstResult(first);
        }
        if(limit > 0) {
            q.setMaxResults(limit);
        }
        return q.getResultList(); 
    }

    public static <T> List<T> getAllFromQuery(Class<T> type, String query, int first, int limit, Object... params) {
        Query q = createQuery(query, params);
        if(first > 0) {
            q.setFirstResult(first);
        }
        if(limit > 0) {
            q.setMaxResults(limit);
        }
        return q.getResultList();
    }
    
    //TODO: check this methods to be a single call
    public static List<Book> getAllBooksFromQuery(String query, int first, int limit, Object... params) {
        Query q = createQuery(query, params);
        if(first > 0) {
            q.setFirstResult(first);
        }
        if(limit > 0) {
            q.setMaxResults(limit);
        }
        return q.getResultList();
    }
    
    public static List<User> getAllUsersFromQuery(String query, int first, int limit, Object... params) {
        Query q = createQuery(query, params);
        if(first > 0) {
            q.setFirstResult(first);
        }
        if(limit > 0) {
            q.setMaxResults(limit);
        }
        return q.getResultList();
    }
    
    public static <T> T getUnique(Class<T> type, String query, Object... params) {
        Query q = createQuery("Select obj from " + type.getName() + " obj " + query, params);
        try {
            return (T) q.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        } catch (NonUniqueResultException nure) {
            //TODO: log or something
            return (T) q.getResultList().get(0);
        }
    }
    
    public static <T> T getFirst(Class<T> type, String query, Object... params) {
        Query q = createQuery("Select obj from " + type.getName() + " obj " + query, params);
        List<T> objs = q.getResultList();
        if(objs.size() == 0) return null;
        return objs.get(0);
    }

    public static ClubMembership getClubMembership(User u, Club c) {
        return getUnique(ClubMembership.class, "WHERE id.user = ? AND id.club = ?", u, c);
    }
    
    /*public static List<User> getUserFollowers(User u, ListWrapper lw) {
        return getAll(User.class, "Select user from Follower follower join follower.follower as user where follower.user.id = " + u.getId(), lw);
    }
    
    public static List<User> getUserFollowing(User u, ListWrapper lw) {
        return getAll(User.class, "Select user from Follower follower join follower.user as user where follower.follower.id = " + u.getId(), lw);
    }*/
    
    
    /**
     * Return true if there is at least one username with the selected value in the db. False otherwise
     * @param username
     * @return
     */
    public static boolean usernameExists(String username) {
        Query q = createQuery("Select u from User u where username = ?", username);
        return exists(q);
    }
    
    /**
     * Return true if there is at least one email with the selected value in the db. False otherwise
     * @param email
     * @return
     */
    public static boolean emailExists(String email) {
        Query q = createQuery("Select c from Credential c where email = ?", email);
        return exists(q);
    }

    public static Credential getCredentialByEmail(String email) {
        Query q = createQuery("Select c from Credential c where email = ?", email);
        try{ return (Credential)q.getSingleResult(); }
        catch(Exception e) {
            return null;
        }
    }
    
    /**
     * Return true is the query executed has one or more elements matched
     * @param q
     * @return
     */
    public static boolean exists(Query q) {
        List r = q.getResultList();
        if(r.size() == 0) return false;
        return true;
    }
    
    public static boolean nameAvailable(String name) {
        Query q = createQuery("Select u from User u where username = ?", name);
        try {
            q.getSingleResult();
            return false;
        } catch (NoResultException nre) {
            return true;
        } catch (NonUniqueResultException nure) {
            //TODO: log or something
            return false;
        }
    }
    
    public static Role getMemberRole() {
        return getUniqueByName(Role.class, Role.MEMBER);
    }
    
    public static Role getAdminRole() {
        return getUniqueByName(Role.class, Role.ADMIN);
    }
    
    /*public static Role getGuestRole() {
        return getUniqueByName(Role.class, Role.GUEST);
    }
    
    public static Role getModeratorRole() {
        return getUniqueByName(Role.class, Role.MODERATOR);
    }*/
    
    public static VerificationToken getVerificationToken(String id) {
        return get(VerificationToken.class, id);
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T getUniqueByName(Class<T> type, String name) {
        Query q =createQuery("Select o from " + type.getName() + " o where name = ?", name);
        try {
            return (T) q.getSingleResult();
        }catch(NoResultException nre) {
            return null;
        }
        
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T getUniqueByUsername(Class<T> type, String username) {
        Query q = createQuery("Select obj from " + type.getName() + " obj where username = ?", username);
        try {
            return (T) q.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
    
    public static User getUserBySocialLogin(SocialLoginUsername username) {
        Query q = createQuery("Select obj.user from SocialLogin obj where obj.providerUserId = ? and obj.socialProvider.name = ?", username.getId(), username.getProvider());
        try {
            return (User)q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public static User getUserByEmail(String email) {
        Query q = createQuery("Select obj.user from Credential obj where obj.email = ?", email);
        try {
            return (User)q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public static User getUserByUsername(String username) {
        if(SocialLogin.isSocialCredential(username)) {
            return getUserBySocialLogin(new SocialLoginUsername(username));
        }
        if(Credential.isValidEmail(username)) {
            return getUserByEmail(username);
        }
        //TODO: catch if it is by email
        return getUniqueByUsername(User.class, username);
    }
    
    public static User getUserById(Long id) {
        return get(User.class, id);
    }
    
    public static Language getLanguageByCode(String code) {
        Query q = createQuery("SELECT obj FROM " + Language.class.getName() + " obj where code = ?", code);
        try {
            return (Language)q.getSingleResult();
        } catch(Exception e) {
            return null;
        }
    }
    
    public static Book getBookFromWork(Work work, Language lang) {
        Query q = createQuery("SELECT obj FROM " + Book.class.getName() + " obj where work = ? and language = ? ", work, lang);
        List<Object> l = q.getResultList();
        if(l.isEmpty()) return null;
        return (Book)l.get(0);
    }
    
    public static Work getExistingWork(Author author, String title, Language lang) {
        Query q = createQuery("SELECT obj FROM " + Work.class.getName() + " obj where author = ? AND title = ? AND language = ?", author, title, lang);
        List<Object> l = q.getResultList();
        if(l.isEmpty()) return null;
        return (Work)l.get(0);
    }

    //TODO: update this to, additionally, take a set of authors
    public static List<Work> getExistingWorks(String title, Language lang) {
        Query q = createQuery("SELECT obj FROM " + Work.class.getName() + " obj WHERE title = ? AND language = ?", title, lang );
        return  (List<Work>) q.getResultList();
    }
    
    public static Work getWorkByTitle(String title) {
        return getFirstByParam(Work.class, "title", title);
    }
    
    public static Author getAuthorByName(String name) {
        return getFirstByParam(Author.class, "name", name);
    }
    
    public static <T> T getFirstByParam(Class<T> type, String param, String value) {
        Query q = createQuery("SELECT obj FROM " + type.getName() + " obj where " + param + " = ?", value);
        List<Object> l = q.getResultList();
        if(l.isEmpty()) return null;
        return (T)l.get(0);
    }

    public static LibraryView getUserLibraryView(User user) { return getUserLibraryView(user.getId()); }
    public static LibraryView getUserLibraryView(Long userId) {
        LibraryView lv = new LibraryView();
        List<Book> reading = getUserReading(userId, 0, LibraryView.DEFAULT_LIBRARY_VIEW_LIMIT);
        List<Book> wishlisted = getUserWishlisted(userId, 0, LibraryView.DEFAULT_LIBRARY_VIEW_LIMIT);
        List<Book> favorited = getUserFavorites(userId, 0, LibraryView.DEFAULT_LIBRARY_VIEW_LIMIT);
        List<Posdta> posdtas = getUserPosdtas(userId, 0, LibraryView.DEFAULT_POSDTA_VIEW_LIMIT);
        lv.setReading(reading);
        lv.setWishlisted(wishlisted);
        lv.setFavorited(favorited);
        lv.setPosdtas(posdtas);
        return lv;
    }
    
    public static List<Book> getUserFavorites(Long userId, int first, int limit) {
        return getAllBooksFromQuery("select distinct bf.book from BookFavorited bf where bf.user.id = ? order by bf.id desc", first, limit, userId);
    }
    
    public static List<Book> getUserWishlisted(Long userId, int first, int limit) {
        return getAllBooksFromQuery("select distinct bw.book from BookWishlisted bw where bw.user.id = ? order by bw.id asc", first, limit, userId);
    }
    
    public static List<Book> getUserReading(Long userId, int first, int limit) {
        return getAllBooksFromQuery("select distinct br.book from BookReading br where br.user.id = ? order by br.id asc", first, limit, userId);
    }

    public static List<Book> getUserRead(User user, int first, int limit) {
        return getAllFromQuery(Book.class, "SELECT p.book FROM Posdta p WHERE p.user = ? order by p.finish desc", first, limit, user);
    }
    
    public static List<Posdta> getUserPosdtas(Long userId, int first, int limit) {
        return getAll(Posdta.class, "where user.id = ?", "ORDER BY id DESC", first, limit, userId);
    }

    public static List<Posdta> getUserPosdtas(User user, int first, int limit) {
        return getAll(Posdta.class, "where user.id = ?", "ORDER BY id DESC", first, limit, user);
    }

    public static List<UserBookInteraction> getUserBookInteractions(User user, int first, int limit) {
//        return getAll(UserBookInteraction.class, " where user = ?", "", first, limit, user);
        return getAllFromQuery(UserBookInteraction.class,
                "SELECT DISTINCT i FROM " + UserBookInteraction.class.getName() + " i WHERE i.user = ? order by i.id desc",
                first,
                limit,
                user);
    }
    
    //TODO: fix this method
    public static BookInfo getBookInfo(Book book) {
        return null;
    }
    
    //TODO: fix this method 
    public static BookInfo getBookInfo(Long bookId) {
        Book book = get(Book.class, bookId);
        BookInfo info = new BookInfo(book);
        return info;
    }

    public static String getBookDetail(Long bookId) {
        BookDetail detail = get(BookDetail.class, bookId);
        return detail != null ? detail.getDetails() : "{}"; // return an empty detail
    }

    public static BookDetail setBookDetail(Long bookId, String details) {
        Book book = getBookById(bookId);
        BookDetail detail = new BookDetail(book, details);
        return put(detail);
    }
    
    public static BookIndex getLastBookIndex() {
        return getUnique(BookIndex.class, "ORDER BY id DESC limit 1");
    }
    
    //getAllBooksFromQuery("select distinct bw.book from BookWishlisted bw where bw.user.id = ?", first, limit, userId);
    //getAllBooksFromQuery(String query, int first, int limit, Object... params)
    public static List<Book> newBooksToIndex(BookIndex index) { return newBooksToIndex(index, 1000);}
    public static List<Book> newBooksToIndex(BookIndex index, int limit) {
        return getAllBooksFromQuery("select b from Book b where b.id > ?", 0, limit, index.getLastIndexed());
    }
    
    public static List<Book> getBooksSinceLastIndex() {return getBooksSinceLastIndex(1000);}
    public static List<Book> getBooksSinceLastIndex(int limit) {
        BookIndex index = getLastBookIndex();
        if(index == null) {
            index = new BookIndex(0L,0);
        }
        return newBooksToIndex(index, limit);
    }
    
    public static AuthorIndex getLastAuthorIndex() {
        return getUnique(AuthorIndex.class, "ORDER BY id DESC limit 1");
    }
    
    //getAll(Class<T> type, String query, String orderBy, int first, int limit, Object... params)
    public static List<Author> newAuthorsToIndex(AuthorIndex index) { return newAuthorsToIndex(index, 1000);}
    public static List<Author> newAuthorsToIndex(AuthorIndex index, int limit) {
        return getAll(Author.class, "where id > ?", "", 0, limit, index.getLastIndexed());
    }
    
    public static List<Author> getAuthorsSinceLastIndex() {return getAuthorsSinceLastIndex(1000);}
    public static List<Author> getAuthorsSinceLastIndex(int limit) {
        AuthorIndex index = getLastAuthorIndex();
        if(index == null) {
            index = new AuthorIndex(0L,0);
        }
        return newAuthorsToIndex(index, limit);
    }
    
    public static SocialProvider saveProvider(String name) {
        SocialProvider provider = new SocialProvider(name);
        return put(provider);
    }
    
    public static SocialProvider getProviderByName(String name) {
        Query q = createQuery("SELECT obj FROM " + SocialProvider.class.getName() + " obj where name = ?", SocialProvider.providerNameToLowerCase(name));
        try {
            return (SocialProvider)q.getSingleResult();
        } catch(Exception e) {
            return null;
        }
    }
    
    public static SocialLogin getSocialLoginById(SocialProvider provider, String userId) {
        Query q = createQuery("SELECT obj FROM " + SocialLogin.class.getName() + " obj where socialProvider = ? and providerUserId = ?", provider, userId);
        try {
            return (SocialLogin)q.getSingleResult();
        } catch(Exception e) {
            return null;
        }
    }
    
    //COUNTING
    
    public static int getUserFollowerCount(Long userId) {
        Query query = DAO.createNativeQueryWithParams("select count(*) from followers where user_id = ?", userId);
        int count = ((BigInteger)query.getSingleResult()).intValue();
        return count;
    }
    
    public static int getUserFollowingCount(Long userId) {
        Query query = DAO.createNativeQueryWithParams("select count(*) from followers where follower_id = ?", userId);
        int count = ((BigInteger)query.getSingleResult()).intValue();
        return count;
    }
    
    public static int getUserFavoriteCount(Long userId) {
        Query query = DAO.createNativeQueryWithParams("select count(*) from book_favorited where user_id = ?", userId);
        int count = ((BigInteger)query.getSingleResult()).intValue();
        return count;
    }
    
    public static int getUserReadingCount(Long userId) {
        Query query = DAO.createNativeQueryWithParams("select count(*) from book_reading where user_id = ?", userId);
        int count = ((BigInteger)query.getSingleResult()).intValue();
        return count;
    }
    
    public static int getUserWishlistCount(Long userId) {
        Query query = DAO.createNativeQueryWithParams("select count(*) from book_wishlisted where user_id = ?", userId);
        int count = ((BigInteger)query.getSingleResult()).intValue();
        return count;
    }
    
    public static int getUserPosdtaCount(Long userId) {
        Query query = DAO.createNativeQueryWithParams("select count(*) from posdta where user_id = ?", userId);
        int count = ((BigInteger)query.getSingleResult()).intValue();
        return count;
    }
    
    public static void saveEvent(Event e) {
        DAO.put(e);
    }
    
    public static void saveEventWithBook(EventWithBook e) {
        Date d = new Date(System.currentTimeMillis() - (24 * 60 * 60 * 1000));
        Query q = createQuery("SELECT e FROM EventWithUser e where user = ? AND type = ? AND target = ? AND eventDate > ?", e.getUser(), e.getEventType(), e.getBook(), d);
        if(q.getResultList().isEmpty()) {
            saveEvent(e);
        }
    }
    
    public static void saveEventWithUser(EventWithUser e) {
        Date d = new Date(System.currentTimeMillis() - (24 * 60 * 60 * 1000));
        Query q = createQuery("SELECT e FROM EventWithUser e where user = ? AND type = ? AND target = ? AND eventDate > ?", e.getUser(), e.getEventType(), e.getTarget(), d);
        if(q.getResultList().isEmpty()) {
            saveEvent(e);
        }
    }

    public static List<CommentThread> getClubThreads(Club club, int first, int limit) {
        Query q = createQuery("SELECT t FROM ClubThread t where club = ? order by id desc", club);
        return q.getResultList();
    }

    public static List<CommentThread> getBookThreads(Book book, int first, int limit) {
        Query q = createQuery("SELECT t FROM BookThread t where book = ? order by id desc", book);
        return q.getResultList();
    }

    public static List<Comment> getRepliesForComment(Comment comment) {
        Query q = createQuery("SELECT c FROM Comment c where parent.id = ? order by id desc", comment.getId());
        return q.getResultList();
    }

    public static CommentTree getBookCommentTree(Book book) {
        List<CommentThread> bookThreads = getBookThreads(book, 0, 50);
        return getCommentTree(bookThreads);
    }

    public static CommentTree getClubCommentTree(Club club) {
        List<CommentThread> clubThreads = getClubThreads(club, 0, 50);
        return getCommentTree(clubThreads);
    }

    public static CommentTree getCommentTree(List<CommentThread> threads) {
        List<CommentNode> nodes = new ArrayList<>();
        for(int i = 0; i < threads.size(); i++) {
            Comment comment = getFirstComment(threads.get(i));
            CommentNode node = getCommentNodes(comment);
            nodes.add(node);
        }
        return CommentTree.createCommentTree(nodes);
    }

    public static CommentNode getCommentNodes(Comment comment) {
        List<Comment> rawReplies = getRepliesForComment(comment);
        List<CommentNode> replyTree = new ArrayList<CommentNode>();
        for(int i = 0; i < rawReplies.size(); i++) {
            replyTree.add(getCommentNodes(rawReplies.get(i)));
        }
        return CommentNode.createCommentNode(comment, replyTree);
    }

    public static Comment getFirstComment(CommentThread thread) {
        Query q = createQuery("SELECT c FROM Comment c WHERE c.parent is null AND c.thread = ? ", thread);
        return (Comment)q.getResultList().get(0);
    }

    public static List<User> getClubMembers(Club club, int first, int limit) {
        Query q = createQuery("Select m.id.user FROM ClubMembership m WHERE m.id.club =  ? AND ( m.status = ? OR m.status IS NULL)", club, ClubMembership.MembershipStatus.MEMBER);
        q.setFirstResult(first);
        q.setMaxResults(limit);
        return q.getResultList();
    }

    public static List<Club> getUserClubs(User user, int first, int limit) {
        Query q = createQuery("Select m.id.club FROM ClubMembership m WHERE m.id.user =  ?", user);
        q.setFirstResult(first);
        q.setMaxResults(limit);
        return q.getResultList();
    }

    public static List<ClubReading> getClubReadingByStatus(Club club, ReadingStatus status, int first, int limit, String orderBy) {
        Query q = createQuery("Select r FROM ClubReading r WHERE r.club =  ? AND r.status = ? " + orderBy, club, status);
        q.setFirstResult(first);
        q.setMaxResults(limit);
        return q.getResultList();
    }

    public static List<ClubReading> getClubReadingByBook(Club club, Book book, ReadingStatus status) {
        Query q = createQuery("SELECT r FROM ClubReading r WHERE r.club = ? AND r.book = ? AND r.status = ?", club, book, status);
        return q.getResultList();
    }

    public static List<ClubReading> getClubWishlisted(Club club, int first, int limit) {
        return getClubReadingByStatus(club, ReadingStatus.WISHLISTED, first, limit, "order by r.wishlisted asc");
    }

    public static List<ClubReading> getClubReading(Club club, int first, int limit) {
        return getClubReadingByStatus(club, ReadingStatus.READING, first, limit, "order by r.reading asc");
    }

    public static List<ClubReading> getClubFinished(Club club, int first, int limit) {
        return getClubReadingByStatus(club, ReadingStatus.FINISHED, first, limit, "order by r.finished asc");
    }

    /* Book similarities */
    public static BookSimilarity getBookSimilarityForBook(Book original, Book similar) {
        Query q = createQuery("SELECT s FROM BookSimilarity s WHERE s.original = ? AND s.similar = ?", original, similar);
        List<Object> r = q.getResultList();
        if(r.isEmpty()) return null;
        return (BookSimilarity)r.get(0);
    }

    public static List<BookSimilarity> getSimilarBooks(Book book) {
        Query q = createQuery("SELECT s FROM BookSimilarity s WHERE s.original = ? ", book);
        return q.getResultList();
    }

    public static BookSimilarityVote getBookSimilarityVoteForUser(BookSimilarity similarity, User user) {
        return getUnique(BookSimilarityVote.class, " WHERE obj.id.similarity = ? AND obj.id.user = ?", similarity, user);
    }
    
}
