package classes.repository;

import classes.Vlan;
import classes.VlanMirror;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VlanRepository extends JpaRepository<Vlan,Integer> {
    @Query("from VlanMirror order by id asc")
    List<Vlan> findAllById();
}
