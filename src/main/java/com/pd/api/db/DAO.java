package com.pd.api.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.pd.api.entity.Book;
import com.pd.api.entity.BookRating;
import com.pd.api.entity.Language;
import com.pd.api.entity.Role;
import com.pd.api.entity.User;
import com.pd.api.entity.VerificationToken;
import com.pd.api.entity.aux.BookInfo;

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
        em.getTransaction().begin();
        if (merge) {
            obj = em.merge(obj);
        }
        em.persist(obj);
        em.flush();
        em.getTransaction().commit();
        return obj;
    }
    
    public static <T> T merge(T obj) {
        EntityManager em = getEM();
        obj = em.merge(obj);
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
        return getAll(type, "", "", first, limit);
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
        Query q = createQuery("Select c from Credential c where username = ?", username);
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
    
    public static User getUserByUsername(String username) {
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
    
    public static BookRating getBookRating(Long bookId) {
        BookRating rating = DAO.get(BookRating.class, bookId);
        if(rating == null) {
            //Create a new rating and save it
            Book book = get(Book.class, bookId);
            if(book == null) {
                return null;
            }
            rating = new BookRating(book.getId());
            //Maybe try to update here?
            rating = put(rating);
            book.setRating(rating);
            book = put(book);
        }
        return rating;
    }
    
    public static BookRating getBookRating(Book book) {
        BookRating rating = get(BookRating.class, book.getId());
        if(rating == null) {
            rating = new BookRating(book.getId());
            rating = put(rating);
            book.setRating(rating);
            book = put(book);
        }
        return rating;
    }
    
    //TODO: fix this method
    public static BookInfo getBookInfo(Book book) {
        return null;
    }
    
    //TODO: fix this method 
    public static BookInfo getBookInfo(Long bookId) {
        Book book = get(Book.class, bookId);
        BookRating rating = getBookRating(bookId);
        BookInfo info = new BookInfo(rating, book);
        return info;
    }
}
