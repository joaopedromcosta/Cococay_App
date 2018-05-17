/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cococay_final.Model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author joaocosta-ipvc
 */
@Entity
@Table(name = "LOGIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Login.findAll", query = "SELECT l FROM Login l")
    , @NamedQuery(name = "Login.findByUsername", query = "SELECT l FROM Login l WHERE l.username = :username")
    , @NamedQuery(name = "Login.findByPasswordField", query = "SELECT l FROM Login l WHERE l.passwordField = :passwordField")
    , @NamedQuery(name = "Login.findByPerguntaSeguranca1", query = "SELECT l FROM Login l WHERE l.perguntaSeguranca1 = :perguntaSeguranca1")
    , @NamedQuery(name = "Login.findByRespostaSeguranca1", query = "SELECT l FROM Login l WHERE l.respostaSeguranca1 = :respostaSeguranca1")
    , @NamedQuery(name = "Login.findByPerguntaSeguranca2", query = "SELECT l FROM Login l WHERE l.perguntaSeguranca2 = :perguntaSeguranca2")
    , @NamedQuery(name = "Login.findByRespostaSeguranca2", query = "SELECT l FROM Login l WHERE l.respostaSeguranca2 = :respostaSeguranca2")
    , @NamedQuery(name = "Login.findByEstado", query = "SELECT l FROM Login l WHERE l.estado = :estado")})
public class Login implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @Column(name = "PASSWORD_FIELD")
    private String passwordField;
    @Basic(optional = false)
    @Column(name = "PERGUNTA_SEGURANCA1")
    private String perguntaSeguranca1;
    @Basic(optional = false)
    @Column(name = "RESPOSTA_SEGURANCA1")
    private String respostaSeguranca1;
    @Basic(optional = false)
    @Column(name = "PERGUNTA_SEGURANCA2")
    private String perguntaSeguranca2;
    @Basic(optional = false)
    @Column(name = "RESPOSTA_SEGURANCA2")
    private String respostaSeguranca2;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private short estado;
    @JoinColumn(name = "ID_FUNCIONARIO", referencedColumnName = "ID_FUNCIONARIO")
    @ManyToOne
    private Funcionario idFuncionario;

    public Login() {
    }

    public Login(String username) {
        this.username = username;
    }

    public Login(String username, String passwordField, String perguntaSeguranca1, String respostaSeguranca1, String perguntaSeguranca2, String respostaSeguranca2, short estado) {
        this.username = username;
        this.passwordField = passwordField;
        this.perguntaSeguranca1 = perguntaSeguranca1;
        this.respostaSeguranca1 = respostaSeguranca1;
        this.perguntaSeguranca2 = perguntaSeguranca2;
        this.respostaSeguranca2 = respostaSeguranca2;
        this.estado = estado;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(String passwordField) {
        this.passwordField = passwordField;
    }

    public String getPerguntaSeguranca1() {
        return perguntaSeguranca1;
    }

    public void setPerguntaSeguranca1(String perguntaSeguranca1) {
        this.perguntaSeguranca1 = perguntaSeguranca1;
    }

    public String getRespostaSeguranca1() {
        return respostaSeguranca1;
    }

    public void setRespostaSeguranca1(String respostaSeguranca1) {
        this.respostaSeguranca1 = respostaSeguranca1;
    }

    public String getPerguntaSeguranca2() {
        return perguntaSeguranca2;
    }

    public void setPerguntaSeguranca2(String perguntaSeguranca2) {
        this.perguntaSeguranca2 = perguntaSeguranca2;
    }

    public String getRespostaSeguranca2() {
        return respostaSeguranca2;
    }

    public void setRespostaSeguranca2(String respostaSeguranca2) {
        this.respostaSeguranca2 = respostaSeguranca2;
    }

    public short getEstado() {
        return estado;
    }

    public void setEstado(short estado) {
        this.estado = estado;
    }

    public Funcionario getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Funcionario idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Login)) {
            return false;
        }
        Login other = (Login) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cococay_final.Login[ username=" + username + " ]";
    }
    
}
