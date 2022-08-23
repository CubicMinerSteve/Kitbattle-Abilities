package me.cubicminer.kbabilities.utils;

import java.util.ArrayList;

import org.bukkit.Location;

public class ExtendedUtils {

	public static ArrayList<Location> getIceCage(Location pLocation) {
		ArrayList<Location> arrayList = new ArrayList<Location>();
		// The following items are 'y-level = -1' blocks. Used as floor.
		arrayList.add(pLocation.clone().add(0.0D, -1.0D, 0.0D));
		// The following items are 'y-level = 2' blocks. Used as ceiling.
		arrayList.add(pLocation.clone().add(0.0D, 2.0D, 0.0D));
		// The following items are 'y-level = 0' blocks.
		arrayList.add(pLocation.clone().add(1.0D, 0.0D, 1.0D));
		arrayList.add(pLocation.clone().add(1.0D, 0.0D, 0.0D));
		arrayList.add(pLocation.clone().add(1.0D, 0.0D, -1.0D));
		arrayList.add(pLocation.clone().add(0.0D, 0.0D, 1.0D));
		arrayList.add(pLocation.clone().add(0.0D, 0.0D, -1.0D));
		arrayList.add(pLocation.clone().add(-1.0D, 0.0D, 1.0D));
		arrayList.add(pLocation.clone().add(-1.0D, 0.0D, 0.0D));
		arrayList.add(pLocation.clone().add(-1.0D, 0.0D, -1.0D));
		arrayList.add(pLocation.clone().add(0.0D, 0.0D, 0.0D));
		// The following items are 'y-level = 1' blocks.
		arrayList.add(pLocation.clone().add(0.0D, 1.0D, 0.0D));
		return arrayList;
	}

}
