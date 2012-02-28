/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Marco
 */
@Embeddable
public class BestellungPK implements Serializable
{
    @Basic(optional = false)
    @Column(name = "kundeid")
    private int kundeid;
    @Basic(optional = false)
    @Column(name = "artikelid")
    private int artikelid;
    @Basic(optional = false)
    @Column(name = "datum")
    @Temporal(TemporalType.DATE)
    private Date datum;

    public BestellungPK()
    {
    }

    public BestellungPK(int kundeid, int artikelid, Date datum)
    {
        this.kundeid = kundeid;
        this.artikelid = artikelid;
        this.datum = datum;
    }

    public int getKundeid()
    {
        return kundeid;
    }

    public void setKundeid(int kundeid)
    {
        this.kundeid = kundeid;
    }

    public int getArtikelid()
    {
        return artikelid;
    }

    public void setArtikelid(int artikelid)
    {
        this.artikelid = artikelid;
    }

    public Date getDatum()
    {
        return datum;
    }

    public void setDatum(Date datum)
    {
        this.datum = datum;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (int) kundeid;
        hash += (int) artikelid;
        hash += (datum != null ? datum.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BestellungPK))
        {
            return false;
        }
        BestellungPK other = (BestellungPK) object;
        if (this.kundeid != other.kundeid)
        {
            return false;
        }
        if (this.artikelid != other.artikelid)
        {
            return false;
        }
        if ((this.datum == null && other.datum != null) || (this.datum != null && !this.datum.equals(other.datum)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "admin.BestellungPK[ kundeid=" + kundeid + ", artikelid=" + artikelid + ", datum=" + datum + " ]";
    }
    
}
