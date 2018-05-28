/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cococay_final;

import cococay_final.Model.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 *
 * @author joaocosta-ipvc
 */
public class Repository {
    private static Repository repo = new Repository();
    EntityManager em;
    EntityManagerFactory emf;
    
    Funcionario loggedEmployee;
    List<Funcionario> funcionarios;
    List<Equipa> equipas;
    
    

    public Repository() {
        emf= Persistence.createEntityManagerFactory("Cococay_FinalPU");
        em=emf.createEntityManager();
        this.loggedEmployee = new Funcionario();
    }
    //Get repository instance
    public static Repository getSingleton(){ return repo; }
    //CRUD Operations
    
    //select method
    public ResultSet select(String select) throws SQLException{
        ResultSet result;
        
        em.getTransaction().begin();
        
        java.sql.Connection con = em.unwrap(java.sql.Connection.class);
        PreparedStatement st= con.prepareStatement(select);
        
        result=st.executeQuery();
        
        em.getTransaction().commit();
        em.clear();
        if(!result.next())
            return null;
        return result;
    }
    //Verificar LogIn -> envia-se user e password inserida
    public Funcionario logIn(String username, String password) throws Exception {
        String sql_statement = "select * from login where '" + username + "' = username and '" + password + "' = password_field";
        ResultSet result = this.select(sql_statement);
        if(result == null){
            System.out.println("Nao existe");
        }
        this.loggedEmployee.setIdFuncionario(result.getLong("id_funcionario"));
        System.out.println(this.loggedEmployee.getIdFuncionario());
        
        return this.loggedEmployee;
    }
    
    
    
}