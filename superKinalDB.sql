-- drop database if exists superKinalDB;

create database if not exists superKinalDB;

use superKinalDB;

create table Clientes(
	clienteId int not null auto_increment,
    nombre varchar(30) not null,
    apellido varchar(30) not null,
    telefono varchar(15),
    direccion varchar(150) not null,
    primary key PK_clienteId(clienteId)
);

insert into Clientes(nombre, apellido, telefono, direccion) values
	('Juan Pablo', 'Toriyama', '6677-9988', 'Jutiapa'),
    ('Vladimir', 'Gonzales Arriaga', '9955-1010', 'Villanueva');
    
select * from Clientes;

DELIMITER $$
    CREATE PROCEDURE sp_AgregarClientes(in nom varchar(30), in ape varchar(30), in tel varchar(25), in dir varchar(150))
        BEGIN
            INSERT INTO Clientes (nombre, apellido, telefono, direccion)
                VALUES (nom, ape, tel, dir);
        END$$
DELIMITER ;
 
DELIMITER $$
    CREATE PROCEDURE sp_listarClientes()
        BEGIN
			SELECT
            Clientes.clienteId,
            Clientes.nombre,
            Clientes.apellido,
            Clientes.telefono,
            Clientes.direccion
                    FROM Clientes;
        END$$
DELIMITER ;

DELIMITER $$
    CREATE PROCEDURE sp_eliminarClientes(in cliId int)
        BEGIN
            DELETE
                FROM Clientes
                WHERE clienteId = cliId;
        END $$
DELIMITER ;

DELIMITER $$
    CREATE PROCEDURE sp_buscarClientes(in cliId int)
        BEGIN
            SELECT
                Clientes.nombre,
				Clientes.apellido,
				Clientes.telefono,
				Clientes.direccion
                    FROM Clientes
                    WHERE clienteId = cliId;
        END $$
DELIMITER ;

DELIMITER $$
    CREATE PROCEDURE sp_editarClientes(in cliId int, in nom varchar(30), in ape varchar(30), in tel varchar(25), in dir varchar(150))
        BEGIN
            UPDATE Clientes
                SET
                    nombre = nom,
                    apellido = ape,
                    telefono = tel,
                    direccion = dir,
                    distribuidorId = disId
                    WHERE clienteId = cliId;
        END$$
DELIMITER ;