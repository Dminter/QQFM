package com.zncm.qqfm.utils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.zncm.qqfm.data.Program;

public class AccountApp {

    public static void main(String[] args) throws Exception {

        // this uses h2 by default but change to match your database
        String databaseUrl = "jdbc:sqlite:test.db";
        // create a connection source to our database
        ConnectionSource connectionSource =
            new JdbcConnectionSource(databaseUrl);

        // instantiate the dao
        Dao<Program, String> accountDao =
            DaoManager.createDao(connectionSource, Program.class);

        // if you need to create the 'accounts' table make this call
        TableUtils.createTable(connectionSource, Program.class);
//Once we have configured our database objects, we can use them to persist an Account to the database and query for it from the database by its ID:

 	
        // create an instance of Account
        Program account = new Program();
        account.setName("Jim Coakley");

        // persist the account object to the database
        accountDao.create(account);

        // retrieve the account from the database by its id field (name)
        Program account2 = accountDao.queryForId("Jim Coakley");
        System.out.println("Account: " + account2.getName());

        // close the connection source
        connectionSource.close();
    }
}