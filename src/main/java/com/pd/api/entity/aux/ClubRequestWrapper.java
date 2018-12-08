package com.pd.api.entity.aux;

import com.pd.api.entity.Club;
import com.pd.api.entity.User;

/**
 * Created by tin on 20/09/18.
 */
public class ClubRequestWrapper {

    public String name;

    public String displayname;

    public String type;

    public String description;

    public ClubRequestWrapper() {}
    public ClubRequestWrapper(String name, String displayname, String type, String description) {
        this.name = name;
        this.displayname = displayname;
        this.type = type;
        this.description = description;
    }

    // TODO: Fix to read each value and create the group
    public Club getClub(User user) {
        // Check and validate each thing
        return new Club(user, name);
    }
}
