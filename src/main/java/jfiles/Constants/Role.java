package jfiles.Constants;

public enum Role {

    USER(101, "User"),
    ADMIN(202, "Admin"),
    SUPER_ADMIN(303, "Super_Admin");

    private int    id;
    private String text;

    Role(int id, String text){
        this.id = id;
        this.text = text;
    }

    public int id(){
        return id;
    }

    public String text(){
        return text;
    }

    public static Role id(String text){

        for(Role r: values()){
            if( r.text.contentEquals( text)){
                return r;
            }
        }
        return null;
    }

}
