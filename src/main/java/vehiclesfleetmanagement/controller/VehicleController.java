package vehiclesfleetmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vehiclesfleetmanagement.entity.Vehicle;
import vehiclesfleetmanagement.service.VehicleService;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@CrossOrigin
public class VehicleController {

    private final VehicleService service;


    @GetMapping(value = "/find/all")
    public ResponseEntity<List<Vehicle>> findAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }


    @GetMapping(value = "/Vehicle/{id}")
    public ResponseEntity<Vehicle> findById(@PathVariable(value = "id") String id) {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }


    @PostMapping(value = "/create")
    public ResponseEntity<Vehicle> save(@RequestBody Vehicle emp) {
        return new ResponseEntity<>(service.create(emp), HttpStatus.CREATED);
    }


    @PostMapping(value = "/create/all")
    public ResponseEntity<List<Vehicle>> saveAll(@RequestBody List<Vehicle> VehicleList) {
        return new ResponseEntity<>(service.createAll(VehicleList), HttpStatus.CREATED);
    }


    @PutMapping(value = "/Vehicle/{id}")
    public ResponseEntity<Vehicle> update(@RequestBody Vehicle newEmp, @PathVariable(value = "id") String id) {
        return new ResponseEntity<>(service.updateById(id, newEmp), HttpStatus.OK);
    }


    @DeleteMapping(value = "/Vehicle/{id}")
    public ResponseEntity<Map<String, Object>> deleteById(@PathVariable(value = "id") String id) {
        return new ResponseEntity<>(service.remove(id), HttpStatus.OK);
    }


    @DeleteMapping(value = "/remove/all")
    public ResponseEntity<Map<String, Object>> deleteAll() {
        return new ResponseEntity<>(service.removeAll(), HttpStatus.OK);
    }

    //BONUS POINTS
    //load a json file
    @GetMapping(value = "/upload")
    public ResponseEntity<Map<String, String>> uploadFile() throws IOException {
        return new ResponseEntity<>(service.importJsonFile(), HttpStatus.OK);
    }

    //paging
    @GetMapping(value = "/page={page}&size={size}")
    public ResponseEntity<Page<Vehicle>> loadPage(@PathVariable(value = "page") int page, @PathVariable(value = "size") int size) {
        return new ResponseEntity<>(service.getPage(page, size), HttpStatus.OK);
    }
}