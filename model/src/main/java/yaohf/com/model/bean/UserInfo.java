package yaohf.com.model.bean;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;

/**
 * User 登录用户，保存基础数据
 */
@Table("UserInfo")
public class UserInfo implements Serializable {


    private static final long serialVersionUID = -7269279534300498717L;

    //自定义主键
    @PrimaryKey(AssignType.BY_MYSELF)
    private String userName;
    private int sex;

    private String passMd5;
    private String photo;
    private String realName;

    public UserInfo(){}
    public UserInfo( String userName,String passMd5, String photo, String realName, int sex) {
        this.userName = userName;
        this.passMd5 = passMd5;
        this.photo = photo;
        this.realName = realName;
        this.sex = sex;
    }


    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPassMd5() {
        return passMd5;

    }

    public void setPassMd5(String passMd5) {
        this.passMd5 = passMd5;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "passMd5='" + passMd5 + '\'' +
                ", photo='" + photo + '\'' +
                ", realName='" + realName + '\'' +
                ", userName='" + userName + '\'' +
                ", sex=" + sex +
                '}';
    }
}
