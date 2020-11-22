package com.example.udemyat40;

public class User {

    public String courseonename, courseonelink, coursetwoname, coursetwolink, email, phone, conedone, ctwodone, conepaid, ctwopaid, coneseen, ctwoseen;

    public User(){

    }

    public User(String courseonename, String courseonelink, String coursetwoname, String coursetwolink,
                String email, String phone, String conedone, String ctwodone,
                String conepaid, String ctwopaid, String coneseen, String ctwoseen){
        this.courseonename = courseonename;
        this.courseonelink = courseonelink;
        this.coursetwoname = coursetwoname;
        this.coursetwolink = coursetwolink;
        this.email = email;
        this.phone = phone;
        this.conedone = conedone;
        this.ctwodone = ctwodone;
        this.conepaid = conepaid;
        this.ctwopaid = ctwopaid;
        this.coneseen = coneseen;
        this.ctwoseen = ctwoseen;
    }

}
