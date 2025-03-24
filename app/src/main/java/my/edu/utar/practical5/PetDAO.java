package my.edu.utar.practical5;

public class PetDAO {
    private int id;
    private String petName;
    private String petType;

    public PetDAO(){
    }

    public PetDAO(String petName, String petType){
        this.petName = petName;
        this.petType = petType;
    }

    public String getPetName(){
        return petName;
    }

    public void setPetName(String petName){
        this.petName = petName;
    }

    public String getPetType(){
        return petType;
    }

    public void setPetType(String petType){
        this.petType = petType;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
}