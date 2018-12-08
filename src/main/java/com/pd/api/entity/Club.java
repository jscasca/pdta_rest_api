package com.pd.api.entity;

import com.pd.api.security.Validation;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tin on 18/09/18.
 */
@Entity
@Table(name="club", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
public class Club {

    public enum ClubType {
        PRIVATE,
        RESTRICTED,
        PUBLIC
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;

    private String name;

    private String displayname;

    private String icon;

    private String background;

    private String description;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user; //  Created by

    @Type(type="timestamp")
    protected Date created = new Date();

    @Enumerated(EnumType.STRING)
    private ClubType type;

    public Club() {}
    public Club(User user, String name) {
        this(user, name, name, "", "", ClubType.PUBLIC, "");
    }

    public Club(User user, String name, String display, String icon, String background , ClubType type, String description) {
        this.user = user;
        this.name = name;
        this.displayname = display;
        this.icon = icon;
        this.background = background;
        this.type = type;
        this.description = description;
    }

    public Long getId() { return id; }

    public String getClubName() {
        return name;
    }

    public String getDisplayName() {
        return displayname;
    }

    public void setDisplayname(String display) {
        this.displayname = display;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPrivate() {
        return type == ClubType.PRIVATE;
    }

    public boolean isRestricted() {
        return type == ClubType.RESTRICTED;
    }

    public boolean isPublic() {
        return type == ClubType.PUBLIC;
    }

    public ClubType getClubType() {
        return type;
    }

    public User getOwner() {
        return user;
    }

    @PrePersist
    public void onPrePersist() {
        if(!Validation.isValidEntityName(name)) {
            throw new IllegalArgumentException("The Club name is not valid");
        }
    }
}
