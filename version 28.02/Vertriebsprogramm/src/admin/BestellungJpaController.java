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
 * @author Marco
 */
public class BestellungJpaController implements Serializable
{

    public BestellungJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Bestellung bestellung) throws PreexistingEntityException, Exception
    {
        if (bestellung.getBestellungPK() == null)
        {
            bestellung.setBestellungPK(new BestellungPK());
        }
        bestellung.getBestellungPK().setKundeid(bestellung.getKunde().getKundeid());
        bestellung.getBestellungPK().setArtikelid(bestellung.getArtikel().getArtikelid());
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Kunde kunde = bestellung.getKunde();
            if (kunde != null)
            {
                kunde = em.getReference(kunde.getClass(), kunde.getKundeid());
                bestellung.setKunde(kunde);
            }
            Artikel artikel = bestellung.getArtikel();
            if (artikel != null)
            {
                artikel = em.getReference(artikel.getClass(), artikel.getArtikelid());
                bestellung.setArtikel(artikel);
            }
            em.persist(bestellung);
            if (kunde != null)
            {
                kunde.getBestellungCollection().add(bestellung);
                kunde = em.merge(kunde);
            }
            if (artikel != null)
            {
                artikel.getBestellungCollection().add(bestellung);
                artikel = em.merge(artikel);
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            if (findBestellung(bestellung.getBestellungPK()) != null)
            {
                throw new PreexistingEntityException("Bestellung " + bestellung + " already exists.", ex);
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

    public void edit(Bestellung bestellung) throws NonexistentEntityException, Exception
    {
        bestellung.getBestellungPK().setKundeid(bestellung.getKunde().getKundeid());
        bestellung.getBestellungPK().setArtikelid(bestellung.getArtikel().getArtikelid());
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Bestellung persistentBestellung = em.find(Bestellung.class, bestellung.getBestellungPK());
            Kunde kundeOld = persistentBestellung.getKunde();
            Kunde kundeNew = bestellung.getKunde();
            Artikel artikelOld = persistentBestellung.getArtikel();
            Artikel artikelNew = bestellung.getArtikel();
            if (kundeNew != null)
            {
                kundeNew = em.getReference(kundeNew.getClass(), kundeNew.getKundeid());
                bestellung.setKunde(kundeNew);
            }
            if (artikelNew != null)
            {
                artikelNew = em.getReference(artikelNew.getClass(), artikelNew.getArtikelid());
                bestellung.setArtikel(artikelNew);
            }
            bestellung = em.merge(bestellung);
            if (kundeOld != null && !kundeOld.equals(kundeNew))
            {
                kundeOld.getBestellungCollection().remove(bestellung);
                kundeOld = em.merge(kundeOld);
            }
            if (kundeNew != null && !kundeNew.equals(kundeOld))
            {
                kundeNew.getBestellungCollection().add(bestellung);
                kundeNew = em.merge(kundeNew);
            }
            if (artikelOld != null && !artikelOld.equals(artikelNew))
            {
                artikelOld.getBestellungCollection().remove(bestellung);
                artikelOld = em.merge(artikelOld);
            }
            if (artikelNew != null && !artikelNew.equals(artikelOld))
            {
                artikelNew.getBestellungCollection().add(bestellung);
                artikelNew = em.merge(artikelNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                BestellungPK id = bestellung.getBestellungPK();
                if (findBestellung(id) == null)
                {
                    throw new NonexistentEntityException("The bestellung with id " + id + " no longer exists.");
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

    public void destroy(BestellungPK id) throws NonexistentEntityException
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Bestellung bestellung;
            try
            {
                bestellung = em.getReference(Bestellung.class, id);
                bestellung.getBestellungPK();
            } catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The bestellung with id " + id + " no longer exists.", enfe);
            }
            Kunde kunde = bestellung.getKunde();
            if (kunde != null)
            {
                kunde.getBestellungCollection().remove(bestellung);
                kunde = em.merge(kunde);
            }
            Artikel artikel = bestellung.getArtikel();
            if (artikel != null)
            {
                artikel.getBestellungCollection().remove(bestellung);
                artikel = em.merge(artikel);
            }
            em.remove(bestellung);
            em.getTransaction().commit();
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public List<Bestellung> findBestellungEntities()
    {
        return findBestellungEntities(true, -1, -1);
    }

    public List<Bestellung> findBestellungEntities(int maxResults, int firstResult)
    {
        return findBestellungEntities(false, maxResults, firstResult);
    }

    private List<Bestellung> findBestellungEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bestellung.class));
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

    public Bestellung findBestellung(BestellungPK id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Bestellung.class, id);
        } finally
        {
            em.close();
        }
    }

    public int getBestellungCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bestellung> rt = cq.from(Bestellung.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally
        {
            em.close();
        }
    }
    
}
