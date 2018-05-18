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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 *
 * @author joaocosta-ipvc
 */
public class Repository {
    
    EntityManager em;
    EntityManagerFactory emf;
    
    List<Funcionario> funcionarios;
    List<Equipa> equipas;
    
    

    public Repository() {
        funcionarios = new ArrayList<>();
        emf= Persistence.createEntityManagerFactory("JavaFXBDPU");
        em=emf.createEntityManager();
    }
    //Operações CRUD
    
    //select 
    public ResultSet select(String select) throws SQLException{
        ResultSet result;
        
        em.getTransaction().begin();
        
        java.sql.Connection con = em.unwrap(java.sql.Connection.class);
        PreparedStatement st= con.prepareStatement(select);
        
        result=st.executeQuery();
        
        em.getTransaction().commit();
        em.clear();
        result.next();
        
        return result;
    }
    //Verificar LogIn -> envia-se user e password inserida
    public int logIn(String username, String password){
        Login login = new Login();
        
        
        
        
        return 0;
    }
    
    
    
}
