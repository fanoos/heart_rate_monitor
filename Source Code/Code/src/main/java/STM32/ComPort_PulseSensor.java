package STM32;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;

public class ComPort_PulseSensor {
	public static double BPM = 0.0, IBI = 0.0;

	public ComPort_PulseSensor() {
		super();
	}

	void connect(String portName) throws Exception {
		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
		if (portIdentifier.isCurrentlyOwned()) {
			System.out.println("Error: Port is currently in use");
		} else {

			CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

			if (commPort instanceof SerialPort) {
				SerialPort serialPort = (SerialPort) commPort;

				serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
						SerialPort.PARITY_NONE);

				InputStream in = serialPort.getInputStream();
				// OutputStream out = serialPort.getOutputStream();

				(new Thread(new SerialReader(in))).start();

			} else {
				System.out.println("Error: Only serial ports are handled by this example.");
			}
		}
	}

	/** */
	public static class SerialReader implements Runnable {
		InputStream in;

		public SerialReader(InputStream in) {
			this.in = in;
		}

		public void run() {
			byte[] buffer = new byte[1024];
			int len = -1;
			try {
				while ((len = this.in.read(buffer)) > -1) {
					try {

						String msg = new String(buffer, 0, len);

						if (msg != null && msg.length() > 2) {
							// System.out.println("result is = " + msg);
							String newline = System.getProperty("line.separator");
							if ((msg.contains("BPM") || msg.contains("IBI")) && (msg.contains(":")) && (!msg.contains(newline))) {
								String[] items = msg.split((","));
								for (int i = 0; i < items.length; i++) {
									if (items[i].contains("BPM")) {

										String[] values = items[i].trim().split(":");
										if (values.length >= 2) {

											BPM = Double.parseDouble(values[1].trim());
											// System.out.println("BPM is= " +
											// BPM);
										}
									}
									if (items[i].contains("IBI") && items[i].contains("|")) {

										String[] values = items[i].split(":");
										if (values.length >= 2) {
											String str = values[1].trim();
											str = str.substring(0, str.length() - 1);

											IBI = Double.parseDouble(str);
											// System.out.println("IBI is= " +
											// IBI);

										}
									}
								}

							}

						}
					} catch (Exception ex) {
						System.out.println("++++++++Error+++++++" + ex.getMessage());
					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		try {
			ComPort_PulseSensor con = new ComPort_PulseSensor();
			con.connect("COM6");

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}