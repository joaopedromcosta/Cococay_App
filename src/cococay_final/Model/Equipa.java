/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cococay_final.Model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author joaocosta-ipvc
 */
@Entity
@Table(name = "EQUIPA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Equipa.findAll", query = "SELECT e FROM Equipa e")
    , @NamedQuery(name = "Equipa.findByIdEquipa", query = "SELECT e FROM Equipa e WHERE e.idEquipa = :idEquipa")
    , @NamedQuery(name = "Equipa.findByDesignacao", query = "SELECT e FROM Equipa e WHERE e.designacao = :designacao")
    , @NamedQuery(name = "Equipa.findByEstado", query = "SELECT e FROM Equipa e WHERE e.estado = :estado")
    , @NamedQuery(name = "Equipa.findByMinimoElementosTrabalhar", query = "SELECT e FROM Equipa e WHERE e.minimoElementosTrabalhar = :minimoElementosTrabalhar")})
public class Equipa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_EQUIPA")
    private Long idEquipa;
    @Basic(optional = false)
    @Column(name = "DESIGNACAO")
    private String designacao;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private short estado;
    @Basic(optional = false)
    @Column(name = "MINIMO_ELEMENTOS_TRABALHAR")
    private short minimoElementosTrabalhar;
    @JoinTable(name = "MEMBROS_EQUIPA", joinColumns = {
        @JoinColumn(name = "ID_EQUIPA", referencedColumnName = "ID_EQUIPA")}, inverseJoinColumns = {
        @JoinColumn(name = "ID_FUNCIONARIO", referencedColumnName = "ID_FUNCIONARIO")})
    @ManyToMany
    private Collection<Funcionario> funcionarioCollection;
    
    @Basic(optional = false)
    @Column(name = "ABREVIATURA")
    private String abreviatura;

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public Equipa() {
    }

    public Equipa(Long idEquipa) {
        this.idEquipa = idEquipa;
    }

    public Equipa(Long idEquipa, String designacao, short estado, short minimoElementosTrabalhar) {
        this.idEquipa = idEquipa;
        this.designacao = designacao;
        this.estado = estado;
        this.minimoElementosTrabalhar = minimoElementosTrabalhar;
    }

    public Long getIdEquipa() {
        return idEquipa;
    }

    public void setIdEquipa(Long idEquipa) {
        this.idEquipa = idEquipa;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public short getEstado() {
        return estado;
    }

    public void setEstado(short estado) {
        this.estado = estado;
    }

    public short getMinimoElementosTrabalhar() {
        return minimoElementosTrabalhar;
    }

    public void setMinimoElementosTrabalhar(short minimoElementosTrabalhar) {
        this.minimoElementosTrabalhar = minimoElementosTrabalhar;
    }

    @XmlTransient
    public Collection<Funcionario> getFuncionarioCollection() {
        return funcionarioCollection;
    }

    public void setFuncionarioCollection(Collection<Funcionario> funcionarioCollection) {
        this.funcionarioCollection = funcionarioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEquipa != null ? idEquipa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Equipa)) {
            return false;
        }
        Equipa other = (Equipa) object;
        if ((this.idEquipa == null && other.idEquipa != null) || (this.idEquipa != null && !this.idEquipa.equals(other.idEquipa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cococay_final.Equipa[ idEquipa=" + idEquipa + " ]";
    }

    
}
