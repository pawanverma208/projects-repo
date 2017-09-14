package com.sample;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utiles {
	public static void main(String args[]) {
		try {
			splitCSV("D:\\tmp\\myfiles\\tst5.csv", 5);
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}

	}

	public static List<String> splitCSV(String inputfile, int splitCount) throws IOException {
		File file = new File(inputfile);
		Scanner scanner = new Scanner(file);
		int count = 0;
		while (scanner.hasNextLine()) {
			scanner.nextLine();
			count++;
		}
		System.out.println("Lines in the file: " + count);

		int nol = count % splitCount == 0 ? count / splitCount : (count / splitCount + 1);
		System.out.println("Lines per generated file: " + nol);

		// Actual splitting of file into smaller files
		List<String> destFiles = new ArrayList<>();

		FileInputStream fstream = new FileInputStream(inputfile);
		DataInputStream in = new DataInputStream(fstream);

		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		String destFileLoc;
		for (int j = 1; j <= splitCount; j++) {
			destFileLoc = "D:\\tmp\\myfiles\\File" + j + ".txt";
			destFiles.add(destFileLoc);
			FileWriter fstream1 = new FileWriter(destFileLoc); // Destination File Location
			BufferedWriter out = new BufferedWriter(fstream1);
			for (int i = 1; i <= nol; i++) {
				strLine = br.readLine();
				if (strLine != null) {
					out.write(strLine);
					if (i != nol) {
						out.newLine();
					}
				}
			}
			out.close();
		}

		in.close();

		return destFiles;
	}

}
