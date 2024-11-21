package com.fca.calidad.maven;

import com.fca.calidad.dao.DAOUserSQLite;
import com.fca.calidad.model.User;

public class app {

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        DAOUserSQLite dao = new DAOUserSQLite();
        User usuario =new User("nombre","correo","password");
        int id =dao.save(usuario);
        
        usuario.setId(id);
        System.out.println(dao.findById(id).toString());
        
        
        
        usuario.setPassword("newpassword");
        usuario.setId(id);
        System.out.println(dao.findById(1).toString());
        dao.deleteById(1);

    }
}