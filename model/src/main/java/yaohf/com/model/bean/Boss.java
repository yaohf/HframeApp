package yaohf.com.model.bean;


import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Conflict;
import com.litesuits.orm.db.annotation.NotNull;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;
import com.litesuits.orm.db.enums.Strategy;

@Table("boss")
public class Boss {

    public static  final String ADDRESS = "add";
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    @Column("_id")
    private int id;

    private String address;
    private String phone;

    public Boss(){}
    public Boss( String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    @NotNull
    @Conflict(Strategy.FAIL)

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public String toString() {
        return "Boss{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
