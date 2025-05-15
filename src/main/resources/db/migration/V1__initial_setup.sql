---------------------------- create sequences ---------------------------
CREATE SEQUENCE if not exists book_seq START 50 MAXVALUE 9223372036854775807 NO CYCLE;
CREATE SEQUENCE if not exists borrower_seq START 50 MAXVALUE 9223372036854775807 NO CYCLE;
CREATE SEQUENCE if not exists ledger_seq START 50 MAXVALUE 9223372036854775807 NO CYCLE;


---------------------------- create tables ---------------------------
create table if not exists book (
    id bigint,
    isbn_no varchar(50),
    title varchar(255),
    author varchar(50),
    created_date timestamp(6),
    updated_date timestamp(6),
    constraint book_id_p primary key (id),
    constraint book_isbn_title_author_u unique (isbn_no, title, author)
);

create table if not exists borrower (
    id bigint,
    name varchar(50),
    email varchar(50),
    created_date timestamp(6),
    updated_date timestamp(6),
    constraint borrower_id_p primary key (id),
    constraint borrower_name_email_u unique (name, email)
);


create table if not exists ledger (
    id bigint,
    book_id bigint,
    borrower_id bigint,
    status varchar(50),
    created_date timestamp(6),
    updated_date timestamp(6),
    constraint ledger_id_p primary key (id)
);
