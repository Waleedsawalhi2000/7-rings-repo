package classes.repository;

import classes.Vlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VlanRepository extends JpaRepository<Vlan,Integer> {
}
