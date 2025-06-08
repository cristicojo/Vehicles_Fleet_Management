package vehiclesfleetmanagement.service;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import vehiclesfleetmanagement.entity.Vehicle;
import vehiclesfleetmanagement.repository.VehicleRepository;
import vehiclesfleetmanagement.sort.SortByCargoInsurancePriority;
import vehiclesfleetmanagement.sort.SortByItpPriority;
import vehiclesfleetmanagement.sort.SortByRcaPriority;
import vehiclesfleetmanagement.sort.SortByRovinietaPriority;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository repo;
    private final MongoTemplate mongoTemplate;


    public List<Vehicle> getAll() {
        return repo.findAll();
    }

    public Vehicle getById(String id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not found a vehicle with id: " + id));
    }


    public Vehicle create(Vehicle newEmp) {
        return repo.save(newEmp);
    }


    public List<Vehicle> createAll(List<Vehicle> vehicleList) {
        return repo.saveAll(vehicleList);
    }


    public Vehicle updateById(String id, Vehicle newVehicle) {

        Vehicle oldVehicle = getById(id);
        oldVehicle.setLicensePlate(newVehicle.getLicensePlate());
        oldVehicle.setVehicleType(newVehicle.getVehicleType());
        oldVehicle.setCargoType(newVehicle.getCargoType());
        oldVehicle.setItp(newVehicle.getItp());
        oldVehicle.setRca(newVehicle.getRca());
        oldVehicle.setRovinieta(newVehicle.getRovinieta());
        oldVehicle.setCargoInsurance(newVehicle.getCargoInsurance());
        return repo.save(oldVehicle);
    }

    public List<Vehicle> updateAndSortPriorities(List<Vehicle> vehicleList) {
        vehicleList.forEach(vehicle -> {

            //sort asc by itp priority
            long itpPriority = ChronoUnit.DAYS.between(LocalDate.now(), vehicle.getItp());
            if (itpPriority >= 0 && itpPriority <= 7) {
                vehicle.setItpPriority(1);
            } else {
                if (itpPriority >= 8 && itpPriority <= 14) {
                    vehicle.setItpPriority(2);
                } else {
                    if (itpPriority >= 15) {
                        vehicle.setItpPriority(3);
                    }
                }
            }

            //sort asc by rca priority
            long rcaPriority = ChronoUnit.DAYS.between(LocalDate.now(), vehicle.getRca());
            if (rcaPriority >= 0 && rcaPriority <= 7) {
                vehicle.setRcaPriority(1);
            } else {
                if (rcaPriority >= 8 && rcaPriority <= 14) {
                    vehicle.setRcaPriority(2);
                } else {
                    if (rcaPriority >= 15) {
                        vehicle.setRcaPriority(3);
                    }
                }
            }

            //sort asc by rovinieta priority
            long rovinietaPriority = ChronoUnit.DAYS.between(LocalDate.now(), vehicle.getRovinieta());
            if (rovinietaPriority >= 0 && rovinietaPriority <= 7) {
                vehicle.setRovinietaPriority(1);
            } else {
                if (rovinietaPriority >= 8 && rovinietaPriority <= 14) {
                    vehicle.setRovinietaPriority(2);
                } else {
                    if (rovinietaPriority >= 15) {
                        vehicle.setRovinietaPriority(3);
                    }
                }
            }

            //sort asc by cargoInsurance priority
            long cargoInsurancePriority = ChronoUnit.DAYS.between(LocalDate.now(), vehicle.getCargoInsurance());
            if (cargoInsurancePriority >= 0 && cargoInsurancePriority <= 7) {
                vehicle.setCargoInsurancePriority(1);
            } else {
                if (cargoInsurancePriority >= 8 && cargoInsurancePriority <= 14) {
                    vehicle.setCargoInsurancePriority(2);
                } else {
                    if (cargoInsurancePriority >= 15) {
                        vehicle.setCargoInsurancePriority(3);
                    }
                }
            }
        });

        //save new priorities
        createAll(vehicleList);

        vehicleList.sort(new SortByItpPriority());
        vehicleList.sort(new SortByRcaPriority());
        vehicleList.sort(new SortByRovinietaPriority());
        vehicleList.sort(new SortByCargoInsurancePriority());

        return vehicleList;
    }


    public Map<String, Object> remove(String id) {
        repo.delete(getById(id));

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "Vehicle with id: " + id + " deleted successfully");
        return body;
    }


    public Map<String, Object> removeAll() {
        repo.deleteAll();

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "All vehicles deleted successfully");
        return body;
    }

    public Map<String, String> importJsonFile() throws IOException {

        Map<String, String> body = new LinkedHashMap<>();
        InputStream stream = Files.newInputStream(Paths.get("/Users/cristicojocaru/Documents/old/vehicles.json"));

        //Read each line of the json file. Each file is one observation document.
        List<Document> observationDocuments = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        String line;
        while ((line = br.readLine()) != null) {
            observationDocuments.add(Document.parse(line));
        }

        if (!observationDocuments.isEmpty()) {
            mongoTemplate.getCollection("vehicles").insertMany(observationDocuments);
            //update the new priorities inserted
            updateAndSortPriorities(getAll());
            body.put("message", "Json file imported successfully, " + observationDocuments.size() + " docs imported");
        }

        return body;
    }

    //paging
    public Page<Vehicle> getPage(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        return repo.findAll(pageable);
    }
}