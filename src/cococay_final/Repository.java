/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cococay_final;

import cococay_final.Model.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.DatePicker;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 *
 * @author joaocosta-ipvc
 */
public class Repository {
    private static final Repository repoSingleton = new Repository();
    EntityManager em;
    EntityManagerFactory emf;
    ResultSet result;
    
    Funcionario loggedEmployee;
    String loggedEmployeeDpt;
    List<Funcionario> employees;
    List<Equipa> teams;
    
    

    public Repository() {
        emf= Persistence.createEntityManagerFactory("Cococay_FinalPU");
        this.loggedEmployee = new Funcionario();
    }
    //Get repository instance
    public static Repository getSingleton(){ return repoSingleton; }
    //Get app strings
    public static String getAppName(){ return "Cococay"; }
    //CRUD Operations
    
    //Close result
    private void closeResult(){
        if(this.result != null){
            try {
                while(!this.result.isClosed()){
                    //System.out.println("\t\tResult is not closed...");
                    try {
                        this.result.close();
                        //System.out.println("Result was closed");
                    } catch (SQLException ex) { Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex); }
                }
            } catch (SQLException ex) {
                Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //select method
    public void select(String select) {
        em=emf.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        
        java.sql.Connection con = em.unwrap(java.sql.Connection.class);
        PreparedStatement st = null;
        try {
            st = con.prepareStatement(select);
            this.result = st.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex);
        }
        em.getTransaction().commit();
        em.clear(); 
        em.close();
        try {
            if(!this.result.next())//If nothing is retrieved from database return null
                this.result = null;
        } catch (SQLException ex) { }
    }
    //update method
    public void update(String sql_statement) {
        em=emf.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        
        java.sql.Connection con = em.unwrap(java.sql.Connection.class);
        PreparedStatement st = null;
        try {
            st = con.prepareStatement(sql_statement);
            st.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex);
        }
        em.getTransaction().commit();
        em.clear(); 
        em.close();
    }
    //Insert method
    public void insert(Object u){
        em=emf.createEntityManager();
        if(!em.getTransaction().isActive())
            em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
        em.clear();
        em.close();
    }
    //Verificar LogIn -> envia-se user e password inserida
    public Funcionario logIn(String username, String password) throws Exception {
        String sql_statement = "select * from login where '" + username + "' = username and '" + password + "' = password_field";
        this.select(sql_statement);
        if(this.result == null){
            throw new Exception("Invalid username or password");
        }
        this.loggedEmployee.setIdFuncionario(this.result.getLong("id_funcionario"));
        this.closeResult();
        this.select("select nome from funcionario where id_funcionario = '" + this.loggedEmployee.getIdFuncionario() + "'");
        this.loggedEmployee.setNome(this.result.getString("nome"));
        System.out.println("Logged Employee number: "+this.loggedEmployee.getIdFuncionario() + " with name: " + this.loggedEmployee.getNome());
        this.loggedEmployeeDpt = this.getDepartmentLoggedUser(this.loggedEmployee.getIdFuncionario());
        this.closeResult();
        this.select("select count(*) from funcionario where estado = 1 and id_funcionario = '" + this.loggedEmployee.getIdFuncionario() + "'");
        if(this.result == null || this.result.getShort("count(*)") == 0){
            throw new Exception("Invalid username or password");
        }
        return this.loggedEmployee;
    }
    
    //Log out method
    public void logout(){
        this.loggedEmployee.setIdFuncionario(null);
        this.loggedEmployee.setNome(null);
        System.out.println("Successful Logout id: " + this.loggedEmployee.getIdFuncionario());
    }
    //Method responsible for check if in the current month select, anyone as holidays schedule
    public List<Ferias> getCurrentMonthHolidaysEmployees(int month, int year){
        String monthString = (month < 10 ? "0" + Integer.toString(month) : Integer.toString(month));
        List<Ferias> holidays = new ArrayList<>();
        //
        Calendar dt = Calendar.getInstance();
        dt.set(Calendar.YEAR, year);
        dt.set(Calendar.MONTH, month-1);
        //
        int numberOfDaysInMonth = dt.getActualMaximum(Calendar.DAY_OF_MONTH);
        String sql_statement;
        
        sql_statement = "select id_ferias, data_inicio, data_fim, verificado_rh, aprovado, id_funcionario from ferias where data_inicio >= to_date('1/" + monthString + "/" + year + 
                "', 'DD/MM/YYYY') and data_fim <= to_date('" + numberOfDaysInMonth + "/" + monthString + "/" + year + "', 'DD/MM/YYYY')";
        
        this.select(sql_statement);
        
        if(this.result != null){
            //get first row data
            try {
                Ferias temp = new Ferias();
                temp.setIdFerias(this.result.getLong("id_ferias"));
                temp.setDataInicio(this.result.getDate("data_inicio"));
                temp.setDataFim(this.result.getDate("data_fim"));
                temp.setVerificadoRh(this.result.getShort("verificado_rh"));
                temp.setAprovado(this.result.getShort("aprovado"));
                Funcionario temp2 = new Funcionario();
                temp2.setIdFuncionario(this.result.getLong("id_funcionario"));
                temp.setIdFuncionario(temp2);
                holidays.add(temp);
            } catch (SQLException ex) { Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex); }
            //get the rest...if exists
            try {
                while(this.result.next()){
                    Ferias temp = new Ferias();
                    temp.setIdFerias(this.result.getLong("id_ferias"));
                    temp.setDataInicio(this.result.getDate("data_inicio"));
                    temp.setDataFim(this.result.getDate("data_fim"));
                    temp.setVerificadoRh(this.result.getShort("verificado_rh"));
                    temp.setAprovado(this.result.getShort("aprovado"));
                    Funcionario temp2 = new Funcionario();
                    temp2.setIdFuncionario(this.result.getLong("id_funcionario"));
                    temp.setIdFuncionario(temp2);
                    holidays.add(temp);
                }
            } catch (SQLException ex) { Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex); }
        }
        this.closeResult();
        if(!holidays.isEmpty()){
            return holidays;
        }
        return null;
    }
    //Get logged user
    public Funcionario getLoggedEmployee(){ return this.loggedEmployee; }
    //Get department of logged user
    public String getDepartmentLoggedUser(long idFuncionario){
        String sql_statement = "select designacao from equipa, membros_equipa where membros_equipa.id_funcionario = '" + idFuncionario + "' and membros_equipa.id_equipa = equipa.id_equipa";
        String temp = "";
        this.result = null;
        try {
            this.select(sql_statement);
            temp = this.result.getString("designacao");
        } catch (SQLException ex) {
            System.out.println("If exception returned error with sql statement");
        } finally{
            this.closeResult();
        }
        if(!temp.isEmpty())
            return temp;
        return null;
    }
    //
    public String getNextHolidays() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = dtf.format(LocalDateTime.now());
        String sql_statment = "select data_inicio from ferias where id_funcionario = '"+this.loggedEmployee.getIdFuncionario()+"' and "
                + "data_inicio >= to_date('"+date+"','dd/MM/yyyy') and verificado_rh = '1' and aprovado = '1' order by data_inicio desc";
        this.select(sql_statment);
        if(this.result != null){
            String dateGet = null;
            try {
                dateGet = dateFormat.format(this.result.getDate("data_inicio"));
            } catch (SQLException ex) { }
            return dateGet;
        }else{
            return "N/A";
        }
    }    
    //Get a string containing the name and department of a user
    public Short getNumberHolidaysLoggedEmployee(){
        String sql_statement = "select numero_dias_ferias_total from funcionario where id_funcionario = '" + this.loggedEmployee.getIdFuncionario() +"'";
        this.select(sql_statement);
        Short temp = null;
        try {
            temp = this.result.getShort("numero_dias_ferias_total");
        } catch (SQLException ex) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            this.closeResult();
        }
        return temp;
    }
    //Get restrictions for the current month displayed
    public List<RestricaoFerias> getRestrictions(int month, int year){
        String monthString = (month < 10 ? "0" + Integer.toString(month) : Integer.toString(month));
        List<RestricaoFerias> restrictions = new ArrayList<>();
        //
        Calendar dt = Calendar.getInstance();
        dt.set(Calendar.YEAR, year);
        dt.set(Calendar.MONTH, month-1);
        //
        int numberOfDaysInMonth = dt.getActualMaximum(Calendar.DAY_OF_MONTH);
        String sql_statement;
        
        sql_statement = "select ID_RESTRICAO, DATA_INICIO, DATA_FIM, MOTIVO, ID_FUNCIONARIO from RESTRICAO_FERIAS where data_inicio >= to_date('1/" + monthString + "/" + year + 
                "', 'DD/MM/YYYY') and data_fim <= to_date('" + numberOfDaysInMonth + "/" + monthString + "/" + year + "', 'DD/MM/YYYY')";
        
        this.select(sql_statement);
        
        if(this.result != null){
            try {
                RestricaoFerias temp = new RestricaoFerias();
                temp.setIdRestricao(this.result.getLong("id_restricao"));
                temp.setMotivo(this.result.getString("motivo"));
                temp.setDataInicio(this.result.getDate("data_inicio"));
                temp.setDataFim(this.result.getDate("data_fim"));
                Funcionario temp2 = new Funcionario();
                temp2.setIdFuncionario(this.result.getLong("id_funcionario"));
                temp.setIdFuncionario(temp2);
                restrictions.add(temp);
            } catch (SQLException ex) { Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex); }
            try{
                while(this.result.next()){
                    RestricaoFerias temp = new RestricaoFerias();
                    temp.setIdRestricao(this.result.getLong("id_restricao"));
                    temp.setMotivo(this.result.getString("motivo"));
                    temp.setDataInicio(this.result.getDate("data_inicio"));
                    temp.setDataFim(this.result.getDate("data_fim"));
                    Funcionario temp2 = new Funcionario();
                    temp2.setIdFuncionario(this.result.getLong("id_funcionario"));
                    temp.setIdFuncionario(temp2);
                    restrictions.add(temp);
                }
            }catch (SQLException ex) { Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex); } 
        }
        this.closeResult();
        if(!restrictions.isEmpty()){
            return restrictions;
        }
        return null;
    }
    //Check if logged user belongs to the same department of another user
    public boolean isFromSameDepartment(Long id_employee){
        List<String> dptLoggedUser = new ArrayList<>();
        List<String> dptUserChecked = new ArrayList<>();
        String sql_statment = "select equipa.designacao from equipa, membros_equipa where membros_equipa.id_equipa = equipa.id_equipa and "
                + "membros_equipa.id_funcionario = '" + this.loggedEmployee.getIdFuncionario() + "'";
        this.select(sql_statment);
        if(this.result != null){
            try {
                dptLoggedUser.add(this.result.getString("designacao"));
                while(this.result.next()){
                    dptLoggedUser.add(this.result.getString("designacao"));
                }
            } catch (SQLException ex) { }
        }
        this.closeResult();
        sql_statment = "select equipa.designacao from equipa, membros_equipa where membros_equipa.id_equipa = equipa.id_equipa and "
                + "membros_equipa.id_funcionario = '" + id_employee + "'";
        this.select(sql_statment);
        if(this.result != null){
            try {
                dptUserChecked.add(this.result.getString("designacao"));
                while(this.result.next()){
                    dptUserChecked.add(this.result.getString("designacao"));
                }
            } catch (SQLException ex) { }
        }
        this.closeResult();
        for (String string : dptUserChecked) {
            if(dptLoggedUser.contains(string))
                return true;
        }
        return false;
    }
    //Check if logged employee is allowed to leave based on department restrictions
    public boolean hasReachedMaximumDptElementsOnHolidays(String date){
        Map<Long, Short> mapDptMaxEmployeesOut = new HashMap<>();
        String sql_statement = "SELECT MEMBROS_EQUIPA.ID_EQUIPA FROM MEMBROS_EQUIPA WHERE "
                + "MEMBROS_EQUIPA.ID_FUNCIONARIO = '" + this.loggedEmployee.getIdFuncionario() + "'";
        //Get departments that the current logged employee is integrated
        this.select(sql_statement);
        try {
            mapDptMaxEmployeesOut.put(this.result.getLong("id_equipa"), null);
            while(this.result.next()){
                mapDptMaxEmployeesOut.put(this.result.getLong("id_equipa"), null);
            }
        } catch (SQLException ex) { }
        this.closeResult();
        //Get the number of elements integrated in each department of the logged employee
        for(Long id: mapDptMaxEmployeesOut.keySet()){
            System.out.println("DPt ID: " + id);
            sql_statement = "select count(*) from membros_equipa where id_equipa = '" + id + "'";
            this.select(sql_statement);
            try {
                System.out.println("Contagem: " + this.result.getShort("count(*)"));
                mapDptMaxEmployeesOut.put(id, this.result.getShort("count(*)"));
            } catch (SQLException ex) { }
        }
        int count = 0;
        //Get the number of employees for each department of the logged employee that are in holidays for the current date given
        for(Long id: mapDptMaxEmployeesOut.keySet()){
            sql_statement = "select count(*) from membros_equipa, ferias where membros_equipa.id_funcionario = ferias.ID_FUNCIONARIO "
                + "and membros_equipa.ID_EQUIPA = '"+ id +"' and ferias.DATA_INICIO <= to_date('"+ date +"','dd/mm/yyyy') and ferias.DATA_FIM >= to_date('"+ date +"','dd/mm/yyyy') and"
                    + "((verificado_rh = '0' and aprovado = '0') or (verificado_rh = '1' and aprovado = '1'))";
            this.select(sql_statement);
            try {
                count = this.result.getShort("count(*)");
            } catch (SQLException ex) { }
            this.closeResult();
            int min = 0;
            this.select("select minimo_elementos_trabalhar from equipa where id_equipa = '" + id + "'");
            try {
                min = this.result.getShort("minimo_elementos_trabalhar");
            } catch (SQLException ex) { }
            this.closeResult();
            // if the ammount of employees on work is less or equal to the minimum necessary -> send true
            System.out.println("Current number of employees on work: " + (mapDptMaxEmployeesOut.get(id) - count));
            if((mapDptMaxEmployeesOut.get(id) - count) <= min)
                return true;
        }
        return false;
    }
    //Check if logged employee has holidays already scheduled in the current period
    public boolean hasAlreadyScheduledHolidays(String date){
        String sql_statement = "select count(*) from ferias where id_funcionario = '"+this.loggedEmployee.getIdFuncionario()+"' and "
                + "DATA_INICIO <= to_date('"+ date +"','dd/mm/yyyy') and DATA_FIM >= to_date('"+ date +"','dd/mm/yyyy') and "
                + "((verificado_rh = '0' and aprovado = '0') or (verificado_rh = '1' and aprovado = '1'))";
        this.select(sql_statement);
        if(this.result == null){
            return false;
        }
        try {
            if(this.result.getShort("count(*)") > 0)
                return true;    
        } catch (SQLException ex) { }
        return false;
    }
    //Check if date collide with restrictions
    public boolean isRestriction(String date){
        String sql_statement = "select count(*) from restricao_ferias where "
                + "DATA_INICIO <= to_date('"+ date +"','dd/mm/yyyy') and DATA_FIM >= to_date('"+ date +"','dd/mm/yyyy')";
        this.select(sql_statement);
        if(this.result == null){
            return false;
        }
        try {
            if(this.result.getShort("count(*)") > 0)
                return true;    
        } catch (SQLException ex) { }
        return false;
    }
    //
    public void addNewHoliday(DatePicker dtPickerBegin, DatePicker dtPickerEnd, int numberOfDays) {
        //
        this.select("select max(id_ferias) from ferias");
        long max = (long) Long.parseLong("6000000000");
        try {
            max = this.result.getLong("max(id_ferias)");
        } catch (SQLException ex) { }
        max++;
        Ferias holidayTemp = new Ferias();
        holidayTemp.setIdFerias(max);
        holidayTemp.setIdFuncionario(loggedEmployee);
        if(this.loggedEmployeeDpt.equals("Human Resources")){
            holidayTemp.setAprovado((short) 1);
            holidayTemp.setVerificadoRh((short) 1);
        }else{
            holidayTemp.setAprovado((short) 0);
            holidayTemp.setVerificadoRh((short) 0);
        }
        holidayTemp.setDataInicio(Date.valueOf(dtPickerBegin.getValue()));
        holidayTemp.setDataFim(Date.valueOf(dtPickerEnd.getValue()));
        //
        this.insert(holidayTemp);
        //subtract the number of days requested to the total
        String sql_statment = null;
        sql_statment = "select numero_dias_ferias_total, numero_dias_ferias_atraso from funcionario where id_funcionario = '"+this.loggedEmployee.getIdFuncionario()+"'";
        this.select(sql_statment);
        int total = 0;
        int totalLastYear = 0;
        try {
            total = this.result.getShort("numero_dias_ferias_total");
            totalLastYear = this.result.getShort("numero_dias_ferias_atraso");
        } catch (SQLException ex) { }
        this.closeResult();
        if(totalLastYear - numberOfDays < 0){
            total = total - numberOfDays;//If is below 0 then we must subtract to the remaining days
            sql_statment = "update funcionario set numero_dias_ferias_total = " +total+", numero_dias_ferias_atraso = 0 where id_funcionario = '"+this.loggedEmployee.getIdFuncionario()+"'";
        }else{
            sql_statment = "update funcionario set numero_dias_ferias_atraso = numero_dias_ferias_atraso - "+numberOfDays+", numero_dias_ferias_total = numero_dias_ferias_total - " + numberOfDays
                    + " where id_funcionario = '"+this.loggedEmployee.getIdFuncionario()+"'";
        }
        this.update(sql_statment);
        
    }
    //Get notifications for the current logged user
    public List<String> getNotifications() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = dtf.format(LocalDateTime.now().plusDays(1));//Get tomorro date...we must check if the next day has a goal defined
        //List of strings....each string is a notification
        List<String> temp = new ArrayList<>();
        String sql_stament = null;
        
        //Human resources notifications
        if(this.loggedEmployeeDpt.equals("Human Resources")){
            //check if tomorrow as a goal defined to show to all employees
            sql_stament = "select count(*) from objetivo_mensagem where data_mostragem >= to_date('"+ date +"','dd/mm/yyyy') "
                    + "and data_mostragem <= to_date('" + date + "','dd/mm/yyyy')";
            this.select(sql_stament);
            try {
                if(this.result.getShort("count(*)") <= 0){//If count is 0..no goal for next day. Notify RH
                    temp.add("Don't forget to define a goal or a motivation message for tomorrow to show to all your employees!");
                }
            } catch (SQLException ex) { }
            this.closeResult();
            //Check if there are request waiting for validation
            sql_stament = "select FERIAS.DATA_INICIO, FERIAS.DATA_FIM, FUNCIONARIO.NOME, FERIAS.ID_FUNCIONARIO FROM FERIAS, FUNCIONARIO WHERE "
                    + "FERIAS.ID_FUNCIONARIO = FUNCIONARIO.ID_FUNCIONARIO AND VERIFICADO_RH = '0' AND APROVADO = '0' AND DATA_INICIO >= TO_DATE('"+date+"','DD/MM/YYYY')";
            this.select(sql_stament);
            try {
                String t;
                if(this.result != null){
                    t = "You have a new request to validate starting in " + this.result.getDate("data_inicio") + " until " + this.result.getDate("data_fim") + 
                            " requested from " + this.result.getString("nome") +"!";
                    temp.add(t);
                    while(this.result.next()){
                        t = "You have a new request to validate starting in " + this.result.getDate("data_inicio") + " until " + this.result.getDate("data_fim") + 
                                " requested from " + this.result.getString("nome") +"!";
                        temp.add(t);
                    }
                }
            } catch (SQLException ex) { }
            this.closeResult();
        }
        date = dtf.format(LocalDateTime.now());
        //Get all refused requests for holidays and show them to the user
        if(!this.loggedEmployeeDpt.equals("Human Resources")){
            sql_stament = "select FERIAS.DATA_INICIO, FERIAS.DATA_FIM FROM FERIAS WHERE "
                    + "VERIFICADO_RH = '1' AND APROVADO = '0' AND DATA_INICIO >= TO_DATE('"+date+"','DD/MM/YYYY') and id_funcionario = '"+this.loggedEmployee.getIdFuncionario()+"' "
                    + "and not exists (select FERIAS.DATA_INICIO, FERIAS.DATA_FIM FROM FERIAS WHERE VERIFICADO_RH = '1' AND APROVADO = '1' AND DATA_INICIO >= "
                    + "TO_DATE('"+date+"','DD/MM/YYYY') and id_funcionario = '"+this.loggedEmployee.getIdFuncionario()+"')";
            this.select(sql_stament);
            if(this.result != null){
                try {
                    temp.add("Your request for holidays starting at " + this.result.getDate("data_inicio") + " until " + this.result.getDate("data_fim") + " "
                            + "has been refused by the Human Resources Dpt.!");
                    while(this.result.next()){
                        temp.add("Your request for holidays starting at " + this.result.getDate("data_inicio") + " until " + this.result.getDate("data_fim") + " "
                            + "has been refused by the Human Resources Dpt.!");
                    }
                } catch (SQLException ex) { }
            }
            this.closeResult();
            System.out.println("Hello");
            sql_stament = "select FERIAS.DATA_INICIO, FERIAS.DATA_FIM FROM FERIAS WHERE "
                    + "VERIFICADO_RH = '1' AND APROVADO = '1' AND DATA_INICIO >= TO_DATE('"+date+"','DD/MM/YYYY') and id_funcionario = '"+this.loggedEmployee.getIdFuncionario()+"'";
            this.select(sql_stament);
            if(this.result != null){
                try {
                    temp.add("Your request for holidays starting at " + this.result.getDate("data_inicio") + " until " + this.result.getDate("data_fim") + " "
                            + "has been accepted by the Human Resources Dpt.!");
                    while(this.result.next()){
                        temp.add("Your request for holidays starting at " + this.result.getDate("data_inicio") + " until " + this.result.getDate("data_fim") + " "
                            + "has been accepted by the Human Resources Dpt.!");
                    }
                } catch (SQLException ex) { }
            }
            this.closeResult();
            
        }
        //Notifications for all departments
        //Check if there are previous year holidays left to schedule
        sql_stament = "select NUMERO_DIAS_FERIAS_ATRASO FROM FUNCIONARIO WHERE ID_FUNCIONARIO = '"+this.loggedEmployee.getIdFuncionario()+"'";
        this.select(sql_stament);
        try {
            int number = this.result.getShort("NUMERO_DIAS_FERIAS_ATRASO");
            if(number > 0)
                temp.add("You still have " + number + " day(s) remaining of holidays from last year to schedule. Those days will expire on 31/12/" + LocalDateTime.now().getYear() + "!");
        } catch (SQLException ex) { }
        this.closeResult();
        return temp;
    }
    //Get number of notifications
    public int getNumberNotifications(){
        List<String> temp = this.getNotifications();
        if(!temp.isEmpty())
            return temp.size();
        return 0;
    }
    //Get all the requests waiting for validation
    public List<Requests> getRequestsList() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        List<Requests> temp = new ArrayList<>();
        //
        String sql_statement = "select ferias.id_ferias, funcionario.nome, ferias.data_inicio, ferias.data_fim from funcionario, ferias "
                + "where ferias.id_funcionario = funcionario.id_funcionario and verificado_rh = '0' and aprovado = '0' order by id_ferias asc";
        this.select(sql_statement);
        //
        if(this.result != null){
            try {
                Requests request = new Requests(this.result.getLong("id_ferias"), this.result.getString("nome"), dateFormat.format(this.result.getDate("data_inicio"))
                    , dateFormat.format(this.result.getDate("data_fim")));
                request.setDepartment("N/A");
                temp.add(request);
                while(this.result.next()){
                    request = new Requests(this.result.getLong("id_ferias"), this.result.getString("nome"), dateFormat.format(this.result.getDate("data_inicio"))
                        , dateFormat.format(this.result.getDate("data_fim")));
                    request.setDepartment("N/A");
                    temp.add(request);
                }
            } catch (SQLException ex) { }
        }
        this.closeResult();
        //Get department
        for(Requests x: temp){
            sql_statement = "SELECT EQUIPA.ABREVIATURA from EQUIPA, MEMBROS_EQUIPA, FERIAS WHERE MEMBROS_EQUIPA.ID_EQUIPA = EQUIPA.ID_EQUIPA AND "
                    + "MEMBROS_EQUIPA.ID_FUNCIONARIO = FERIAS.ID_FUNCIONARIO AND FERIAS.ID_FERIAS = '" + x.getId()+ "'";
            this.select(sql_statement);
            String dptTemporary = null;
            if(this.result != null){
                try {
                    dptTemporary = this.result.getString("ABREVIATURA");
                    while(this.result.next()){
                        dptTemporary = dptTemporary.concat(", " + this.result.getString("ABREVIATURA"));
                    }
                } catch (SQLException ex) { }
            }
            this.closeResult();
            x.setDepartment(dptTemporary);
        }
        
        return temp;
    }
    //Set the new state of validation for a specific request
    public void setValidationRequest(Long id, boolean b) {
        String sql_statement = null;
        Long id_employee = null;
        sql_statement = "select id_funcionario from ferias where id_ferias = '"+id+"'";
        this.select(sql_statement);
        try {
            id_employee = this.result.getLong("id_funcionario");
        } catch (SQLException ex) { }
        this.closeResult();
        //if accepted only change the confirmation numbers
        if(b){
            sql_statement = "update ferias set verificado_rh = 1, aprovado = 1 where id_ferias = '" + id +"'";
            this.update(sql_statement);
            return;
        }else{
            sql_statement = "update ferias set verificado_rh = 1 where id_ferias = '" + id + "'";
            this.update(sql_statement);
            //otherwise get number of days and add to the total
            sql_statement = "select numero_dias_ferias_atraso from funcionario, ferias where funcionario.id_funcionario = ferias.id_funcionario and"
                    + " ferias.id_ferias = '" + id + "'";
            this.select(sql_statement);
            int number = 0;
            try {
                number = this.result.getShort("numero_dias_ferias_atraso");
            } catch (SQLException ex) { }
            this.closeResult();
            //get days of the selected holidays
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            LocalDate beginDt = null ;
            LocalDate endDt = null ;
            sql_statement = "select data_inicio, data_fim from ferias where id_ferias = '"+id+"'";
            this.select(sql_statement);
            System.out.println("I m here");
            try {
                Calendar cal = Calendar.getInstance();
                cal.setTime(this.result.getDate("data_inicio"));
                beginDt = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
                cal.setTime(this.result.getDate("data_fim"));
                endDt = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
            } catch (SQLException ex) { }
            //Get number of days between two dates including weekends and the begin and end dates
            int temp = (int) (ChronoUnit.DAYS.between(beginDt, endDt)) + 1;
            //System.out.println("\t\tDays between two dates: " + temp);
            //Set a calendar type of date corresponding to the datepicker begin
            Calendar calendarDateBegin = Calendar.getInstance();
            calendarDateBegin.set(beginDt.getYear(), beginDt.getMonthValue() - 1, beginDt.getDayOfMonth());
            //Check number of weekend days
            for(int i = 0; i < (ChronoUnit.DAYS.between(beginDt, endDt)) + 1; i++){
                //If day of the week is sunday or saturday increase one day to available days
                if(calendarDateBegin.get(Calendar.DAY_OF_WEEK) == 1 || calendarDateBegin.get(Calendar.DAY_OF_WEEK) == 7){
                    temp--;
                }
                calendarDateBegin.add(Calendar.DAY_OF_MONTH, 1);
            }
            //
            if(number > 0){//If number of day left to schedule from last year is greater than 0 all the days of the refused holidays most be added to this field
                sql_statement = "update funcionario set numero_dias_ferias_total = numero_dias_ferias_total + "+temp+" where id_funcionario = '"+id_employee+"'";
                this.update(sql_statement);
                sql_statement = "update funcionario set numero_dias_ferias_atraso = numero_dias_ferias_atraso + "+temp+" where id_funcionario = '"+id_employee+"'";
                this.update(sql_statement);
                return;
            }
            sql_statement = "update funcionario set numero_dias_ferias_total = numero_dias_ferias_total + "+temp+" where id_funcionario = '"+id_employee+"'";
            this.update(sql_statement);
            sql_statement = "select numero_dias_ferias_total from funcionario, ferias where funcionario.id_funcionario = ferias.id_funcionario and"
                    + " ferias.id_ferias = '" + id + "'";
            this.select(sql_statement);
            number = 0;
            try {
                number = this.result.getShort("numero_dias_ferias_total");
            } catch (SQLException ex) { }
            this.closeResult();
            if(number - 25 > 0){
                sql_statement = "update funcionario set numero_dias_ferias_atraso = numero_dias_ferias_atraso + "+(number - 25)+" where id_funcionario = '"+id_employee+"'";
                this.update(sql_statement);
            }
        }
    }
    //Get all the employees working in the company
    public List<EmployeesForList> getEmployeesList() {
        List<EmployeesForList> tempList = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //
        String sql_statement = "SELECT ID_FUNCIONARIO, NOME, DATA_CONTRATO from FUNCIONARIO "
                + "ORDER BY ID_FUNCIONARIO";
        this.select(sql_statement);
        if(this.result != null){
            try {
                EmployeesForList temp = new EmployeesForList(this.result.getLong("id_funcionario"), this.result.getString("nome"),
                        (this.result.getDate("data_contrato") == null ? "": dateFormat.format(this.result.getDate("data_contrato"))));
                tempList.add(temp);
                while(this.result.next()){
                    temp = new EmployeesForList(this.result.getLong("id_funcionario"), this.result.getString("nome"),
                        (this.result.getDate("data_contrato") == null ? "": dateFormat.format(this.result.getDate("data_contrato"))));
                    tempList.add(temp);
                }
            } catch (SQLException ex) { }
        }
        this.closeResult();
        for(EmployeesForList x: tempList){
            sql_statement = "SELECT EQUIPA.DESIGNACAO from EQUIPA, MEMBROS_EQUIPA WHERE MEMBROS_EQUIPA.ID_EQUIPA = EQUIPA.ID_EQUIPA AND ID_FUNCIONARIO = "
                + "'" + x.getId() + "'";
            this.select(sql_statement);
            String dptTemporary = null;
            if(this.result != null){
                try {
                    dptTemporary = this.result.getString("designacao");
                    while(this.result.next()){
                        dptTemporary = dptTemporary.concat("\n" + this.result.getString("designacao"));
                    }
                } catch (SQLException ex) { }
            }
            this.closeResult();
            x.setDepartment(dptTemporary);
        }
        return tempList;
    }
    //
    public void disableEmployee(EmployeesForList currentSelectedEmployee) {
        String sql_statement = "update funcionario set estado = 0, data_contrato = null where id_funcionario = '"+currentSelectedEmployee.getId()+"'";
        this.update(sql_statement);
    }

    public void enableEmployee(EmployeesForList currentSelectedEmployee) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	String sql_statement = "update funcionario set estado = 1, data_contrato = to_date('"+dtf.format(LocalDate.now())+"','dd/MM/yyyy') where id_funcionario = '"+currentSelectedEmployee.getId()+"'";
        this.update(sql_statement);
    }
    //The next method checks in the database if a certain field is already in use by another employee
    public boolean isFieldAlreadyInDB(String sql_statement) {
        this.select(sql_statement);
        if(this.result != null){
            try {
                if(this.result.getShort("count(*)") > 0){
                    this.closeResult();
                    return true;
                }
            } catch (SQLException ex) { }
        }
        this.closeResult();
        return false;
    }
    //Get the minimum wage
    public float getMinimumWage() {
        return ((float) 585.00);
    }
    //Get all the current created position available in the company
    public List<FuncaoTrabalho> getPositionHeldList() {
        List<FuncaoTrabalho> temp = new ArrayList<>();
        String sql_statement = "select * from Funcao_Trabalho";
        this.select(sql_statement);
        FuncaoTrabalho x;
        if(this.result != null){
            try {
                x = new FuncaoTrabalho();
                x.setIdFuncaoTrabalho(this.result.getLong("id_funcao_trabalho"));
                x.setDesignacaoEquipa(this.result.getString("designacao_equipa"));
                temp.add(x);
                while(this.result.next()){
                    x = new FuncaoTrabalho();
                    x.setIdFuncaoTrabalho(this.result.getLong("id_funcao_trabalho"));
                    x.setDesignacaoEquipa(this.result.getString("designacao_equipa"));
                    temp.add(x);
                }
            } catch (SQLException ex) { }
        }
        this.closeResult();
        return temp;
    }

    public List<Equipa> getAllActiveDepartments() {
        List<Equipa> temp = new ArrayList<>();
        String sql_statement = "select * from Equipa where estado = '1'";
        this.select(sql_statement);
        Equipa x;
        if(this.result != null){
            try {
                x = new Equipa();
                x.setIdEquipa(this.result.getLong("id_equipa"));
                x.setDesignacao(this.result.getString("designacao"));
                x.setEstado(this.result.getShort("estado"));
                x.setMinimoElementosTrabalhar(this.result.getShort("minimo_elementos_trabalhar"));
                temp.add(x);
                while(this.result.next()){
                    x = new Equipa();
                    x.setIdEquipa(this.result.getLong("id_equipa"));
                    x.setDesignacao(this.result.getString("designacao"));
                    x.setEstado(this.result.getShort("estado"));
                    x.setMinimoElementosTrabalhar(this.result.getShort("minimo_elementos_trabalhar"));
                    temp.add(x);
                }
            } catch (SQLException ex) { }
        }
        this.closeResult();
        return temp;
    }
    
    //if new position was created then add to the db
    public FuncaoTrabalho addNewPositionHeld(String currentSelectedPositionHeld) {
        String sql_statment = ("select max(id_funcao_trabalho) from funcao_trabalho");
        this.select(sql_statment);
        long id = 0;
        if(this.result != null){
            try {
                id = this.result.getLong("max(id_funcao_trabalho)");
                id++;
            } catch (SQLException ex) {}
        }
        this.closeResult();
        if(id != 0){
            FuncaoTrabalho temp = new FuncaoTrabalho(id);
            temp.setDesignacaoEquipa(currentSelectedPositionHeld);
            this.insert(temp);
            return temp;
        }
        return null;
    }
    //Add the new employee to the db
    public Funcionario addNewEmployee(Funcionario temp) throws Exception {
        String sql_statment = "select max(id_funcionario) from funcionario";
        this.select(sql_statment);
        long id = 0;
        if(this.result != null){
            try {
                id = this.result.getLong("max(id_funcionario)");
                id++;
            } catch (SQLException ex) {}
        }
        this.closeResult();
        if(id != 0){
            temp.setIdFuncionario(id);
            this.insert(temp);
            return temp;
        }else{
            throw new Exception("Error: An error occured when trying to add the new employee. Contact the system Administrator!");
        }
    }
    //add the new employee to all the departments he belongs
    public void setDepartmentsForNewEmployee(List<Long> listOfIDDepartments, Long idFuncionario) {
        String sql_statement;
        int i = listOfIDDepartments.size();
        for (int i2 = 0; i2 < i; i2++) {
            if(i2 == i)
                return;
            System.out.println("Department setted ::" + listOfIDDepartments.size());
            sql_statement = "INSERT INTO MEMBROS_EQUIPA(ID_EQUIPA, ID_FUNCIONARIO) VALUES(" + listOfIDDepartments.get(i2) + ", "+ idFuncionario + ")";
            this.update(sql_statement);
        }
    }
    //add the login parameters for the new employee
    public void setNewLoggin(Funcionario funcionario, String text) {
        Login temp = new Login();
        temp.setIdFuncionario(funcionario);
        temp.setUsername(text);
        temp.setPasswordField(text);
        temp.setEstado((short) 1);
        temp.setPerguntaSeguranca1("N/A");
        temp.setPerguntaSeguranca2("N/A");
        temp.setRespostaSeguranca1("N/A");
        temp.setRespostaSeguranca2("N/A");
        this.insert(temp);
    }
    //Get all departments even if they are inactive
    public List<Equipa> getAllDepartments() {
        List<Equipa> temp = new ArrayList<>();
        String sql_statement = "select * from Equipa order by estado DESC";
        this.select(sql_statement);
        Equipa x;
        if(this.result != null){
            try {
                x = new Equipa();
                x.setIdEquipa(this.result.getLong("id_equipa"));
                x.setDesignacao(this.result.getString("designacao"));
                x.setEstado(this.result.getShort("estado"));
                x.setAbreviatura(this.result.getString("abreviatura"));
                x.setMinimoElementosTrabalhar(this.result.getShort("minimo_elementos_trabalhar"));
                temp.add(x);
                while(this.result.next()){
                    x = new Equipa();
                    x.setIdEquipa(this.result.getLong("id_equipa"));
                    x.setDesignacao(this.result.getString("designacao"));
                    x.setEstado(this.result.getShort("estado"));
                    x.setAbreviatura(this.result.getString("abreviatura"));
                    x.setMinimoElementosTrabalhar(this.result.getShort("minimo_elementos_trabalhar"));
                    temp.add(x);
                }
            } catch (SQLException ex) { }
        }
        this.closeResult();
        return temp;
    }

    public Equipa addNewDepartment(String denomination, String abreviation, Integer minimum) {
        Equipa temp = new Equipa();
        String sql_statment = ("select max(id_equipa) from equipa");
        this.select(sql_statment);
        long id = 0;
        if(this.result != null){
            try {
                id = this.result.getLong("max(id_equipa)");
                id++;
            } catch (SQLException ex) {}
        }
        this.closeResult();
        if(id != 0){
            temp.setIdEquipa(id);
            temp.setDesignacao(denomination);
            temp.setAbreviatura(abreviation);
            temp.setMinimoElementosTrabalhar(Short.parseShort(String.valueOf(minimum)));
            temp.setEstado((short) 1);
            this.insert(temp);
            return temp;
        }
        return null;
    }

    public List<Funcionario> getEmployeesListOfDepartment(Long dpt) {
        List<Funcionario> tempList = new ArrayList<>();
        //
        String sql_statement = "SELECT FUNCIONARIO.ID_FUNCIONARIO, FUNCIONARIO.NOME from FUNCIONARIO, Membros_equipa"
                + " where FUNCIONARIO.ID_FUNCIONARIO = Membros_equipa.ID_FUNCIONARIO and Membros_equipa.id_equipa = '"+dpt+"' ORDER BY ID_FUNCIONARIO";
        this.select(sql_statement);
        if(this.result != null){
            try {
                Funcionario temp = new Funcionario(this.result.getLong("Id_funcionario"));
                temp.setNome(this.result.getString("nome"));
                tempList.add(temp);
                while(this.result.next()){
                    temp = new Funcionario(this.result.getLong("Id_funcionario"));
                    temp.setNome(this.result.getString("nome"));
                    tempList.add(temp);
                }
            } catch (SQLException ex) { }
        }
        this.closeResult();
        return tempList;
    }
    public List<Funcionario> getEmployeesListOfAllDepartments() {
        List<Funcionario> tempList = new ArrayList<>();
        //
        String sql_statement = "SELECT ID_FUNCIONARIO, NOME from FUNCIONARIO"
                + " ORDER BY ID_FUNCIONARIO";
        this.select(sql_statement);
        if(this.result != null){
            try {
                Funcionario temp = new Funcionario(this.result.getLong("Id_funcionario"));
                temp.setNome(this.result.getString("nome"));
                tempList.add(temp);
                while(this.result.next()){
                    temp = new Funcionario(this.result.getLong("Id_funcionario"));
                    temp.setNome(this.result.getString("nome"));
                    tempList.add(temp);
                }
            } catch (SQLException ex) { }
        }
        this.closeResult();
        return tempList;
    }
    //Update the department details
    public void updateDepartmentDetails(Equipa t) {
        String sql_statement = "update equipa set designacao = '"+t.getDesignacao()+"', abreviatura = '"+t.getAbreviatura()+"', "
                + "minimo_elementos_trabalhar = '"+t.getMinimoElementosTrabalhar()+"', estado = '"+t.getEstado()+"' where id_equipa = '"+t.getIdEquipa()+"'";
        this.update(sql_statement);
    }
    //Remove employee from a department
    public void removerEmployeeFromDepartment(Long idEquipa, Long idFuncionario) {
        String sql_statment = "DELETE FROM MEMBROS_EQUIPA WHERE ID_EQUIPA = '"+idEquipa+"' AND ID_FUNCIONARIO = '"+idFuncionario+"'";
        this.update(sql_statment);
        
    }

    public Login getLoginData(String user) throws Exception {
        String sql_statement = "select * from login where username = '"+user+"'";
        Login temp = new Login();
        this.select(sql_statement);
        if(this.result != null){
            Funcionario f = new Funcionario(this.result.getLong("id_funcionario"));
            temp.setIdFuncionario(f);
            temp.setPerguntaSeguranca1(this.result.getString("pergunta_seguranca1"));
            temp.setRespostaSeguranca1(this.result.getString("resposta_seguranca1"));
        }else{
            throw new Exception("Username invalid!");
        }
        this.closeResult();
        return temp;
    }

    public void changeUserPassword(Long idFuncionario, String text) {
        String sql_statement = "update login set password_field = '"+text+"' where id_funcionario = '"+idFuncionario+"'";
        this.update(sql_statement);
    }

    //
     //add new restriction
    public void addRestriction(DatePicker beginDate, DatePicker endDate, String Reason){
        long max=0;
         max=this.getMAxIdRestriction();
        max++;
        RestricaoFerias restricao = new RestricaoFerias();
        restricao.setIdRestricao(max);
        restricao.setDataInicio(Date.valueOf(beginDate.getValue()));
        restricao.setDataFim(Date.valueOf(endDate.getValue()));
        restricao.setMotivo(Reason);
        restricao.setIdFuncionario(loggedEmployee);
        this.insert(restricao);
    }
  // get mac id Restriction
    public long getMAxIdRestriction(){
        this.select("select MAX(id_restricao) from RESTRICAO_FERIAS");
        long max = (long) Long.parseLong("6000000000");
        try {
            max = this.result.getLong("MAX(id_restricao)");
        } catch (SQLException ex) { 
            ex.printStackTrace();
        }
        
        return max;
    }
    // check date for accept or not a restriction
    public boolean checkDateRestriction(String beginDate , String endDate){
        System.out.println("entrei nesta funÁao");
        String sql_statement = "select count(*) from Restricao_ferias where  "
                + "to_date('"+ beginDate+"','DD/MM/YY') >= DATA_INICIO "
                + "and to_date('"+ beginDate+"','DD/MM/YY') <= DATA_FIM "
                + "or to_date('"+ endDate+"','DD/MM/YY') >= DATA_INICIO "
                + "and to_date('"+ endDate+"','DD/MM/YY') <= DATA_FIM";
        this.select(sql_statement);
        if(this.result==null){
            return false;
        }
        try{
            if(this.result.getShort("count(*)")>0){
                System.out.println("resultadocrlh "+this.result.getShort("count(*)"));
                return false;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return true;
    }
    //Get all the Restrictions
    public List<Restrictions> getRestrictionsList(){
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    List<Restrictions> temp = new ArrayList<>();
    String sql_statement ="select ID_RESTRICAO,DATA_INICIO,DATA_FIM,MOTIVO from RESTRICAO_FERIAS where DATA_FIM >= SYSDATE-1";
    this.select(sql_statement);
    if(this.result!=null){
        try{
            Restrictions restrictions = new Restrictions(this.result.getLong("ID_RESTRICAO"), 
                    dateFormat.format(this.result.getDate("DATA_INICIO")),
                    dateFormat.format(this.result.getDate("DATA_FIM")), 
                    this.result.getString("MOTIVO"));
            temp.add(restrictions);
            while(this.result.next()){
                restrictions=  new Restrictions(this.result.getLong("ID_RESTRICAO"), 
                    dateFormat.format(this.result.getDate("DATA_INICIO")),
                    dateFormat.format(this.result.getDate("DATA_FIM")), 
                    this.result.getString("MOTIVO"));
                temp.add(restrictions);
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        } 
    }
    return temp;
    }
    //Get all validated holidays
    public List<HolidaysForList> getAllHolidays(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<HolidaysForList> temp = new ArrayList<>();
        String sql_statment = "select ferias.id_funcionario, ferias.data_inicio, ferias.data_fim, funcionario.nome from ferias, funcionario"
                + " where ferias.id_funcionario = funcionario.id_funcionario and ferias.data_inicio >= "
                + "to_date('"+dtf.format(LocalDate.now())+"','dd/MM/yyyy') and verificado_rh = 1 and aprovado = 1 order by ferias.data_inicio asc";
        this.select(sql_statment);
        if(this.result != null){
            HolidaysForList t = new HolidaysForList();
            try {
                t.setId(this.result.getLong("id_funcionario"));
                t.setBeginDate(dateFormat.format(this.result.getDate("data_inicio")));
                t.setEndDate(dateFormat.format(this.result.getDate("data_fim")));
                t.setName(this.result.getString("nome"));
                temp.add(t);
                while(this.result.next()){
                    t = new HolidaysForList();
                    t.setId(this.result.getLong("id_funcionario"));
                    t.setBeginDate(dateFormat.format(this.result.getDate("data_inicio")));
                    t.setEndDate(dateFormat.format(this.result.getDate("data_fim")));
                    t.setName(this.result.getString("nome"));
                    temp.add(t);
                }
            } catch (SQLException ex) {}
        }
        this.closeResult();
        for(HolidaysForList x: temp){
            sql_statment = "SELECT EQUIPA.DESIGNACAO from EQUIPA, MEMBROS_EQUIPA WHERE MEMBROS_EQUIPA.ID_EQUIPA = EQUIPA.ID_EQUIPA AND ID_FUNCIONARIO = "
                + "'" + x.getId() + "'";
            this.select(sql_statment);
            String dptTemporary = null;
            if(this.result != null){
                try {
                    dptTemporary = this.result.getString("designacao");
                    while(this.result.next()){
                        dptTemporary = dptTemporary.concat("\n" + this.result.getString("designacao"));
                    }
                } catch (SQLException ex) { }
            }
            this.closeResult();
            x.setDepartments(dptTemporary);
        }
        return temp;
    }

    public void addNewAbsence(Falta temp) throws Exception {
        temp.setIdFuncionarioRegista(this.loggedEmployee);
        String sql_statment = "select max(id_falta) from falta";
        this.select(sql_statment);
        if(this.result != null){
            try {
                temp.setIdFalta(this.result.getLong("max(id_falta)") + 1);
            } catch (SQLException ex) { }
        }else{
            throw new Exception("ID Error");
        }
        this.closeResult();
        this.insert(temp);
    }
    
    public List<AbsencesForList> getAllAbsences(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        List<AbsencesForList> temp = new ArrayList<>();
        String sql_statment = "select * from falta order by data_falta asc";
        this.select(sql_statment);
        if(this.result != null){
            AbsencesForList x = new AbsencesForList();
            try {
                x.setDate(dateFormat.format(this.result.getDate("data_falta")));
                x.setId(this.result.getLong("id_funcionario_falta"));
                if(this.result.getShort("justificada") == 1){
                    x.setJustified("Yes");
                    x.setJustification(this.result.getString("descricao"));
                }else{
                        x.setJustified("No");
                        x.setJustification("N/A");
                    }
                temp.add(x);
                while(this.result.next()){
                    x = new AbsencesForList();
                    x.setDate(dateFormat.format(this.result.getDate("data_falta")));
                    x.setId(this.result.getLong("id_funcionario_falta"));
                    if(this.result.getShort("justificada") == 1){
                        x.setJustified("Yes");
                        x.setJustification(this.result.getString("descricao"));
                    }else{
                        x.setJustified("No");
                        x.setJustification("N/A");
                    }
                    temp.add(x);
                }
                this.closeResult();
                for(AbsencesForList f: temp){
                    sql_statment = "select nome from funcionario where id_funcionario = '"+f.getId()+"'";
                    this.select(sql_statment);
                    f.setName(this.result.getString("nome"));
                }
            } catch (SQLException ex) { System.out.println("ups");}
        }
        this.closeResult();
        return temp;
    }
}