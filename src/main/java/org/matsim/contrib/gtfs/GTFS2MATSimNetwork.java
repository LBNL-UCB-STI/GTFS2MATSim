/* *********************************************************************** *
 * project: org.matsim.*
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2017 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */

/**
 * 
 */
package org.matsim.contrib.gtfs;

import java.time.LocalDate;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.network.io.NetworkWriter;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.geometry.CoordinateTransformation;
import org.matsim.core.utils.geometry.transformations.TransformationFactory;
import org.matsim.pt.transitSchedule.api.TransitScheduleReader;
import org.matsim.pt.transitSchedule.api.TransitScheduleWriter;
import org.matsim.pt.utils.CreatePseudoNetwork;
import org.matsim.pt.utils.CreateVehiclesForSchedule;
import org.matsim.vehicles.MatsimVehicleWriter;
import org.matsim.vehicles.VehicleWriterV1;

/**
 * @author  jbischoff
 * This is an example script that utilizes GTFS2MATSim and creates a pseudo network and vehicles using MATSim standard API functionality.
 */

public final class GTFS2MATSimNetwork {

	public static void main(String[] args) {
	
		//this was tested for the latest VBB GTFS, available at 
		// http://www.vbb.de/de/article/fahrplan/webservices/datensaetze/1186967.html
		String gtfsZipFile = args[0]; // gtfs zip file location
		String scenarioCRS = args[1]; // CRS [ex. EPSG:26910]

		//input data
		CoordinateTransformation ct = TransformationFactory.getCoordinateTransformation(TransformationFactory.WGS84, scenarioCRS);
		LocalDate date = LocalDate.parse("2020-06-25");

		//output files
		String networkFile = "network.xml";
		Scenario scenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());

		//Convert GTFS
		RunGTFS2MATSim.convertGTFSandAddToScenario(scenario,gtfsZipFile,date,ct,true,true);

		//Write out network, vehicles and schedule
		new NetworkWriter(scenario.getNetwork()).write(networkFile);

		// TODO doesn't exit gracefully after writing network file.
		System.exit(0);
	}
}