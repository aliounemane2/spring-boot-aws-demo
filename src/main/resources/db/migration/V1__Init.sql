create sequence profile_id_seq start with 1 increment by 1;
create sequence job_id_seq start with 1 increment by 1;

create table profiles
(
    id         bigint    DEFAULT nextval('profile_id_seq') not null,
    name       varchar(255)                                not null,
    email      varchar(255)                                not null,
    blog       varchar(255) null,
    github     varchar(255) null,
    twitter    varchar(255) null,
    youtube    varchar(255) null,
    image_name varchar(255) null,
    created_at timestamp default now(),
    updated_at timestamp,
    primary key (id),
    CONSTRAINT user_email_unique UNIQUE (email)
);

create table job_postings
(
    id          bigint    DEFAULT nextval('job_id_seq') not null,
    company     varchar(255)                            not null,
    title       varchar(255)                            not null,
    description varchar(255)                            not null,
    created_at  timestamp default now(),
    updated_at  timestamp,
    primary key (id)
);
