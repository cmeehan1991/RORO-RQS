/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PortPicker;

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
@Table(name = "port_scope", catalog = "kline", schema = "")
@NamedQueries({
    @NamedQuery(name = "PortScope.findAll", query = "SELECT p FROM PortScope p"),
    @NamedQuery(name = "PortScope.findById", query = "SELECT p FROM PortScope p WHERE p.id = :id"),
    @NamedQuery(name = "PortScope.findByPortSelected", query = "SELECT p FROM PortScope p WHERE p.portSelected = :portSelected"),
    @NamedQuery(name = "PortScope.findByPortName", query = "SELECT p FROM PortScope p WHERE p.portName = :portName"),
    @NamedQuery(name = "PortScope.findByPortCountry", query = "SELECT p FROM PortScope p WHERE p.portCountry = :portCountry"),
    @NamedQuery(name = "PortScope.findByPortUnloc", query = "SELECT p FROM PortScope p WHERE p.portUnloc = :portUnloc")})
public class PortScope implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "PORT_SELECTED")
    private Boolean portSelected;
    @Basic(optional = false)
    @Column(name = "PORT_NAME")
    private String portName;
    @Basic(optional = false)
    @Column(name = "PORT_COUNTRY")
    private String portCountry;
    @Basic(optional = false)
    @Column(name = "PORT_UNLOC")
    private String portUnloc;

    public PortScope() {
    }

    public PortScope(Integer id) {
        this.id = id;
    }

    public PortScope(Integer id, String portName, String portCountry, String portUnloc) {
        this.id = id;
        this.portName = portName;
        this.portCountry = portCountry;
        this.portUnloc = portUnloc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        Integer oldId = this.id;
        this.id = id;
        changeSupport.firePropertyChange("id", oldId, id);
    }

    public Boolean getPortSelected() {
        return portSelected;
    }

    public void setPortSelected(Boolean portSelected) {
        Boolean oldPortSelected = this.portSelected;
        this.portSelected = portSelected;
        changeSupport.firePropertyChange("portSelected", oldPortSelected, portSelected);
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        String oldPortName = this.portName;
        this.portName = portName;
        changeSupport.firePropertyChange("portName", oldPortName, portName);
    }

    public String getPortCountry() {
        return portCountry;
    }

    public void setPortCountry(String portCountry) {
        String oldPortCountry = this.portCountry;
        this.portCountry = portCountry;
        changeSupport.firePropertyChange("portCountry", oldPortCountry, portCountry);
    }

    public String getPortUnloc() {
        return portUnloc;
    }

    public void setPortUnloc(String portUnloc) {
        String oldPortUnloc = this.portUnloc;
        this.portUnloc = portUnloc;
        changeSupport.firePropertyChange("portUnloc", oldPortUnloc, portUnloc);
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
        if (!(object instanceof PortScope)) {
            return false;
        }
        PortScope other = (PortScope) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PortPicker.PortScope[ id=" + id + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
