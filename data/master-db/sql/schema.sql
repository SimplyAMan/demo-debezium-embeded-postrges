--drop table result;

create table if not exists document
(
    document_id bigint,
    created     timestamp default now(),
    constraint pk_document primary key (document_id)
);
-- ALTER TABLE document REPLICA IDENTITY FULL;