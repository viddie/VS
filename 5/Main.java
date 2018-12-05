import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Main{
    private int temp;
    private int pressure;
    private int humidity;
    private int tempMin;
    private int tempMax;

    public int getTemp(){
        return temp;
    }
    public void setTemp(int input){
        this.temp = input;
    }
    public int getPressure(){
        return pressure;
    }
    public void setPressure(int input){
        this.pressure = input;
    }
    public int getHumidity(){
        return humidity;
    }
    public void setHumidity(int input){
        this.humidity = input;
    }
    public int getTempMin(){
        return tempMin;
    }
    public void setTempMin(int input){
        this.tempMin = input;
    }
    public int getTempMax(){
        return tempMax;
    }
    public void setTempMax(int input){
        this.tempMax = input;
    }
}