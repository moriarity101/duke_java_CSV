
/**
 * Write a description of tester here.
 * 
 * @author (Himanshu Dutta) 
 * 
 */
import edu.duke.*;
import org.apache.commons.csv.*;
public class tester_export {
    public void countryInfo(CSVParser parser, String country){
        System.out.println("The detail about the following country is:");
        for(CSVRecord record : parser){
            String ctry = record.get("Country");
            if(ctry.contains(country)){
                System.out.println(country + ": " + record.get("Exports") + ": " + record.get("Value (dollars)"));
            }
        }
    }
    public void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2){
        System.out.println("The countries with both these export items are:");
        for(CSVRecord record : parser){
            String exp = record.get("Exports");
            if(exp.contains(exportItem1) && exp.contains(exportItem2)){
                System.out.println(record.get("Country"));
            }
        }
    }
    public void numberOfExporters(CSVParser parser, String exportItem){
        System.out.print("The total number of countries exporting this item is: ");
        int c = 0;
        for (CSVRecord record : parser){
            String exp = record.get("Exports");
            if(exp.contains(exportItem)){
                c += 1;
            }
        }
        System.out.println(c);
    }
    public void bigExporters(CSVParser parser, String value){
        System.out.println("The number of countries which export this item is:");
        
        for(CSVRecord record : parser){
            String val = record.get("Value (dollars)");
            if(val.length() >= value.length()){
                  System.out.println(record.get("Country") + ": " + record.get("Value (dollars)"));              
            }
            
        }
        
    }
    
    public void tester(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        countryInfo(parser, "Nauru");
        parser = fr.getCSVParser();
        listExportersTwoProducts(parser,"cotton", "flowers");
        parser = fr.getCSVParser();
        numberOfExporters(parser,"cocoa");
        parser = fr.getCSVParser();
        bigExporters(parser, "$999,999,999,999");
        
    }
    
}
