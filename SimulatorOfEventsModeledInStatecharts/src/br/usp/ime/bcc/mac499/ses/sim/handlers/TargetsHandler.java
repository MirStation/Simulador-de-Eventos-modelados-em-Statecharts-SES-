package br.usp.ime.bcc.mac499.ses.sim.handlers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import br.usp.ime.bcc.mac499.ses.sim.Simulator;

public class TargetsHandler {
	
	private void targetPrompt(Simulator simulator, File[] files) {
		int id, pickedNumber;
		String input;
		Map<Integer, File> filesMap = new HashMap<>();
		Scanner scanner = new Scanner(System.in);
		System.out.println(":[");
		System.out.println("The default target file doesn't appear to be in the folder targets!");
		while (true) {
			System.out.println("Please, select one of the alternatives below.");
			id = 0;
			for (File file : files) {
				System.out.println((++id) + " - " + file.getName());
				filesMap.put(id, file);
			}
			System.out.println("Enter the number of the target file you wish to open:");
			input = scanner.next();
			try {
				pickedNumber = Integer.parseInt(input);
				if (pickedNumber >= 1 && pickedNumber <= id) {
					simulator.setTargetFile(filesMap.get(pickedNumber));
					simulator.setTargetName(filesMap.get(pickedNumber).getName());
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println(input + " is not a valid number!");
			}
		}
		scanner.close();
	}
	
	public void processTargets(Simulator simulator) {
		File targets = new File("targets");
		File []files;
		boolean targetFileExist = false;
		if (targets.listFiles().length > 0) {
			files = targets.listFiles();
			for (File file : files) {
				if (file.getName().equals(simulator.getTargetName())) {
					targetFileExist = true;
					simulator.setTargetFile(file);
					break;
				}
			}
			if (!targetFileExist) {
				if (files.length == 1) {
					simulator.setTargetFile(files[0]);
					simulator.setTargetName(files[0].getName());
				} else {
					targetPrompt(simulator, files);
				}
			}	
		}
	}

}
