package classes;

import org.hibernate.annotations.Synchronize;

import javax.persistence.*;

@Entity
@Synchronize("VlanMirror")
public class Vlan{
    @Id
    private int id;
    private String type;
    private String address;
    @OneToOne
    @Transient
    private VlanMirror vlanMirror;

    public VlanMirror getVlanMirror() {
        return vlanMirror;
    }

    public void setVlanMirror(VlanMirror vlanMirror) {
        this.vlanMirror = vlanMirror;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", address='" + address + '\'' +
                '}';
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
