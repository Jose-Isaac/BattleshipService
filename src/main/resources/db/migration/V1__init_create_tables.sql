create table if not exists game_room {
    id                  uuid        not null primary key
}

create table if not exists board {
    id           uuid not null primary key,
    game_room_id uuid not null,
    plays        jsonb not null
    CONSTRAINT fk_game_room
        FOREIGN KEY (game_room_id) REFERENCES game_room(id)
}

create table if not exists player {
    id      uuid            not null primary key,
    name    varchar(20)     not null
}

create table player_game_room {
    id              uuid,
    game_room_id    uuid,
    player_id       uuid,
    PRIMARY KEY (game_room_id, player_id)
    CONSTRAINT fk_game_room
        FOREIGN KEY (game_room_id) REFERENCES game_room(id),
    CONSTRAINT fk_player_id
        FOREIGN KEY (player_id) REFERENCES player(id)
}