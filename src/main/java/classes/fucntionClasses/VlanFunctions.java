package classes.fucntionClasses;

import classes.Main;
import classes.main.classes.Vlan;
import classes.main.classes.VlanMirror;
import classes.repository.VlanMirrorRepository;
import classes.repository.VlanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;
import java.util.List;

public class VlanFunctions {
    static final Logger logger = LoggerFactory.getLogger(Main.class);
    final
    VlanRepository repo;
    final
    VlanMirrorRepository vlanMirrorRepository;
    @Autowired
    public VlanFunctions(VlanMirrorRepository vlanMirrorRepository, VlanRepository repo) {
        this.vlanMirrorRepository = vlanMirrorRepository;
        this.repo = repo;
    }

    public void update() {
        List<Vlan> vlanList=repo.findAll();
        for (Vlan vlan : vlanList) {
            repo.save(vlan);
            VlanMirror mirror = new VlanMirror();
            mirror.setAddress(vlan.getAddress());
            mirror.setId(vlan.getId());
            mirror.setType(vlan.getType());
            vlanMirrorRepository.save(mirror);
        }
    }

    public void post() {
        List<Vlan> vlanList=repo.findAll();
        List<VlanMirror> vlanMirrorList = vlanMirrorRepository.findAll();
        for (Vlan vlan : vlanList) {
            if (vlanMirrorRepository.findById(vlan.getId()).isEmpty()) {
                VlanMirror vlanMirror = new VlanMirror();
                vlanMirror.setId(vlan.getId());
                vlanMirror.setType(vlan.getType());
                vlanMirror.setAddress(vlan.getAddress());
                vlanMirrorRepository.save(vlanMirror);
                logger.info("record posted in vlan mirror:" + LocalTime.now());
                return;
            }
        }
    }

    public void delete() {
        List<Vlan> vlanList=repo.findAll();
        List<VlanMirror> vlanMirrorList = vlanMirrorRepository.findAll();
        for (VlanMirror vlanMirror : vlanMirrorList) {
            if (repo.findById(vlanMirror.getId()).isEmpty()) {
                vlanMirrorRepository.deleteById(vlanMirror.getId());
                logger.info("record deleted:" + LocalTime.now());
            }
        }
    }
    public List<Vlan> getData(){
        return repo.findAll();
    }
}
