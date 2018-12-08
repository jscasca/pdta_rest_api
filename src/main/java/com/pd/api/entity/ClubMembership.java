package com.pd.api.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by tin on 19/09/18.
 */
@Entity
@Table(name="club_membership")
public class ClubMembership implements Serializable {

    public enum ClubPermission {
        ADMIN,
        CAN_INVITE
    }

    @Id
    private ClubMembershipKeys id;

    public static HashSet<ClubPermission> getDefaultPermissions() {
        return new HashSet<>();
    }
    public static HashSet<ClubPermission> getAdminPermissions() {
        HashSet permissions = new HashSet();
        permissions.add(ClubPermission.ADMIN);
        permissions.add(ClubPermission.CAN_INVITE);
        return permissions;
    }

    @Type(type="timestamp")
    protected Date start = new Date();

    private String permissions;

    @Transient
    private HashSet<ClubPermission> permissionSet;

    public ClubMembership() {}
    public ClubMembership(User u, Club g) { this(u, g, getDefaultPermissions());}
    public ClubMembership(User u, Club g, HashSet<ClubPermission> permissionSet) {
        this.id = new ClubMembershipKeys(u, g);
        this.permissionSet = permissionSet;
        this.permissions = mapPermissionsToString(permissionSet);
    }

    public ClubMembershipKeys getId() {
        return id;
    }

    public void setId(ClubMembershipKeys id) {
        this.id = id;
    }

    public User getUser() {
        return this.id.getUser();
    }

    public void setUser(User u) {
        this.id.setUser(u);
    }

    public Club getClub() {
        return this.id.getClub();
    }

    public void setClub(Club g) {
        this.id.setClub(g);
    }

    public boolean isAdmin() {
        return checkPermission(ClubPermission.ADMIN);
    }

    public boolean canInvite() {
        return checkPermission(ClubPermission.ADMIN);
    }

    @PostLoad
    public void updatePermissionSet() {
        permissionSet = mapPermissionsFromString(permissions);
    }

    public boolean checkPermission(ClubPermission permission) {
        if(permissionSet == null) permissionSet = mapPermissionsFromString(permissions);
        return permissionSet.contains(permission);
    }

    public void addPermission(ClubPermission permission) {
        this.permissionSet.add(permission);
        updatePermissions();
    }

    public HashSet<ClubPermission> getPermissionSet() {
        return mapPermissionsFromString(permissions);
    }

    public void setPermissionSet(HashSet<ClubPermission> permissionSet) {
        this.permissionSet = permissionSet;
        updatePermissions();
    }

    private void updatePermissions() {
        permissions = mapPermissionsToString(this.permissionSet);
    }

    public static HashSet<ClubPermission> mapPermissionsFromString(String myPermissions) {
        ClubPermission[] permissions = ClubPermission.values();
        HashSet<ClubPermission> permissionSet = new HashSet<>();
        if(permissions.length != permissions.length) {
            // throw exception maybe? Something is wrong here
        }
        CharacterIterator it = new StringCharacterIterator(myPermissions);
        char ch = it.last();
        int permissionIndex = 0;
        while(ch != CharacterIterator.DONE && permissionIndex < permissions.length) {
            // Convert to boolean
            if(ch == '1') {
                permissionSet.add(permissions[permissionIndex]);
            }
            permissionIndex++;
            ch = it.previous();
        }
        return permissionSet;
    }

    public static String mapPermissionsToString(HashSet<ClubPermission> myPermissions) {
        ClubPermission[] permissions = ClubPermission.values();
        StringBuffer booleanString = new StringBuffer();
        for(ClubPermission permission : permissions) {
            booleanString.insert(0, myPermissions.contains(permission) ? "1" : "0");
        }
        return booleanString.toString();
    }
}
