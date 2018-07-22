package STM32;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.concurrent.TimeUnit;

import org.apache.edgent.analytics.math3.Aggregations;

import org.apache.edgent.analytics.math3.ResultMap;

import org.apache.edgent.analytics.math3.stat.Statistic2;

import org.apache.edgent.console.server.HttpServer;
import org.apache.edgent.function.Functions;
import org.apache.edgent.providers.development.DevelopmentProvider;
import org.apache.edgent.providers.direct.DirectProvider;

import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.TWindow;
import org.apache.edgent.topology.Topology;

import com.google.gson.JsonObject;

public class PulseSensor_Edgent_Aggregation {
	static  String sMax="0",sMin="0",sMean="0",sStddev="0";
	static   MyFirebase myFirebase;
	public static void main(String[] args) throws Exception {
		myFirebase = new MyFirebase();
	
		Pulse_Sensor sensor = new Pulse_Sensor();
		
		DirectProvider dp = new DevelopmentProvider();
		Topology top = dp.newTopology("STM32_PulseSensor_Aggregation");
	
	
		
		TStream<Map<String, Double>> readings = top.poll(sensor, 1, TimeUnit.SECONDS);
		TStream<Double> BPMData = readings.map(t -> t.get("BPM").doubleValue());
		/**
		 * Create a window on the stream of the last 10 readings partitioned
		 */
		TWindow<Double, Integer> window = BPMData.last(10, TimeUnit.SECONDS, Functions.unpartitioned());
		/**
		 * reduce the readings to one average value every window batch. Once the
		 * window is full, the batch of tuples is aggregated, the result is
		 * added to the output stream and the window is cleared. The next
		 * aggregation isn't generated until the window is full again. Aggregate
		 * the windows calculating the min, max, mean and standard deviation
		 */
		TStream<ResultMap> aggResults = window.batch((List<Double> list, Integer partition) -> Aggregations
				.aggregateN(list, Statistic2.MAX, Statistic2.MIN, Statistic2.MEAN, Statistic2.STDDEV));

		TStream<JsonObject> joResultMap = aggResults.map(ResultMap.toJsonObject());
		joResultMap.print();
		
		/**
		 * Send Result to Firebase Realtime Database
		 */
		joResultMap.sink(tuple -> { 
		sMax=tuple.get("MAX").toString(); 
		sMin=tuple.get("MIN").toString(); 
		sMean=tuple.get("MEAN").toString(); 
		sStddev=tuple.get("STDDEV").toString(); 
		
		Map<String, String> aggObject = new HashMap<String, String>();
		aggObject.put("MAX",sMax);
		aggObject.put("MIN",sMin);
		aggObject.put("MEAN",sMean);
		aggObject.put("STDDEV",sStddev);
		myFirebase.update_push(aggObject, "AggregationHeartRateModel/dPhyK0QQ3Dhkp2heNiyM3d7aL333/-L8HmWFXh_k5vUUDx1Bt");
		//myFirebase.update_set(aggObject, "StateHeartRateModel/dPhyK0QQ3Dhkp2heNiyM3d7aL333/-L8HmWFXh_k5vUUDx1Bt/Aggregation");
	   
		 });
		dp.submit(top);
		System.out.println(dp.getServices().getService(HttpServer.class).getConsoleUrl());
	}

}
