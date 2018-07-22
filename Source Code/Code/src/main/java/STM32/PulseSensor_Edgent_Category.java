package STM32;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.edgent.analytics.sensors.Range;
import org.apache.edgent.analytics.sensors.Ranges;
import org.apache.edgent.connectors.iot.IotDevice;
import org.apache.edgent.connectors.iot.QoS;
import org.apache.edgent.connectors.iotp.IotpDevice;
import org.apache.edgent.console.server.HttpServer;
import org.apache.edgent.providers.development.DevelopmentProvider;
import org.apache.edgent.providers.direct.DirectProvider;

import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;

import com.google.gson.JsonObject;

public class PulseSensor_Edgent_Category {
	static MyFirebase myFirebase;
	static  String sBPM="0",sIBI="0",sState="0";
	public static void main(String[] args) throws Exception {
		myFirebase = new MyFirebase();
		Pulse_Sensor sensor = new Pulse_Sensor();

		DirectProvider dp = new DevelopmentProvider();
		Topology top = dp.newTopology("STM32_PulseSensor_Category");

		TStream<Map<String, Double>> readings = top.poll(sensor, 1, TimeUnit.SECONDS);
		//readings.print();

		// Split the stream by comfortable zone category
		List<TStream<Map<String, Double>>> categories = readings.split(5, tuple -> {
			Double bpm = tuple.get("BPM");
			Double ibi = tuple.get("IBI");
			if ((bpm >= 50 && bpm < 120)) {
				// WARM UP
				return 0;
			} else if ((bpm >= 120 && bpm < 140)) {
				// FITNESS
				return 1;
			} else if ((bpm >= 140 && bpm < 160)) {
				// ENDURANCE
				return 2;
			} else if ((bpm >= 140 && bpm < 160)) {
				// HARD CORE
				return 3;
			} else if ((bpm >= 160)) {
				// RED LINE
				return 4;
			}

			return -1;
		});
	
		// Get each individual stream
		TStream<Map<String, Double>> WARM_UP = categories.get(0).tag("WARM_UP");
		TStream<Map<String, Double>> FITNESS = categories.get(1).tag("FITNESS");
		TStream<Map<String, Double>> ENDURANCE = categories.get(2).tag("ENDURANCE");
		TStream<Map<String, Double>> HARD_CORE = categories.get(3).tag("HARD_CORE");
		TStream<Map<String, Double>> RED_LINE = categories.get(4).tag("RED_LINE");
		
		// Category
		TStream<JsonObject> WARM_UPObjects = WARM_UP.map(tuple -> {
			JsonObject jObj = new JsonObject();
			jObj.addProperty("BPM", tuple.get("BPM"));
			jObj.addProperty("IBI", tuple.get("IBI"));
		    jObj.addProperty("State", "WARM UP");
			return jObj;
		}).tag("WARM_UP");
		
		TStream<JsonObject> FITNESSObjects = FITNESS.map(tuple -> {
			JsonObject jObj = new JsonObject();
			jObj.addProperty("BPM", tuple.get("BPM"));
			jObj.addProperty("IBI", tuple.get("IBI"));
		    jObj.addProperty("State", "FITNESS");
			return jObj;
		}).tag("FITNESS");
		
		TStream<JsonObject> ENDURANCEObjects = ENDURANCE.map(tuple -> {
			JsonObject jObj = new JsonObject();
			jObj.addProperty("BPM", tuple.get("BPM"));
			jObj.addProperty("IBI", tuple.get("IBI"));
		    jObj.addProperty("State", "ENDURANCE");
			return jObj;
		}).tag("ENDURANCE");
		
		TStream<JsonObject> HARD_COREObjects = HARD_CORE.map(tuple -> {
			JsonObject jObj = new JsonObject();
			jObj.addProperty("BPM", tuple.get("BPM"));
			jObj.addProperty("IBI", tuple.get("IBI"));
		    jObj.addProperty("State", "HARD CORE");
			return jObj;
		}).tag("HARD_CORE");
		
		TStream<JsonObject> RED_LINEObjects = RED_LINE.map(tuple -> {
			JsonObject jObj = new JsonObject();
			jObj.addProperty("BPM", tuple.get("BPM"));
			jObj.addProperty("IBI", tuple.get("IBI"));
		    jObj.addProperty("State", "RED LINE");
			return jObj;
		}).tag("RED_LINE");

		/**
		 * union two streams to obtain a single stream 
		 */
		TStream<JsonObject> normalSet = WARM_UPObjects.union(FITNESSObjects);

		// Set of streams containing alerts from the other categories
		HashSet<TStream<JsonObject>> othersSet = new HashSet<TStream<JsonObject>>();
		// othersSet.add(tooCold_TooDryObjects);
		othersSet.add(ENDURANCEObjects);
		othersSet.add(HARD_COREObjects);
		othersSet.add(RED_LINEObjects);

		TStream<JsonObject> AllSet = normalSet.union(othersSet);
	
		AllSet.print();
		AllSet.sink(tuple -> {
			if(tuple.isJsonNull()) return;
			sBPM=tuple.get("BPM").isJsonNull()?"NULL":tuple.get("BPM").toString();
			sIBI=tuple.get("IBI").isJsonNull()?"NULL":tuple.get("IBI").toString();
			sState=tuple.get("State").isJsonNull()?"NULL":tuple.get("State").toString();
		
			//System.out.println(sBPM+","+sIBI+","+sState);
			Map<String, String> aggObject = new HashMap<String, String>();
			aggObject.put("BPM",sBPM);
			aggObject.put("IBI",sIBI);
			aggObject.put("STATE",sState);
			
		   myFirebase.update_push(aggObject, "StateHeartRateModel/dPhyK0QQ3Dhkp2heNiyM3d7aL333/-L8HmWFXh_k5vUUDx1Bt");
	
		});
		
		dp.submit(top);
		System.out.println(dp.getServices().getService(HttpServer.class).getConsoleUrl());
	}

}
