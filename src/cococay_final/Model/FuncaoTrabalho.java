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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author joaocosta-ipvc
 */
@Entity
@Table(name = "FUNCAO_TRABALHO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FuncaoTrabalho.findAll", query = "SELECT f FROM FuncaoTrabalho f")
    , @NamedQuery(name = "FuncaoTrabalho.findByIdFuncaoTrabalho", query = "SELECT f FROM FuncaoTrabalho f WHERE f.idFuncaoTrabalho = :idFuncaoTrabalho")
    , @NamedQuery(name = "FuncaoTrabalho.findByDesignacaoEquipa", query = "SELECT f FROM FuncaoTrabalho f WHERE f.designacaoEquipa = :designacaoEquipa")})
public class FuncaoTrabalho implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_FUNCAO_TRABALHO")
    private Long idFuncaoTrabalho;
    @Column(name = "DESIGNACAO_EQUIPA")
    private String designacaoEquipa;
    @OneToMany(mappedBy = "idFuncaoTrabalho")
    private Collection<Funcionario> funcionarioCollection;

    public FuncaoTrabalho() {
    }

    public FuncaoTrabalho(Long idFuncaoTrabalho) {
        this.idFuncaoTrabalho = idFuncaoTrabalho;
    }

    public Long getIdFuncaoTrabalho() {
        return idFuncaoTrabalho;
    }

    public void setIdFuncaoTrabalho(Long idFuncaoTrabalho) {
        this.idFuncaoTrabalho = idFuncaoTrabalho;
    }

    public String getDesignacaoEquipa() {
        return designacaoEquipa;
    }

    public void setDesignacaoEquipa(String designacaoEquipa) {
        this.designacaoEquipa = designacaoEquipa;
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
        hash += (idFuncaoTrabalho != null ? idFuncaoTrabalho.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FuncaoTrabalho)) {
            return false;
        }
        FuncaoTrabalho other = (FuncaoTrabalho) object;
        if ((this.idFuncaoTrabalho == null && other.idFuncaoTrabalho != null) || (this.idFuncaoTrabalho != null && !this.idFuncaoTrabalho.equals(other.idFuncaoTrabalho))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cococay_final.FuncaoTrabalho[ idFuncaoTrabalho=" + idFuncaoTrabalho + " ]";
    }
    
}
