use animals;
create table animals
(
	Animal varchar(100) primary key,
    NombreCientifico varchar(100),
    Alimentacion varchar(40),
    Peso decimal(7,2), -- maximo estimado 9999999,99
    Altura varchar(20),
    habitad varchar(100),
    especie varchar(20),
    datoCurioso varchar(200)

);

alter table animals modify Peso varchar(50);
alter table animals modify habitad varchar(200);