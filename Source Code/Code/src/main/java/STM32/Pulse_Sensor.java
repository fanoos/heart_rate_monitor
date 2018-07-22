
package STM32;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.edgent.function.Supplier;

/*Streams of X-NUCLEO-IKS01A2.*/
public class Pulse_Sensor implements Supplier<Map<String,Double>> {
    private static final long serialVersionUID = 1L;
    // Initial environment sensor
    public Double currentBPM = 0.0;
    public Double currentIBI = 0.0;
 
    private ComPort_PulseSensor comPort=null;

    public Pulse_Sensor() {
    	comPort=new ComPort_PulseSensor();
    	try {
			comPort.connect("COM6");
		} catch (Exception e) {
		
			e.printStackTrace();
		}
    }

  
    @Override
    public Map<String, Double> get() {
        /**
         * Read data from Serial Communication
         */
    	currentBPM=comPort.BPM;
    	currentIBI=comPort.IBI;

        Map<String, Double> pressures = new HashMap<String, Double>();
        pressures.put("BPM", currentBPM);
        pressures.put("IBI", currentIBI);
        return pressures;
    }
}
