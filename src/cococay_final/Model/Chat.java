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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author joaocosta-ipvc
 */
@Entity
@Table(name = "CHAT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Chat.findAll", query = "SELECT c FROM Chat c")
    , @NamedQuery(name = "Chat.findByIdChat", query = "SELECT c FROM Chat c WHERE c.idChat = :idChat")
    , @NamedQuery(name = "Chat.findByEstado", query = "SELECT c FROM Chat c WHERE c.estado = :estado")})
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CHAT")
    private Long idChat;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private short estado;
    @JoinTable(name = "MEMBROS_CHAT", joinColumns = {
        @JoinColumn(name = "ID_CHAT", referencedColumnName = "ID_CHAT")}, inverseJoinColumns = {
        @JoinColumn(name = "ID_FUNCIONARIO", referencedColumnName = "ID_FUNCIONARIO")})
    @ManyToMany
    private Collection<Funcionario> funcionarioCollection;
    @OneToMany(mappedBy = "idChat")
    private Collection<MensagemChat> mensagemChatCollection;

    public Chat() {
    }

    public Chat(Long idChat) {
        this.idChat = idChat;
    }

    public Chat(Long idChat, short estado) {
        this.idChat = idChat;
        this.estado = estado;
    }

    public Long getIdChat() {
        return idChat;
    }

    public void setIdChat(Long idChat) {
        this.idChat = idChat;
    }

    public short getEstado() {
        return estado;
    }

    public void setEstado(short estado) {
        this.estado = estado;
    }

    @XmlTransient
    public Collection<Funcionario> getFuncionarioCollection() {
        return funcionarioCollection;
    }

    public void setFuncionarioCollection(Collection<Funcionario> funcionarioCollection) {
        this.funcionarioCollection = funcionarioCollection;
    }

    @XmlTransient
    public Collection<MensagemChat> getMensagemChatCollection() {
        return mensagemChatCollection;
    }

    public void setMensagemChatCollection(Collection<MensagemChat> mensagemChatCollection) {
        this.mensagemChatCollection = mensagemChatCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idChat != null ? idChat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Chat)) {
            return false;
        }
        Chat other = (Chat) object;
        if ((this.idChat == null && other.idChat != null) || (this.idChat != null && !this.idChat.equals(other.idChat))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cococay_final.Chat[ idChat=" + idChat + " ]";
    }
    
}
