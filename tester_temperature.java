
/**
 * Write a description of tester_temprature here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.io.File;
import java.lang.*;
import edu.duke.*;
import org.apache.commons.csv.*;
import edu.duke.DirectoryResource;
public class tester_temperature {
    public CSVRecord coldestHourInFile(CSVParser parser){
        CSVRecord leastSoFar = null;
        for (CSVRecord data : parser){
            if (leastSoFar == null){
                leastSoFar = data;
            }
            else {
                double c_temp = Double.parseDouble(data.get("TemperatureF"));
                double l_temp = Double.parseDouble(leastSoFar.get("TemperatureF"));
                if (c_temp<l_temp)
                leastSoFar = data;
            }
        }
        return leastSoFar;
    }
    public CSVRecord lowestHumidityInFile(CSVParser parser){
        CSVRecord leastSoFar = null;
        for (CSVRecord data : parser){
            if (data.get("Humidity").equals("N/A")) continue;
            if (leastSoFar == null){
                leastSoFar = data;
            }
            
            else {                                    
                    int c_humid = Integer.parseInt(data.get("Humidity"));
                    int l_humid = Integer.parseInt(leastSoFar.get("Humidity"));
                    if (c_humid < l_humid)
                    leastSoFar = data;
                }                              
        }
        return leastSoFar;
    }
    public String lowestHumidityInManyFiles(){
        File lowestFileSoFar = null;
        CSVRecord leastSoFar = null;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            CSVRecord current = lowestHumidityInFile(fr.getCSVParser());
            if (leastSoFar == null){
                leastSoFar = current;
                lowestFileSoFar = f;
            }
            else {
                if(Integer.parseInt(current.get("Humidity")) < Integer.parseInt(leastSoFar.get("Humidity"))){
                    leastSoFar = current;
                    lowestFileSoFar = f;
                }
            }
        }
        System.out.println("Least humid day was " + leastSoFar.get("Humidity")+ " in file " + lowestFileSoFar.getName());
        
          return lowestFileSoFar.getName();          
    }
    public String fileWithColdestTemperature(){
        File coldestFileSoFar = null;
        CSVRecord leastSoFar = null;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            CSVRecord current = coldestHourInFile(fr.getCSVParser());
            if(Double.parseDouble(current.get("TemperatureF")) == -9999) continue;
            if (leastSoFar == null){
                leastSoFar = current;
                coldestFileSoFar = f;
            }
            else {
                if(Double.parseDouble(current.get("TemperatureF")) < Double.parseDouble(leastSoFar.get("TemperatureF"))){
                    leastSoFar = current;
                    coldestFileSoFar = f;
                }
            }
        }
        System.out.println("Coldest day was in file " + coldestFileSoFar.getName());
        FileResource f_l = new FileResource(coldestFileSoFar);
        
        System.out.println("Coldest temperature on that day was found to be " + coldestHourInFile(f_l.getCSVParser()).get("TemperatureF"));
        System.out.println("All the Temperatures on the coldest day were:");
        for (CSVRecord record : f_l.getCSVParser()){
            System.out.println(record.get("DateUTC") + "     " + record.get("TimeEST") + "      " + record.get("TemperatureF"));
        }
          return coldestFileSoFar.getName();          
    }
    
    public double averageTemperatureInFile(CSVParser parser){
        double sum_t=0;
        int count=0;
        for (CSVRecord record : parser){
            sum_t += Double.parseDouble(record.get("TemperatureF"));
            count += 1;
        }
        
        return (sum_t/count);
        
    }
    public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value){
        double sum_t = 0;
        int count = 0;
        for (CSVRecord record : parser){
            if (record.get("Humidity").equals("N/A")) continue;
            else if (Integer.parseInt(record.get("Humidity")) >= value){
                sum_t += Double.parseDouble(record.get("TemperatureF"));
                count += 1;
            }
        }
        return (sum_t/count);
    }
    public void main() {
        FileResource fr = new FileResource();
        CSVRecord least = coldestHourInFile(fr.getCSVParser());
        System.out.println("Coldest temprature was found to be " + least.get("TemperatureF") + " at " + least.get("TimeEST")); 
        fileWithColdestTemperature();
        fr = new FileResource();
        least = lowestHumidityInFile(fr.getCSVParser());
        System.out.println("Least humidity was found to be " + least.get("Humidity") + " at " + least.get("TimeEDT"));
        lowestHumidityInManyFiles();
        fr = new FileResource();
        double average = averageTemperatureInFile(fr.getCSVParser());
        System.out.println("The average temperature was found to be " + average);
        fr = new FileResource();
        average = averageTemperatureWithHighHumidityInFile(fr.getCSVParser(),80);
        System.out.println("The average temperature with humidity higher than 71 was found to be " + average);
    }

}
