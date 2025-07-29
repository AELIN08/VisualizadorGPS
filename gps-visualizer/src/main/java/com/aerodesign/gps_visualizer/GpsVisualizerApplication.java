package com.aerodesign.gps_visualizer;

import com.aerodesign.gps_visualizer.service.GpsReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class GpsVisualizerApplication {

	public static void main(String[] args) {
		GpsReader.start();
		SpringApplication.run(GpsVisualizerApplication.class, args);
	}


}
