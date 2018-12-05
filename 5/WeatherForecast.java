import java.util.List;

public class WeatherForecast{
    private Coord coord;
    private List<Weather> weather;
    private String base;
    private Main main;
    private int visibility;
    private Wind wind;
    private Clouds clouds;
    private int dt;
    private Sys sys;
    private int id;
    private String name;
    private int cod;

    public Coord getCoord(){
        return coord;
    }
    public void setCoord(Coord input){
        this.coord = input;
    }
    public List<Weather> getWeather(){
        return weather;
    }
    public void setWeather(List<Weather> input){
        this.weather = input;
    }
    public String getBase(){
        return base;
    }
    public void setBase(String input){
        this.base = input;
    }
    public Main getMain(){
        return main;
    }
    public void setMain(Main input){
        this.main = input;
    }
    public int getVisibility(){
        return visibility;
    }
    public void setVisibility(int input){
        this.visibility = input;
    }
    public Wind getWind(){
        return wind;
    }
    public void setWind(Wind input){
        this.wind = input;
    }
    public Clouds getClouds(){
        return clouds;
    }
    public void setClouds(Clouds input){
        this.clouds = input;
    }
    public int getDt(){
        return dt;
    }
    public void setDt(int input){
        this.dt = input;
    }
    public Sys getSys(){
        return sys;
    }
    public void setSys(Sys input){
        this.sys = input;
    }
    public int getId(){
        return id;
    }
    public void setId(int input){
        this.id = input;
    }
    public String getName(){
        return name;
    }
    public void setName(String input){
        this.name = input;
    }
    public int getCod(){
        return cod;
    }
    public void setCod(int input){
        this.cod = input;
    }
}
  