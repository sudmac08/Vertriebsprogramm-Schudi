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
public class ArtikelJpaController implements Serializable {

    public ArtikelJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Artikel artikel) {
        if (artikel.getPreisCollection() == null) {
            artikel.setPreisCollection(new ArrayList<Preis>());
        }
        if (artikel.getBestellungCollection() == null) {
            artikel.setBestellungCollection(new ArrayList<Bestellung>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Preis> attachedPreisCollection = new ArrayList<Preis>();
            for (Preis preisCollectionPreisToAttach : artikel.getPreisCollection()) {
                preisCollectionPreisToAttach = em.getReference(preisCollectionPreisToAttach.getClass(), preisCollectionPreisToAttach.getPreisPK());
                attachedPreisCollection.add(preisCollectionPreisToAttach);
            }
            artikel.setPreisCollection(attachedPreisCollection);
            Collection<Bestellung> attachedBestellungCollection = new ArrayList<Bestellung>();
            for (Bestellung bestellungCollectionBestellungToAttach : artikel.getBestellungCollection()) {
                bestellungCollectionBestellungToAttach = em.getReference(bestellungCollectionBestellungToAttach.getClass(), bestellungCollectionBestellungToAttach.getBestellungPK());
                attachedBestellungCollection.add(bestellungCollectionBestellungToAttach);
            }
            artikel.setBestellungCollection(attachedBestellungCollection);
            em.persist(artikel);
            for (Preis preisCollectionPreis : artikel.getPreisCollection()) {
                Artikel oldArtikelOfPreisCollectionPreis = preisCollectionPreis.getArtikel();
                preisCollectionPreis.setArtikel(artikel);
                preisCollectionPreis = em.merge(preisCollectionPreis);
                if (oldArtikelOfPreisCollectionPreis != null) {
                    oldArtikelOfPreisCollectionPreis.getPreisCollection().remove(preisCollectionPreis);
                    oldArtikelOfPreisCollectionPreis = em.merge(oldArtikelOfPreisCollectionPreis);
                }
            }
            for (Bestellung bestellungCollectionBestellung : artikel.getBestellungCollection()) {
                Artikel oldArtikelOfBestellungCollectionBestellung = bestellungCollectionBestellung.getArtikel();
                bestellungCollectionBestellung.setArtikel(artikel);
                bestellungCollectionBestellung = em.merge(bestellungCollectionBestellung);
                if (oldArtikelOfBestellungCollectionBestellung != null) {
                    oldArtikelOfBestellungCollectionBestellung.getBestellungCollection().remove(bestellungCollectionBestellung);
                    oldArtikelOfBestellungCollectionBestellung = em.merge(oldArtikelOfBestellungCollectionBestellung);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Artikel artikel) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Artikel persistentArtikel = em.find(Artikel.class, artikel.getArtikelid());
            Collection<Preis> preisCollectionOld = persistentArtikel.getPreisCollection();
            Collection<Preis> preisCollectionNew = artikel.getPreisCollection();
            Collection<Bestellung> bestellungCollectionOld = persistentArtikel.getBestellungCollection();
            Collection<Bestellung> bestellungCollectionNew = artikel.getBestellungCollection();
            List<String> illegalOrphanMessages = null;
            for (Preis preisCollectionOldPreis : preisCollectionOld) {
                if (!preisCollectionNew.contains(preisCollectionOldPreis)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Preis " + preisCollectionOldPreis + " since its artikel field is not nullable.");
                }
            }
            for (Bestellung bestellungCollectionOldBestellung : bestellungCollectionOld) {
                if (!bestellungCollectionNew.contains(bestellungCollectionOldBestellung)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Bestellung " + bestellungCollectionOldBestellung + " since its artikel field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Preis> attachedPreisCollectionNew = new ArrayList<Preis>();
            for (Preis preisCollectionNewPreisToAttach : preisCollectionNew) {
                preisCollectionNewPreisToAttach = em.getReference(preisCollectionNewPreisToAttach.getClass(), preisCollectionNewPreisToAttach.getPreisPK());
                attachedPreisCollectionNew.add(preisCollectionNewPreisToAttach);
            }
            preisCollectionNew = attachedPreisCollectionNew;
            artikel.setPreisCollection(preisCollectionNew);
            Collection<Bestellung> attachedBestellungCollectionNew = new ArrayList<Bestellung>();
            for (Bestellung bestellungCollectionNewBestellungToAttach : bestellungCollectionNew) {
                bestellungCollectionNewBestellungToAttach = em.getReference(bestellungCollectionNewBestellungToAttach.getClass(), bestellungCollectionNewBestellungToAttach.getBestellungPK());
                attachedBestellungCollectionNew.add(bestellungCollectionNewBestellungToAttach);
            }
            bestellungCollectionNew = attachedBestellungCollectionNew;
            artikel.setBestellungCollection(bestellungCollectionNew);
            artikel = em.merge(artikel);
            for (Preis preisCollectionNewPreis : preisCollectionNew) {
                if (!preisCollectionOld.contains(preisCollectionNewPreis)) {
                    Artikel oldArtikelOfPreisCollectionNewPreis = preisCollectionNewPreis.getArtikel();
                    preisCollectionNewPreis.setArtikel(artikel);
                    preisCollectionNewPreis = em.merge(preisCollectionNewPreis);
                    if (oldArtikelOfPreisCollectionNewPreis != null && !oldArtikelOfPreisCollectionNewPreis.equals(artikel)) {
                        oldArtikelOfPreisCollectionNewPreis.getPreisCollection().remove(preisCollectionNewPreis);
                        oldArtikelOfPreisCollectionNewPreis = em.merge(oldArtikelOfPreisCollectionNewPreis);
                    }
                }
            }
            for (Bestellung bestellungCollectionNewBestellung : bestellungCollectionNew) {
                if (!bestellungCollectionOld.contains(bestellungCollectionNewBestellung)) {
                    Artikel oldArtikelOfBestellungCollectionNewBestellung = bestellungCollectionNewBestellung.getArtikel();
                    bestellungCollectionNewBestellung.setArtikel(artikel);
                    bestellungCollectionNewBestellung = em.merge(bestellungCollectionNewBestellung);
                    if (oldArtikelOfBestellungCollectionNewBestellung != null && !oldArtikelOfBestellungCollectionNewBestellung.equals(artikel)) {
                        oldArtikelOfBestellungCollectionNewBestellung.getBestellungCollection().remove(bestellungCollectionNewBestellung);
                        oldArtikelOfBestellungCollectionNewBestellung = em.merge(oldArtikelOfBestellungCollectionNewBestellung);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = artikel.getArtikelid();
                if (findArtikel(id) == null) {
                    throw new NonexistentEntityException("The artikel with id " + id + " no longer exists.");
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
            Artikel artikel;
            try {
                artikel = em.getReference(Artikel.class, id);
                artikel.getArtikelid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The artikel with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Preis> preisCollectionOrphanCheck = artikel.getPreisCollection();
            for (Preis preisCollectionOrphanCheckPreis : preisCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Artikel (" + artikel + ") cannot be destroyed since the Preis " + preisCollectionOrphanCheckPreis + " in its preisCollection field has a non-nullable artikel field.");
            }
            Collection<Bestellung> bestellungCollectionOrphanCheck = artikel.getBestellungCollection();
            for (Bestellung bestellungCollectionOrphanCheckBestellung : bestellungCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Artikel (" + artikel + ") cannot be destroyed since the Bestellung " + bestellungCollectionOrphanCheckBestellung + " in its bestellungCollection field has a non-nullable artikel field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(artikel);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Artikel> findArtikelEntities() {
        return findArtikelEntities(true, -1, -1);
    }

    public List<Artikel> findArtikelEntities(int maxResults, int firstResult) {
        return findArtikelEntities(false, maxResults, firstResult);
    }

    private List<Artikel> findArtikelEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Artikel.class));
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

    public Artikel findArtikel(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Artikel.class, id);
        } finally {
            em.close();
        }
    }

    public int getArtikelCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Artikel> rt = cq.from(Artikel.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
