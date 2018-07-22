package ML;

public class KNN {

	// calculate the class of a set of attributes using KNN
	// int performKNN(double *data, int classesNum[], double d[], int k, int
	// rows, int columns) {
	public int performKNN(double[][] data, int classesNum[], double d[], int k, int rows, int columns) {

		double[] temp2 = new double[rows];
		double[] firstK = new double[k];
		int[] classes = new int[rows];
		int[] kClasses = new int[k];

		euclideanDistanceFromDatabase(d, temp2, data, rows, columns);
		sort(temp2, classes, classesNum, rows);
		extractFirstN(temp2, firstK, classes, kClasses, k);

		// calculate Fashion
		int cont = 0, cont2 = 0, pos = 0, num = 0, i = 0;
		int[] frec = new int[k];
		int[] temp = new int[k];
		int best = 0, bestPosition = 0;

		// initialize the frequency counter
		for (i = 0; i < k; i++) {
			//frec[k] = 0;
			frec[i] = 0;
		}

		// check repetitions of each number
		for (cont = 0; cont < k; cont++) {
			num = classes[cont];
			pos = cont;

			for (cont2 = 0; cont2 < k; cont2++) {
				if (classes[cont2] == num) {
					temp[pos]++;
				}
			}
		}

		best = temp[0];
		bestPosition = 0;

		for (cont = 0; cont < k; cont++) {
			if (temp[cont] > best) {
				bestPosition = cont;
				best = temp[cont];
			}
		}

		return classes[bestPosition];
	}

	// calculate the most common type: Fashion
	int mostFrequentClass(int classes[], int k) {

		int cont = 0, cont2 = 0, pos = 0, num = 0, i = 0;
		int[] freq = new int[k];
		int[] temp = new int[k];
		int best = 0, bestPosition = 0;

		// initialize the frequency counter
		for (i = 0; i < k; i++) {
			freq[k] = 0;
		}

		// check repetitions of each number
		for (cont = 0; cont < k; cont++) {
			num = classes[cont];
			pos = cont;

			for (cont2 = 0; cont2 < k; cont2++) {
				if (classes[cont2] == num) {
					temp[pos]++;
				}
			}
		}

		best = temp[0];
		bestPosition = 0;

		for (cont = 0; cont < k; cont++) {
			if (temp[cont] > best) {
				bestPosition = cont;
				best = temp[cont];
			}
		}

		return classes[bestPosition];
	}

	// extract the first N
	void extractFirstN(double data[], double firstK[], int classes[], int kClasses[], int k) {
		for (int i = 0; i < k; i++) {
			firstK[i] = data[i];
			kClasses[i] = classes[i];
		}
	}

	// calculate Euclidean distance between two points
	double euclideanDistance(double pt1[], double pt2[], int columns) {
		int i;
		double sum = 0;

		for (i = 0; i < columns; i++) {

			sum = Math.pow(pt1[i] - pt2[i], 2) + sum;
		}

		return Math.sqrt(sum);
	}

	// calculate Euclidean distance between a point and the database
	// void euclideanDistanceFromDatabase(double pt1[], double temp[], double
	// *instances, int rows, int columns) {
	void euclideanDistanceFromDatabase(double pt1[], double temp[], double[][] instances, int rows, int columns) {
		int i = 0, j = 0;
		double[] pt2 = new double[columns];

		for (i = 0; i < rows; i++) {
			for (j = 0; j < columns; j++) {
				// pt2[j] = instances[i * columns + j];
				pt2[j] = instances[i][j];
			}
			temp[i] = euclideanDistance(pt1, pt2, columns);
		}
	}

	// sort distances Ascending
	void sort(double data[], int classes[], int classesNo[], int rows) {
		int i = 1, j = 1, f = 1;

		double temp_0;
		int temp_1;

		// create a copy of the original classes
		for (i = 0; i < rows; i++) {
			classes[i] = classesNo[i];
		}

		// sorting
		for (i = 1; (i <= rows) && f == 1; i++) {
			f = 0;
			for (j = 0; j < (rows - 1); j++) {
				if (data[j + 1] < data[j]) { // descending order >, ascending
												// order <
					temp_0 = data[j];
					temp_1 = classes[j];
					data[j] = data[j + 1];
					classes[j] = classes[j + 1];
					data[j + 1] = temp_0;
					classes[j + 1] = temp_1;
					f = 1;
				}
			}
		}
	}

}
