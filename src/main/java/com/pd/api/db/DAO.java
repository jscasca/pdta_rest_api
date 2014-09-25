package com.pd.api.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.pd.api.entity.Role;

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
    
    public static <T> T get(Class<T> type, Long id) {
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
        Query q = getEM().createNativeQuery(query);
        for(int i = 0; i<params.length; i++)q.setParameter(i + 1, params[i]);
        return q;
    }
    
    //public static <T> List<T> getAll(Class<T> type, String query, ListWrapper lw) {return getAll(type, query, lw.orderBy, lw.first, lw.limit);}
    
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
    
    /*public static List<User> getUserFollowers(User u, ListWrapper lw) {
        return getAll(User.class, "Select user from Follower follower join follower.follower as user where follower.user.id = " + u.getId(), lw);
    }
    
    public static List<User> getUserFollowing(User u, ListWrapper lw) {
        return getAll(User.class, "Select user from Follower follower join follower.user as user where follower.follower.id = " + u.getId(), lw);
    }*/
    
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
}
