
    alter table book 
        drop 
        foreign key FK2E3AE9D2555F24;

    alter table book 
        drop 
        foreign key FK2E3AE9467D90C4;

    alter table book_favorited 
        drop 
        foreign key FKB02CD0727B3EB024;

    alter table book_favorited 
        drop 
        foreign key FKB02CD0726EA76CE4;

    alter table book_favorited 
        drop 
        foreign key FKB02CD072D2555F24;

    alter table book_reading 
        drop 
        foreign key FK3292B7367B3EB024;

    alter table book_reading 
        drop 
        foreign key FK3292B7366EA76CE4;

    alter table book_reading 
        drop 
        foreign key FK3292B736D2555F24;

    alter table book_suggestions 
        drop 
        foreign key FK6FE9FA996EA76CE4;

    alter table book_wishlisted 
        drop 
        foreign key FK7C44C43A7B3EB024;

    alter table book_wishlisted 
        drop 
        foreign key FK7C44C43A6EA76CE4;

    alter table book_wishlisted 
        drop 
        foreign key FK7C44C43AD2555F24;

    alter table credential 
        drop 
        foreign key FKD743E8576EA76CE4;

    alter table credential_role 
        drop 
        foreign key FKB8D2EF5EC97CA904;

    alter table credential_role 
        drop 
        foreign key FKB8D2EF5E3F39A1E4;

    alter table followers 
        drop 
        foreign key FK2DA6E415D6EA73B1;

    alter table followers 
        drop 
        foreign key FK2DA6E4156EA76CE4;

    alter table posdta 
        drop 
        foreign key FKC570C2FD7B3EB024;

    alter table posdta 
        drop 
        foreign key FKC570C2FD6EA76CE4;

    alter table posdta 
        drop 
        foreign key FKC570C2FDD2555F24;

    alter table work 
        drop 
        foreign key FK37C711843DFA64;

    alter table work 
        drop 
        foreign key FK37C711467D90C4;

    alter table work_rating 
        drop 
        foreign key FKFD44028BD2555F24;

    drop table if exists author;

    drop table if exists book;

    drop table if exists book_favorited;

    drop table if exists book_reading;

    drop table if exists book_suggestions;

    drop table if exists book_wishlisted;

    drop table if exists credential;

    drop table if exists credential_role;

    drop table if exists followers;

    drop table if exists language;

    drop table if exists posdta;

    drop table if exists role;

    drop table if exists user;

    drop table if exists work;

    drop table if exists work_rating;

    create table author (
        id bigint not null auto_increment,
        icon varchar(255),
        name varchar(255),
        primary key (id)
    ) ENGINE=InnoDB;

    create table book (
        id bigint not null auto_increment,
        icon varchar(255),
        title varchar(255),
        language_id bigint,
        work_id bigint,
        primary key (id)
    ) ENGINE=InnoDB;

    create table book_favorited (
        id bigint not null auto_increment,
        creationDate datetime,
        book_id bigint,
        user_id bigint,
        work_id bigint,
        primary key (id),
        unique (user_id, book_id)
    ) ENGINE=InnoDB;

    create table book_reading (
        id bigint not null auto_increment,
        creationDate datetime,
        book_id bigint,
        user_id bigint,
        work_id bigint,
        primary key (id),
        unique (user_id, book_id)
    ) ENGINE=InnoDB;

    create table book_suggestions (
        id bigint not null,
        map longtext,
        user_id bigint,
        primary key (id),
        unique (user_id)
    ) ENGINE=InnoDB;

    create table book_wishlisted (
        id bigint not null auto_increment,
        creationDate datetime,
        book_id bigint,
        user_id bigint,
        work_id bigint,
        primary key (id),
        unique (user_id, book_id)
    ) ENGINE=InnoDB;

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

    create table followers (
        user_id bigint not null,
        follower_id bigint not null,
        primary key (user_id, follower_id)
    ) ENGINE=InnoDB;

    create table language (
        id bigint not null auto_increment,
        code varchar(255),
        primary key (id)
    ) ENGINE=InnoDB;

    create table posdta (
        id bigint not null auto_increment,
        finish datetime,
        posdta varchar(255),
        rating integer not null,
        start datetime,
        book_id bigint,
        user_id bigint,
        work_id bigint,
        primary key (id)
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

    create table work (
        id bigint not null auto_increment,
        icon varchar(255),
        rating double precision,
        title varchar(255),
        author_id bigint,
        language_id bigint,
        primary key (id)
    ) ENGINE=InnoDB;

    create table work_rating (
        id bigint not null,
        countR1 integer not null,
        countR2 integer not null,
        countR3 integer not null,
        countR4 integer not null,
        countR5 integer not null,
        work_id bigint,
        primary key (id),
        unique (work_id)
    ) ENGINE=InnoDB;

    alter table book 
        add index FK2E3AE9D2555F24 (work_id), 
        add constraint FK2E3AE9D2555F24 
        foreign key (work_id) 
        references work (id);

    alter table book 
        add index FK2E3AE9467D90C4 (language_id), 
        add constraint FK2E3AE9467D90C4 
        foreign key (language_id) 
        references language (id);

    alter table book_favorited 
        add index FKB02CD0727B3EB024 (book_id), 
        add constraint FKB02CD0727B3EB024 
        foreign key (book_id) 
        references book (id);

    alter table book_favorited 
        add index FKB02CD0726EA76CE4 (user_id), 
        add constraint FKB02CD0726EA76CE4 
        foreign key (user_id) 
        references user (id);

    alter table book_favorited 
        add index FKB02CD072D2555F24 (work_id), 
        add constraint FKB02CD072D2555F24 
        foreign key (work_id) 
        references work (id);

    alter table book_reading 
        add index FK3292B7367B3EB024 (book_id), 
        add constraint FK3292B7367B3EB024 
        foreign key (book_id) 
        references book (id);

    alter table book_reading 
        add index FK3292B7366EA76CE4 (user_id), 
        add constraint FK3292B7366EA76CE4 
        foreign key (user_id) 
        references user (id);

    alter table book_reading 
        add index FK3292B736D2555F24 (work_id), 
        add constraint FK3292B736D2555F24 
        foreign key (work_id) 
        references work (id);

    alter table book_suggestions 
        add index FK6FE9FA996EA76CE4 (user_id), 
        add constraint FK6FE9FA996EA76CE4 
        foreign key (user_id) 
        references user (id);

    alter table book_wishlisted 
        add index FK7C44C43A7B3EB024 (book_id), 
        add constraint FK7C44C43A7B3EB024 
        foreign key (book_id) 
        references book (id);

    alter table book_wishlisted 
        add index FK7C44C43A6EA76CE4 (user_id), 
        add constraint FK7C44C43A6EA76CE4 
        foreign key (user_id) 
        references user (id);

    alter table book_wishlisted 
        add index FK7C44C43AD2555F24 (work_id), 
        add constraint FK7C44C43AD2555F24 
        foreign key (work_id) 
        references work (id);

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

    alter table followers 
        add index FK2DA6E415D6EA73B1 (follower_id), 
        add constraint FK2DA6E415D6EA73B1 
        foreign key (follower_id) 
        references user (id);

    alter table followers 
        add index FK2DA6E4156EA76CE4 (user_id), 
        add constraint FK2DA6E4156EA76CE4 
        foreign key (user_id) 
        references user (id);

    alter table posdta 
        add index FKC570C2FD7B3EB024 (book_id), 
        add constraint FKC570C2FD7B3EB024 
        foreign key (book_id) 
        references book (id);

    alter table posdta 
        add index FKC570C2FD6EA76CE4 (user_id), 
        add constraint FKC570C2FD6EA76CE4 
        foreign key (user_id) 
        references user (id);

    alter table posdta 
        add index FKC570C2FDD2555F24 (work_id), 
        add constraint FKC570C2FDD2555F24 
        foreign key (work_id) 
        references work (id);

    alter table work 
        add index FK37C711843DFA64 (author_id), 
        add constraint FK37C711843DFA64 
        foreign key (author_id) 
        references author (id);

    alter table work 
        add index FK37C711467D90C4 (language_id), 
        add constraint FK37C711467D90C4 
        foreign key (language_id) 
        references language (id);

    alter table work_rating 
        add index FKFD44028BD2555F24 (work_id), 
        add constraint FKFD44028BD2555F24 
        foreign key (work_id) 
        references work (id);
