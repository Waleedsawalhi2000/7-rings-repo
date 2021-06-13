package classes.fucntionClasses;

import classes.Main;
import classes.Vlan;
import classes.VlanMirror;
import classes.repository.VlanMirrorRepository;
import classes.repository.VlanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;
import java.util.List;

public class VlanMirrorFunctions {
    static final Logger logger = LoggerFactory.getLogger(Main.class);
    final
    VlanRepository repo;
    final
    VlanMirrorRepository vlanMirrorRepository;
    @Autowired
    public VlanMirrorFunctions(VlanMirrorRepository vlanMirrorRepository, VlanRepository repo) {
        this.vlanMirrorRepository = vlanMirrorRepository;
        this.repo = repo;
    }

    public void update() {
        List<VlanMirror> vlanMirrorList = vlanMirrorRepository.findAll();
        for (VlanMirror mirror : vlanMirrorList) {
            vlanMirrorRepository.save(mirror);
            Vlan vlan = new Vlan();
            vlan.setAddress(mirror.getAddress());
            vlan.setId(mirror.getId());
            vlan.setType(mirror.getType());
            repo.save(vlan);
        }
    }

    public void post() {
        List<Vlan> vlanList=repo.findAll();
        List<VlanMirror> vlanMirrorList = vlanMirrorRepository.findAll();
        for (VlanMirror mirror : vlanMirrorList) {
            if (repo.findById(mirror.getId()).isEmpty()) {
                Vlan vlan = new Vlan();
                vlan.setType(mirror.getType());
                vlan.setAddress(mirror.getAddress());
                vlan.setId(mirror.getId());
                repo.save(vlan);
                logger.info("record posted in vlan:" + LocalTime.now());
                return;
            }
        }
    }

    public void delete() {
        List<Vlan> vlanList=repo.findAll();
        List<VlanMirror> vlanMirrorList = vlanMirrorRepository.findAll();
        for (Vlan vlan : vlanList) {
            if (vlanMirrorRepository.findById(vlan.getId()).isEmpty()) {
                repo.deleteById(vlan.getId());
                logger.info("record deleted:" + LocalTime.now());
            }
        }
    }

    public List<VlanMirror> getData(){
        return vlanMirrorRepository.findAll();
    }


}
