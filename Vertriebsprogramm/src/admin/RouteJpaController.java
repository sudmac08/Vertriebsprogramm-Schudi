/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import admin.exceptions.IllegalOrphanException;
import admin.exceptions.NonexistentEntityException;
import admin.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Marco
 */
public class RouteJpaController implements Serializable
{

    public RouteJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Route route) throws PreexistingEntityException, Exception
    {
        if (route.getKundeCollection() == null)
        {
            route.setKundeCollection(new ArrayList<Kunde>());
        }
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Kunde> attachedKundeCollection = new ArrayList<Kunde>();
            for (Kunde kundeCollectionKundeToAttach : route.getKundeCollection())
            {
                kundeCollectionKundeToAttach = em.getReference(kundeCollectionKundeToAttach.getClass(), kundeCollectionKundeToAttach.getKundeid());
                attachedKundeCollection.add(kundeCollectionKundeToAttach);
            }
            route.setKundeCollection(attachedKundeCollection);
            em.persist(route);
            for (Kunde kundeCollectionKunde : route.getKundeCollection())
            {
                Route oldRouteidOfKundeCollectionKunde = kundeCollectionKunde.getRouteid();
                kundeCollectionKunde.setRouteid(route);
                kundeCollectionKunde = em.merge(kundeCollectionKunde);
                if (oldRouteidOfKundeCollectionKunde != null)
                {
                    oldRouteidOfKundeCollectionKunde.getKundeCollection().remove(kundeCollectionKunde);
                    oldRouteidOfKundeCollectionKunde = em.merge(oldRouteidOfKundeCollectionKunde);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            if (findRoute(route.getRouteid()) != null)
            {
                throw new PreexistingEntityException("Route " + route + " already exists.", ex);
            }
            throw ex;
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public void edit(Route route) throws IllegalOrphanException, NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Route persistentRoute = em.find(Route.class, route.getRouteid());
            Collection<Kunde> kundeCollectionOld = persistentRoute.getKundeCollection();
            Collection<Kunde> kundeCollectionNew = route.getKundeCollection();
            List<String> illegalOrphanMessages = null;
            for (Kunde kundeCollectionOldKunde : kundeCollectionOld)
            {
                if (!kundeCollectionNew.contains(kundeCollectionOldKunde))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Kunde " + kundeCollectionOldKunde + " since its routeid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Kunde> attachedKundeCollectionNew = new ArrayList<Kunde>();
            for (Kunde kundeCollectionNewKundeToAttach : kundeCollectionNew)
            {
                kundeCollectionNewKundeToAttach = em.getReference(kundeCollectionNewKundeToAttach.getClass(), kundeCollectionNewKundeToAttach.getKundeid());
                attachedKundeCollectionNew.add(kundeCollectionNewKundeToAttach);
            }
            kundeCollectionNew = attachedKundeCollectionNew;
            route.setKundeCollection(kundeCollectionNew);
            route = em.merge(route);
            for (Kunde kundeCollectionNewKunde : kundeCollectionNew)
            {
                if (!kundeCollectionOld.contains(kundeCollectionNewKunde))
                {
                    Route oldRouteidOfKundeCollectionNewKunde = kundeCollectionNewKunde.getRouteid();
                    kundeCollectionNewKunde.setRouteid(route);
                    kundeCollectionNewKunde = em.merge(kundeCollectionNewKunde);
                    if (oldRouteidOfKundeCollectionNewKunde != null && !oldRouteidOfKundeCollectionNewKunde.equals(route))
                    {
                        oldRouteidOfKundeCollectionNewKunde.getKundeCollection().remove(kundeCollectionNewKunde);
                        oldRouteidOfKundeCollectionNewKunde = em.merge(oldRouteidOfKundeCollectionNewKunde);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                Integer id = route.getRouteid();
                if (findRoute(id) == null)
                {
                    throw new NonexistentEntityException("The route with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Route route;
            try
            {
                route = em.getReference(Route.class, id);
                route.getRouteid();
            } catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The route with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Kunde> kundeCollectionOrphanCheck = route.getKundeCollection();
            for (Kunde kundeCollectionOrphanCheckKunde : kundeCollectionOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Route (" + route + ") cannot be destroyed since the Kunde " + kundeCollectionOrphanCheckKunde + " in its kundeCollection field has a non-nullable routeid field.");
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(route);
            em.getTransaction().commit();
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public List<Route> findRouteEntities()
    {
        return findRouteEntities(true, -1, -1);
    }

    public List<Route> findRouteEntities(int maxResults, int firstResult)
    {
        return findRouteEntities(false, maxResults, firstResult);
    }

    private List<Route> findRouteEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Route.class));
            Query q = em.createQuery(cq);
            if (!all)
            {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally
        {
            em.close();
        }
    }

    public Route findRoute(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Route.class, id);
        } finally
        {
            em.close();
        }
    }

    public int getRouteCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Route> rt = cq.from(Route.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally
        {
            em.close();
        }
    }
    
}
