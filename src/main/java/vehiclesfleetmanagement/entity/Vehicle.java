package vehiclesfleetmanagement.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Document(collection = "vehicles")
public class Vehicle {

	@Id
	private String id;

	private String licensePlate;

	private String vehicleType;

	private String cargoType;

	private LocalDate itp;

	private LocalDate rca;

	private LocalDate rovinieta;

	private LocalDate cargoInsurance;

	private int itpPriority;

	private int rovinietaPriority;

	private int rcaPriority;

	private int cargoInsurancePriority;
}
