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

import ML.DataSet;
import ML.KNN;

public class PulseSensor_Edgent_ML_KNN {

	static MyFirebase myFirebase;

	public static void main(String[] args) throws Exception {
		myFirebase = new MyFirebase();

		Pulse_Sensor sensor = new Pulse_Sensor();

		DirectProvider dp = new DevelopmentProvider();
		Topology top = dp.newTopology("STM32_PulseSensor_ML");

		TStream<Map<String, Double>> readings = top.poll(sensor, 1, TimeUnit.SECONDS);
		TStream<Double> IBIData = readings.map(t -> t.get("IBI").doubleValue());
		readings.print();
		/**
		 * Create a window on the stream of the last 10 readings partitioned
		 */
		TWindow<Double, Integer> window = IBIData.last(10, Functions.unpartitioned());

		TStream<JsonObject> joResultMap = window.aggregate((List<Double> list, Integer partition) -> {
			double meanOfLast3 = 0.0, lastIBI = 0.0, secondToLastIBI = 0.0, thirdToLastIBI = 0.0, meanOfLast10 = 0.0;
			double sumOfLast3 = 0.0, sumOfLast10 = 0.0;
			int counter = 1;
			int fibrillation = 0;
			if (list.size() != 10)
				return null;
			for (Double val : list) {
				if (counter == 8)
					thirdToLastIBI = val;
				else if (counter == 9)
					secondToLastIBI = val;
				else if (counter == 10) {
					lastIBI = val;
					sumOfLast3 = thirdToLastIBI + secondToLastIBI + lastIBI;
				}

				sumOfLast10 += val;

				counter++;
			}
			meanOfLast3 = sumOfLast3 / 3;
			meanOfLast10 = sumOfLast10 / 10;

			JsonObject jo = new JsonObject();
			jo.addProperty("size", list.size());
			jo.addProperty("thirdToLastIBI", thirdToLastIBI);
			jo.addProperty("secondToLastIBI", secondToLastIBI);
			jo.addProperty("lastIBI", lastIBI);
			jo.addProperty("meanOfLast3", meanOfLast3);
			jo.addProperty("meanOfLast10", meanOfLast10);

			double hb[] = { meanOfLast3, lastIBI, secondToLastIBI, thirdToLastIBI, meanOfLast10 };
			KNN knn = new KNN();
			DataSet ds = new DataSet();
			fibrillation = knn.performKNN(ds.getInstances(), ds.getClasses(), hb, 1, ds.getRows(), ds.getColumns());
			jo.addProperty("fibrillation", fibrillation);
			return jo;

		});

		joResultMap.print();

		/**
		 * Send Result to Firebase Realtime Database
		 */

		joResultMap.sink(tuple -> {
			String sIBI, sBPM, sFibrillation = "FALSE";
			sIBI = tuple.get("meanOfLast10").toString();
			sBPM = (60000 / Double.parseDouble(sIBI)) + "";
			if (tuple.get("meanOfLast10").toString().contains("1"))
				sFibrillation = "TRUE";

			Map<String, String> aggObject = new HashMap<String, String>();
			aggObject.put("IBI", sIBI);
			aggObject.put("BPM", sBPM);
			aggObject.put("Fibrillation", sFibrillation);
			
			myFirebase.update_push(aggObject,
					"FibrillationHeartRateModel/dPhyK0QQ3Dhkp2heNiyM3d7aL333/-L8HmWFXh_k5vUUDx1Bt");
		//	myFirebase.update_set(aggObject,
				//	"StateHeartRateModel/dPhyK0QQ3Dhkp2heNiyM3d7aL333/-L8HmWFXh_k5vUUDx1Bt/Fibrillation");

		});

		dp.submit(top);
		System.out.println(dp.getServices().getService(HttpServer.class).getConsoleUrl());
	}

}
