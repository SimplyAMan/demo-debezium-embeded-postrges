drop table if exists result;

create table if not exists document
(
    document_id bigint,
    created     timestamp default now(),
    constraint pk_document primary key (document_id)
);
-- ALTER TABLE document REPLICA IDENTITY FULL;
-- drop table if exists outbox;
create table if not exists outbox
(
    outbox_id   bigserial,
    payload     json,
    routing_key text,
    status      text,
    created     timestamp default now(),
    sent        timestamp,
    constraint outbox_pk primary key (outbox_id)
);
comment on table outbox is 'Події для передачі в message broker';
comment on column outbox.outbox_id is 'Код події';
comment on column outbox.payload is 'Тіло повідомлення';
comment on column outbox.routing_key is 'Routing key в message broker';
comment on column outbox.status is 'Статус події ("created", "sent")';
comment on column outbox.created is 'Час створення події';
comment on column outbox.sent is 'Час відправки події в message broker';