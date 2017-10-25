package com.zncm.qqfm.data;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by jiaomx on 2017/7/26.
 */


@DatabaseTable(tableName = "program")
public class Program extends Base {
    @DatabaseField(id = true)
    Long program_id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String url;
    @DatabaseField
    private int type;
    @DatabaseField
    private Long time;

    @DatabaseField
    private String ex1;
    @DatabaseField
    private String ex2;
    @DatabaseField
    private String ex3;
    @DatabaseField
    private String ex4;
    @DatabaseField
    private String ex5;
    @DatabaseField

    private int exi1;
    @DatabaseField
    private int exi2;
    @DatabaseField
    private int exi3;
    @DatabaseField
    private int exi4;
    @DatabaseField
    private int exi5;


    public Program(Long program_id, String name, String url, int type, Long time,
                   String ex1, String ex2, String ex3, String ex4, String ex5, int exi1,
                   int exi2, int exi3, int exi4, int exi5) {
        this.program_id = program_id;
        this.name = name;
        this.url = url;
        this.type = type;
        this.time = time;
        this.ex1 = ex1;
        this.ex2 = ex2;
        this.ex3 = ex3;
        this.ex4 = ex4;
        this.ex5 = ex5;
        this.exi1 = exi1;
        this.exi2 = exi2;
        this.exi3 = exi3;
        this.exi4 = exi4;
        this.exi5 = exi5;
    }

    public Program(String name, String url) {
        this.name = name;
        this.url = url;
        this.time = System.currentTimeMillis();
    }


    public Program() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getProgram_id() {
        return program_id;
    }

    public void setProgram_id(Long program_id) {
        this.program_id = program_id;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }


    public String getEx1() {
        return ex1;
    }

    public void setEx1(String ex1) {
        this.ex1 = ex1;
    }

    public String getEx2() {
        return ex2;
    }

    public void setEx2(String ex2) {
        this.ex2 = ex2;
    }

    public String getEx3() {
        return ex3;
    }

    public void setEx3(String ex3) {
        this.ex3 = ex3;
    }

    public String getEx4() {
        return ex4;
    }

    public void setEx4(String ex4) {
        this.ex4 = ex4;
    }

    public String getEx5() {
        return ex5;
    }

    public void setEx5(String ex5) {
        this.ex5 = ex5;
    }

    public int getExi1() {
        return exi1;
    }

    public void setExi1(int exi1) {
        this.exi1 = exi1;
    }

    public int getExi2() {
        return exi2;
    }

    public void setExi2(int exi2) {
        this.exi2 = exi2;
    }

    public int getExi3() {
        return exi3;
    }

    public void setExi3(int exi3) {
        this.exi3 = exi3;
    }

    public int getExi4() {
        return exi4;
    }

    public void setExi4(int exi4) {
        this.exi4 = exi4;
    }

    public int getExi5() {
        return exi5;
    }

    public void setExi5(int exi5) {
        this.exi5 = exi5;
    }
}
