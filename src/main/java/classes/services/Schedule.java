package classes.services;

import classes.main.classes.Database;
import classes.Main;
import classes.main.classes.Vlan;
import classes.main.classes.VlanMirror;
import classes.fucntionClasses.VlanFunctions;
import classes.fucntionClasses.VlanMirrorFunctions;
import classes.repository.VlanMirrorRepository;
import classes.repository.VlanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduling.enable", matchIfMissing = true)
public class Schedule {
    static final Logger logger = LoggerFactory.getLogger(Main.class);
    final
    VlanMirrorRepository vlanMirrorRepository;
    final
    VlanRepository repo;

    int count = 0;
    int count1 = 0;

    List<Vlan> vlanListClone = new ArrayList<>();
    List<VlanMirror> vlanMirrorListClone = new ArrayList<>();

    final List<Vlan> vlanList = new ArrayList<>();
    final List<VlanMirror> vlanMirrorList = new ArrayList<>();



    @Autowired
    public Schedule(VlanMirrorRepository vlanMirrorRepository, VlanRepository repo) {
        this.vlanMirrorRepository = vlanMirrorRepository;
        this.repo = repo;
    }

    @Scheduled(cron = "${cron.delay.run}")
    public void run() {
        VlanFunctions vlan = new VlanFunctions(vlanMirrorRepository, repo);
        VlanMirrorFunctions vlanMirror = new VlanMirrorFunctions(vlanMirrorRepository, repo);


        List<Vlan> vlanList = repo.findAll();
        List<VlanMirror> vlanMirrorList = vlanMirrorRepository.findAll();


        int result = vlanList.size() > vlanListClone.size() || vlanMirrorList.size() > vlanMirrorListClone.size() ? 1 : 0;

        switch (result) {
            case 0 -> {
                if (count == 0) {
                    vlanListClone = vlanList;
                    vlanMirrorListClone = vlanMirrorList;
                    count++;
                }
                if (vlanList.size() == vlanMirrorList.size()) {
                    break;
                }
                if (vlanList.size() > vlanMirrorList.size()) {
                    vlanMirror.delete();
                    vlan.delete();
                }
                if (vlanList.size() < vlanMirrorList.size()) {
                    vlan.delete();
                    vlanMirror.delete();
                }
            }
            case 1 -> {
                if (vlanList.size() > vlanMirrorList.size()) {
                    vlan.post();
                }
                if (vlanList.size() < vlanMirrorList.size()) {
                    vlanMirror.post();
                }
            }
        }
        vlanListClone = vlanList;
        vlanMirrorListClone = vlanMirrorList;
    }

    Database database= null;
    @Scheduled(cron = "${cron.delay.update}")
    public void update() {
        /*Database database = new Database(vlanList);*/
        VlanFunctions vlan = new VlanFunctions(vlanMirrorRepository, repo);
        VlanMirrorFunctions vlanMirror = new VlanMirrorFunctions(vlanMirrorRepository, repo);
        vlanList.addAll(repo.findAll());
        vlanMirrorList.addAll(vlanMirrorRepository.findAll());
        if (repo.findAll().toString().equals(vlanMirrorRepository.findAll().toString()) && count1==0) {
            database=new Database(repo.findAll());
            count1++;
            return;
        }
        if (!repo.findAll().toString().equals(vlanMirrorRepository.findAll().toString()) && repo.findAll().size()==vlanMirrorRepository.findAll().size()) {

            if (vlanMirrorRepository.findAll().toString().equals(database.getDatabase().toString())) {
                vlan.update();
                vlanMirror.update();
                logger.info("vlan updated");
            } else{
                vlanMirror.update();
                vlan.update();
                logger.info("vlan mirror updated");
            }

            database.getDatabase().clear();
            count1=0;
        }
        /*database.setDatabase(vlanList);*/

    }
}


