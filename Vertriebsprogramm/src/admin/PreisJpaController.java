/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

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

/**
 *
 * @author Mike
 */
public class PreisJpaController implements Serializable {

    public PreisJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Preis preis) throws PreexistingEntityException, Exception {
        if (preis.getPreisPK() == null) {
            preis.setPreisPK(new PreisPK());
        }
        preis.getPreisPK().setArtikelid(preis.getArtikel().getArtikelid());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Artikel artikel = preis.getArtikel();
            if (artikel != null) {
                artikel = em.getReference(artikel.getClass(), artikel.getArtikelid());
                preis.setArtikel(artikel);
            }
            em.persist(preis);
            if (artikel != null) {
                artikel.getPreisCollection().add(preis);
                artikel = em.merge(artikel);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPreis(preis.getPreisPK()) != null) {
                throw new PreexistingEntityException("Preis " + preis + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Preis preis) throws NonexistentEntityException, Exception {
        preis.getPreisPK().setArtikelid(preis.getArtikel().getArtikelid());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Preis persistentPreis = em.find(Preis.class, preis.getPreisPK());
            Artikel artikelOld = persistentPreis.getArtikel();
            Artikel artikelNew = preis.getArtikel();
            if (artikelNew != null) {
                artikelNew = em.getReference(artikelNew.getClass(), artikelNew.getArtikelid());
                preis.setArtikel(artikelNew);
            }
            preis = em.merge(preis);
            if (artikelOld != null && !artikelOld.equals(artikelNew)) {
                artikelOld.getPreisCollection().remove(preis);
                artikelOld = em.merge(artikelOld);
            }
            if (artikelNew != null && !artikelNew.equals(artikelOld)) {
                artikelNew.getPreisCollection().add(preis);
                artikelNew = em.merge(artikelNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PreisPK id = preis.getPreisPK();
                if (findPreis(id) == null) {
                    throw new NonexistentEntityException("The preis with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PreisPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Preis preis;
            try {
                preis = em.getReference(Preis.class, id);
                preis.getPreisPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The preis with id " + id + " no longer exists.", enfe);
            }
            Artikel artikel = preis.getArtikel();
            if (artikel != null) {
                artikel.getPreisCollection().remove(preis);
                artikel = em.merge(artikel);
            }
            em.remove(preis);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Preis> findPreisEntities() {
        return findPreisEntities(true, -1, -1);
    }

    public List<Preis> findPreisEntities(int maxResults, int firstResult) {
        return findPreisEntities(false, maxResults, firstResult);
    }

    private List<Preis> findPreisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Preis.class));
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

    public Preis findPreis(PreisPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Preis.class, id);
        } finally {
            em.close();
        }
    }

    public int getPreisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Preis> rt = cq.from(Preis.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
