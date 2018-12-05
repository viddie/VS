public class Sys{
    private int type;
    private int id;
    private int message;
    private String country;
    private int sunrise;
    private int sunset;

    public int getType(){
        return type;
    }
    public void setType(int input){
        this.type = input;
    }
    public int getId(){
        return id;
    }
    public void setId(int input){
        this.id = input;
    }
    public int getMessage(){
        return message;
    }
    public void setMessage(int input){
        this.message = input;
    }
    public String getCountry(){
        return country;
    }
    public void setCountry(String input){
        this.country = input;
    }
    public int getSunrise(){
        return sunrise;
    }
    public void setSunrise(int input){
        this.sunrise = input;
    }
    public int getSunset(){
        return sunset;
    }
    public void setSunset(int input){
        this.sunset = input;
    }
}
