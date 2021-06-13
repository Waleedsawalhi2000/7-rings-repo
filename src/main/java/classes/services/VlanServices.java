package classes.services;
import classes.Vlan;
import classes.VlanMirror;
import classes.repository.VlanMirrorRepository;
import classes.repository.VlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
public class VlanServices {
    @Autowired
    VlanRepository repo;
    @Autowired
    VlanMirrorRepository mirrorRepo;

    @GetMapping("/vlan")
    public List<Vlan> home() {
        return repo.findAll();
    }

    @GetMapping("/vlan/{id}")
    public Optional<Vlan> getVlan(@PathVariable("id") int id) {
        return repo.findById(id);
    }

    @DeleteMapping("/vlan/{id}")
    public String deleteVlan(@PathVariable("id") int id) {
        repo.deleteById(id);
        mirrorRepo.deleteById(id);
        return "deleted";
    }

    @PutMapping("/vlan/{id}")
    public String update(@RequestBody Vlan vlan) {
        vlan.setId(vlan.getId());
        repo.save(vlan);
        VlanMirror vlanMirror = new VlanMirror();
        vlanMirror.setType(vlan.getType());
        vlanMirror.setId(vlan.getId());
        vlanMirror.setAddress(vlan.getAddress());
        vlanMirror.setVlan(vlan);
        mirrorRepo.save(vlanMirror);
        return "updated";
    }

    @PostMapping("/vlan")
    public String post(@RequestBody Vlan vlan) {
            if (repo.findById(vlan.getId()).isPresent())
                throw new IllegalStateException("this id is already entered in the database");

        repo.save(vlan);
        VlanMirror vlanMirror=new VlanMirror();
        vlanMirror.setAddress(vlan.getAddress());
        vlanMirror.setId(vlan.getId());
        vlanMirror.setType(vlan.getType());
        vlanMirror.setVlan(vlan);
        mirrorRepo.save(vlanMirror);
        return "posted";
    }
}
