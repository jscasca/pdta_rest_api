
    alter table credential 
        drop 
        foreign key FKD743E8576EA76CE4;

    alter table credential_role 
        drop 
        foreign key FKB8D2EF5EC97CA904;

    alter table credential_role 
        drop 
        foreign key FKB8D2EF5E3F39A1E4;

    drop table if exists credential;

    drop table if exists credential_role;

    drop table if exists role;

    drop table if exists user;

    create table credential (
        id bigint not null auto_increment,
        password varchar(255),
        username varchar(255),
        user_id bigint,
        primary key (id),
        unique (username)
    ) ENGINE=InnoDB;

    create table credential_role (
        credential_id bigint not null,
        role_id bigint not null,
        primary key (credential_id, role_id)
    ) ENGINE=InnoDB;

    create table role (
        id bigint not null auto_increment,
        authority varchar(255),
        name varchar(255),
        primary key (id)
    ) ENGINE=InnoDB;

    create table user (
        id bigint not null auto_increment,
        displayname varchar(255),
        icon varchar(255),
        username varchar(255),
        primary key (id),
        unique (username)
    ) ENGINE=InnoDB;

    alter table credential 
        add index FKD743E8576EA76CE4 (user_id), 
        add constraint FKD743E8576EA76CE4 
        foreign key (user_id) 
        references user (id);

    alter table credential_role 
        add index FKB8D2EF5EC97CA904 (role_id), 
        add constraint FKB8D2EF5EC97CA904 
        foreign key (role_id) 
        references role (id);

    alter table credential_role 
        add index FKB8D2EF5E3F39A1E4 (credential_id), 
        add constraint FKB8D2EF5E3F39A1E4 
        foreign key (credential_id) 
        references credential (id);
