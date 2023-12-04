--drop table result;

create table if not exists result
(
    result_id      bigserial,
    document_id    bigint    not null,
    created_origin timestamp not null,
    created        timestamp default now() not null,
    constraint pk_result primary key (result_id)
);
create index if not exists ind_result_document_id on result(document_id);