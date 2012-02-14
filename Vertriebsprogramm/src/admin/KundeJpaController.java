/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import admin.exceptions.IllegalOrphanException;
import admin.exceptions.NonexistentEntityException;
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
 * @author Mike
 */
public class KundeJpaController implements Serializable {

    public KundeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Kunde kunde) {
        if (kunde.getBestellungCollection() == null) {
            kunde.setBestellungCollection(new ArrayList<Bestellung>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Route routeid = kunde.getRouteid();
            if (routeid != null) {
                routeid = em.getReference(routeid.getClass(), routeid.getRouteid());
                kunde.setRouteid(routeid);
            }
            Collection<Bestellung> attachedBestellungCollection = new ArrayList<Bestellung>();
            for (Bestellung bestellungCollectionBestellungToAttach : kunde.getBestellungCollection()) {
                bestellungCollectionBestellungToAttach = em.getReference(bestellungCollectionBestellungToAttach.getClass(), bestellungCollectionBestellungToAttach.getBestellungPK());
                attachedBestellungCollection.add(bestellungCollectionBestellungToAttach);
            }
            kunde.setBestellungCollection(attachedBestellungCollection);
            em.persist(kunde);
            if (routeid != null) {
                routeid.getKundeCollection().add(kunde);
                routeid = em.merge(routeid);
            }
            for (Bestellung bestellungCollectionBestellung : kunde.getBestellungCollection()) {
                Kunde oldKundeOfBestellungCollectionBestellung = bestellungCollectionBestellung.getKunde();
                bestellungCollectionBestellung.setKunde(kunde);
                bestellungCollectionBestellung = em.merge(bestellungCollectionBestellung);
                if (oldKundeOfBestellungCollectionBestellung != null) {
                    oldKundeOfBestellungCollectionBestellung.getBestellungCollection().remove(bestellungCollectionBestellung);
                    oldKundeOfBestellungCollectionBestellung = em.merge(oldKundeOfBestellungCollectionBestellung);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Kunde kunde) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Kunde persistentKunde = em.find(Kunde.class, kunde.getKundeid());
            Route routeidOld = persistentKunde.getRouteid();
            Route routeidNew = kunde.getRouteid();
            Collection<Bestellung> bestellungCollectionOld = persistentKunde.getBestellungCollection();
            Collection<Bestellung> bestellungCollectionNew = kunde.getBestellungCollection();
            List<String> illegalOrphanMessages = null;
            for (Bestellung bestellungCollectionOldBestellung : bestellungCollectionOld) {
                if (!bestellungCollectionNew.contains(bestellungCollectionOldBestellung)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Bestellung " + bestellungCollectionOldBestellung + " since its kunde field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (routeidNew != null) {
                routeidNew = em.getReference(routeidNew.getClass(), routeidNew.getRouteid());
                kunde.setRouteid(routeidNew);
            }
            Collection<Bestellung> attachedBestellungCollectionNew = new ArrayList<Bestellung>();
            for (Bestellung bestellungCollectionNewBestellungToAttach : bestellungCollectionNew) {
                bestellungCollectionNewBestellungToAttach = em.getReference(bestellungCollectionNewBestellungToAttach.getClass(), bestellungCollectionNewBestellungToAttach.getBestellungPK());
                attachedBestellungCollectionNew.add(bestellungCollectionNewBestellungToAttach);
            }
            bestellungCollectionNew = attachedBestellungCollectionNew;
            kunde.setBestellungCollection(bestellungCollectionNew);
            kunde = em.merge(kunde);
            if (routeidOld != null && !routeidOld.equals(routeidNew)) {
                routeidOld.getKundeCollection().remove(kunde);
                routeidOld = em.merge(routeidOld);
            }
            if (routeidNew != null && !routeidNew.equals(routeidOld)) {
                routeidNew.getKundeCollection().add(kunde);
                routeidNew = em.merge(routeidNew);
            }
            for (Bestellung bestellungCollectionNewBestellung : bestellungCollectionNew) {
                if (!bestellungCollectionOld.contains(bestellungCollectionNewBestellung)) {
                    Kunde oldKundeOfBestellungCollectionNewBestellung = bestellungCollectionNewBestellung.getKunde();
                    bestellungCollectionNewBestellung.setKunde(kunde);
                    bestellungCollectionNewBestellung = em.merge(bestellungCollectionNewBestellung);
                    if (oldKundeOfBestellungCollectionNewBestellung != null && !oldKundeOfBestellungCollectionNewBestellung.equals(kunde)) {
                        oldKundeOfBestellungCollectionNewBestellung.getBestellungCollection().remove(bestellungCollectionNewBestellung);
                        oldKundeOfBestellungCollectionNewBestellung = em.merge(oldKundeOfBestellungCollectionNewBestellung);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = kunde.getKundeid();
                if (findKunde(id) == null) {
                    throw new NonexistentEntityException("The kunde with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Kunde kunde;
            try {
                kunde = em.getReference(Kunde.class, id);
                kunde.getKundeid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The kunde with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Bestellung> bestellungCollectionOrphanCheck = kunde.getBestellungCollection();
            for (Bestellung bestellungCollectionOrphanCheckBestellung : bestellungCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Kunde (" + kunde + ") cannot be destroyed since the Bestellung " + bestellungCollectionOrphanCheckBestellung + " in its bestellungCollection field has a non-nullable kunde field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Route routeid = kunde.getRouteid();
            if (routeid != null) {
                routeid.getKundeCollection().remove(kunde);
                routeid = em.merge(routeid);
            }
            em.remove(kunde);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Kunde> findKundeEntities() {
        return findKundeEntities(true, -1, -1);
    }

    public List<Kunde> findKundeEntities(int maxResults, int firstResult) {
        return findKundeEntities(false, maxResults, firstResult);
    }

    private List<Kunde> findKundeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Kunde.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Kunde findKunde(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Kunde.class, id);
        } finally {
            em.close();
        }
    }

    public int getKundeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Kunde> rt = cq.from(Kunde.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
