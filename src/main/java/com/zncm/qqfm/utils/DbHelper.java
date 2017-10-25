package com.zncm.qqfm.utils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.zncm.qqfm.data.Base;
import com.zncm.qqfm.data.Program;

import java.sql.SQLException;
import java.util.List;

public class DbHelper {


    static ConnectionSource connectionSource;
    private static Dao<Program, String> programDao;

    public static Dao<Program, String> getProgramDao() {
        if (programDao == null) {
            initDb();
        }
        return programDao;
    }


    private static void initDb() {
        try {
            if (programDao != null) {
                return;
            }
            String databaseUrl = "jdbc:sqlite:qqfm.db";
            connectionSource = new JdbcConnectionSource(databaseUrl);
//            createTables();
            programDao = DaoManager.createDao(connectionSource, Program.class);


//            Program account2 = programDao.queryForId("Jim Coakley");
//            System.out.println("Account: " + account2.getName());
//            connectionSource.close();
        } catch (Exception e) {
//            java.lang.IllegalArgumentException: Must specify one of id, generatedId, and generatedIdSequence with program_id

            e.printStackTrace();
        }
    }

//    public static void add(Base obj) {
//        try {
//            if (obj instanceof Program) {
//                getProgramDao().create((Program) obj);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }



    public static void delete(Base obj) {
        try {
            if (obj instanceof Program) {
                getProgramDao().delete((Program) obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static List<?> query(Class<?> clazz) {
        try {
            if (clazz == Program.class) {
                return getProgramDao().queryForAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public static void createTables() {
        try {
            TableUtils.createTable(connectionSource, Program.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}