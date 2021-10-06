-- noinspection SqlNoDataSourceInspectionForFile

CREATE TABLE IF NOT EXISTS room (
  id                     VARCHAR(60)  DEFAULT RANDOM_UUID() PRIMARY KEY,
  name                   VARCHAR      NOT NULL,
  status                 VARCHAR      NOT NULL
);

CREATE TABLE IF NOT EXISTS message (
  id                     VARCHAR(60)  DEFAULT RANDOM_UUID() PRIMARY KEY,
  room_id                VARCHAR(60)  NOT NULL,
  name                   VARCHAR      NOT NULL,
  text                   VARCHAR      NOT NULL,
  date                   TIMESTAMP    NOT NULL,

  CONSTRAINT fk_room_id FOREIGN KEY (room_id) REFERENCES room(id)
);
