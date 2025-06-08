package vehiclesfleetmanagement.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import vehiclesfleetmanagement.entity.Vehicle;



@Repository
public interface VehicleRepository extends MongoRepository<Vehicle,String>, PagingAndSortingRepository<Vehicle, String> {
}
