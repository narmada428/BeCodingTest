package com.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Rank {

	private static Map<String, Integer> map = new HashMap<>();

	public static void main(String[] args) {
		ValueComparator v = new ValueComparator(map);
		TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>(v);
		try (Scanner scan = new Scanner(System.in)) {
			System.out.println("Enter File Name or with complete location :");
			String fileName = scan.nextLine();
			try (Scanner fileScan = new Scanner(new File(fileName))) {
				while (fileScan.hasNextLine()) {
					String matchResult = fileScan.nextLine();
					splitTeamAndScore(matchResult);
				}
				System.out.println("File Processing complete. Result is: ");
				sorted_map.putAll(map);
				int i = 1;
				for (Map.Entry entry : sorted_map.entrySet()) {
					int value = (int) entry.getValue();
					String point = "pt";
					if (value > 2 || value == 0) {
						point = "pts";
					}
					System.out.println(i + ". " + entry.getKey() + ", " + value + " " + point);
					i++;
				}
			} catch (FileNotFoundException e) {
				System.out.println("File Not Found please share valid Path....");
			}
		}
	}

	public static void splitTeamAndScore(String matchData) {
		String[] teamAndScoreArr = matchData.split(", ");
		String team1 = teamAndScoreArr[0];
		String team2 = teamAndScoreArr[1];
		// team1 data
		List<String> team1NameAndPoint = getTeamNameAndPoint(team1);
		String team1Name = team1NameAndPoint.get(0);
		Integer team1Point = Integer.parseInt(team1NameAndPoint.get(1));

		// team 2 data
		List<String> team2NameAndPoint = getTeamNameAndPoint(team2);
		String team2Name = team2NameAndPoint.get(0);
		Integer team2Point = Integer.parseInt(team2NameAndPoint.get(1));

		if (team1Point > team2Point) {
			if (map.containsKey(team1Name)) {
				int currPoint = map.get(team1Name);
				map.replace(team1Name, currPoint + 3);
			} else {
				map.put(team1Name, 3);
			}
			if (!map.containsKey(team2Name)) {
				map.put(team2Name, 0);
			}
		} else if (team1Point == team2Point) {
			if (map.containsKey(team1Name)) {
				int currPoint = map.get(team1Name);
				map.replace(team1Name, currPoint + 1);
			} else {
				map.put(team1Name, 1);
			}
			if (map.containsKey(team2Name)) {
				int currPoint = map.get(team2Name);
				map.replace(team2Name, currPoint + 1);
			} else {
				map.put(team2Name, 1);
			}
		} else if (team1Point < team2Point) {
			if (map.containsKey(team2Name)) {
				int currPoint = map.get(team2Name);
				map.replace(team2Name, currPoint + 3);
			} else {
				map.put(team2Name, 3);
			}
			if (!map.containsKey(team1Name)) {
				map.put(team1Name, 0);
			}

		}
	}

	public static List<String> getTeamNameAndPoint(String team) {
		String teamPoint, teamName = "";
		String[] teamData = team.split(" ");
		if (teamData.length > 2) {
			teamPoint = teamData[teamData.length - 1];
			teamName = teamData[0];
			for (int i = 1; i < teamData.length - 1; i++) {
				teamName += " " + teamData[i];
			}
		} else {
			teamPoint = teamData[1];
			teamName = teamData[0];
		}
		return Arrays.asList(teamName, teamPoint);
	}
}
class ValueComparator implements Comparator<String> {
    Map<String, Integer> base;

    public ValueComparator(Map<String, Integer> base) {
        this.base = base;
    }

    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } 
    }
}

