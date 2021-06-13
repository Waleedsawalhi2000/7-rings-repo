package classes;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.Synchronize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
public class VlanMirror {
    @Id
    private int id;
    private String type;
    private String address;

    @Override
    public String toString() {
        return "VlanMirror{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", address='" + address + '\'' +
                ", vlan=" + vlan +
                '}';
    }
    @OneToOne(fetch = FetchType.EAGER)
    @Transient
    private Vlan vlan;

    public Vlan getVlan() {
        return vlan;
    }

    public void setVlan(Vlan vlan) {
        this.vlan = vlan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
