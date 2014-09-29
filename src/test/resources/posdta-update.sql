
    create table followers (
        user_id bigint not null,
        follower_id bigint not null,
        primary key (user_id, follower_id)
    ) ENGINE=InnoDB;

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
