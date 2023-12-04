--drop table result;

create table if not exists result
(
    document_id bigint,
    created     timestamp default now(),
    constraint pk_document primary key (document_id)
);
-- ALTER TABLE result REPLICA IDENTITY FULL;