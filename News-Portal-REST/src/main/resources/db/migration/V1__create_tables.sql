create table comment_item (
    id integer not null auto_increment,
    comment_date datetime(6) not null,
    message varchar(2000) not null,
    author_id integer,
    publication_id integer,
    primary key (id)) engine=InnoDB;

create table news_item (
    id integer not null auto_increment,
    content varchar(5000) not null,
    publication_data datetime(6) not null,
    title varchar(50) not null,
    author_id integer,
    primary key (id)) engine=InnoDB;

create table user (
    id integer not null auto_increment,
    account_non_locked bit not null,
    active bit not null,
    password varchar(100) not null,
    username varchar(50) not null,
    primary key (id)) engine=InnoDB;

create table user_role (
    user_id integer not null,
    roles varchar(255)) engine=InnoDB;

alter table comment_item add constraint FK_author_comment foreign key (author_id) references user (id);
alter table comment_item add constraint FK_news_comment foreign key (publication_id) references news_item (id);
alter table news_item add constraint FK_author_news foreign key (author_id) references user (id);
alter table user_role add constraint FK_user_role foreign key (user_id) references user (id);