package classes.repository;

import classes.main.classes.VlanMirror;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VlanMirrorRepository extends JpaRepository<VlanMirror,Integer> {
    @Query("from Vlan order by id asc")
    List<VlanMirror> findAllById();
}
