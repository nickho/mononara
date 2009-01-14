/*****************************************
 *                                       *
 *  Mononara : The Kanji card reviewer   *
 *                                       *
 *   Distributable under LGPL license.   *
 *   See terms of license at gnu.org.    *
 *                                       *
 *****************************************/
package com.kanjiportal.portal;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Pattern;
import org.hibernate.validator.Range;
import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Name;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * User
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement User
 */

@Entity
@Scope(SESSION)
@Table(name = "user")
public class User implements Serializable {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private int level;

    public User(String password, String username, int level) {
        this.level = level;
        this.password = password;
        this.username = username;
    }

    public User() {
    }

    @NotNull
    @Length(max = 100)
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @NotNull
    @Length(max = 100)
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    @NotNull
    @Length(min = 5, max = 15)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Id
    @Length(min = 4, max = 15)
    @Pattern(regex = "^\\w*$", message = "not a valid username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull
    @Range(min = 1, max = 10)
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "User(" + username + ")";
    }


}
