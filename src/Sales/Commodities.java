/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sales;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author cmeehan
 */
@Entity
@Table(name = "commodities", catalog = "kline", schema = "")
@NamedQueries({
    @NamedQuery(name = "Commodities.findAll", query = "SELECT c FROM Commodities c"),
    @NamedQuery(name = "Commodities.findById", query = "SELECT c FROM Commodities c WHERE c.id = :id"),
    @NamedQuery(name = "Commodities.findByClass1", query = "SELECT c FROM Commodities c WHERE c.class1 = :class1"),
    @NamedQuery(name = "Commodities.findByCategory", query = "SELECT c FROM Commodities c WHERE c.category = :category")})
public class Commodities implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "CLASS")
    private String class1;
    @Column(name = "CATEGORY")
    private String category;

    public Commodities() {
    }

    public Commodities(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        Integer oldId = this.id;
        this.id = id;
        changeSupport.firePropertyChange("id", oldId, id);
    }

    public String getClass1() {
        return class1;
    }

    public void setClass1(String class1) {
        String oldClass1 = this.class1;
        this.class1 = class1;
        changeSupport.firePropertyChange("class1", oldClass1, class1);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        String oldCategory = this.category;
        this.category = category;
        changeSupport.firePropertyChange("category", oldCategory, category);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Commodities)) {
            return false;
        }
        Commodities other = (Commodities) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sales.Commodities[ id=" + id + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
