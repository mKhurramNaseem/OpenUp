package com.example.openup;



    public class Users {

        String imageuri, emaill, namee, password, cpassword, status, id, lastMessage;

        public Users(String id, String namee, String emaill, String password, String cpassword, String imageuri, String status) {
            this.id = id;
            this.namee = namee;
            this.emaill = emaill;
            this.password = password;
            this.cpassword = cpassword;
            this.imageuri = imageuri;
            this.status = status;
            this.lastMessage = lastMessage;

        }


        public String getimageuri() {
            return imageuri;
        }

        public void setimageuri(String imageuri) {
            this.imageuri = imageuri;
        }


        public String getemaill() {
            return emaill;
        }

        public void setemaill(String emaill) {
            this.emaill = emaill;
        }

        public String getnamee() {
            return namee;
        }

        public void setnamee(String namee) {
            this.namee = namee;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setcPassword(String cpassword) {
            this.cpassword = cpassword;
        }

        public String getcPassword() {
            return cpassword;
        }

        public String getstatus() {
            return status;
        }

        public void setstatus(String status) {
            this.status = status;
        }

        public String id() {
            return id;
        }

        public void setid(String id) {
            this.id = id;
        }

        String profilepic, mail, userName, userId, Status;
    }






