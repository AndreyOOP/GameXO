package jfiles.Constants;

public enum Roles {

    USER(101, "User"),
    ADMIN(202, "Admin"),
    SUPER_ADMIN(303, "Super_Admin");

    private int    id;
    private String text;

    Roles(int id, String text){
        this.id = id;
        this.text = text;
    }

    public int id(){
        return id;
    }

//    public String text(){
//        return text;
//    }

    public static Roles id(String text){

        for(Roles r: values()){
            if( r.text.contentEquals( text)){
                return r;
            }
        }
        return null;
    }

//    public static Roles id(int id){
//
//        for(Roles r: values()){
//            if(r.id == id){
//                return r;
//            }
//        }
//        return null;
//    }

}
