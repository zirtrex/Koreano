--BASE DE DATOS EL KOREANO E.I.R.L.
--Creación de la tabla DetallePersona
CREATE TABLE DetallePersona
  (
    codDetallePersona CHAR(9),
    nombres           VARCHAR2(150),
    apellidos         VARCHAR2(50),
    direccion         VARCHAR2(100),
    sexo              CHAR(1),
    telefono          CHAR(9),
    email             VARCHAR2(200),
    fechaIngreso      DATE,
    experiencia       VARCHAR2(10),
    estadoDetPer      CHAR(1),

    CONSTRAINT pk_codDetPer PRIMARY KEY (codDetallePersona),
    CONSTRAINT chk_sexo CHECK(sexo IN('M','F')),
    CONSTRAINT unq_tel UNIQUE(telefono),
    CONSTRAINT unq_email UNIQUE(email),
    CONSTRAINT chk_estPer CHECK(estadoDetPer IN('0','1'))
  );
INSERT
INTO DetallePersona
  (
    codDetallePersona,
    nombres,
    estadoDetPer
  )
  VALUES
  (
    'DP0000001',
    'admin',
    '1'
  );
--Creación de la tabla Usuario
CREATE TABLE Usuario
  (
    codUsuario        CHAR(9),
    codDetallePersona CHAR(9),
    nombreUsuario VARCHAR2(16),
    claveUsuario  VARCHAR2(32),
    rol           VARCHAR2(6),
    estadoUsuario CHAR(1),

    CONSTRAINT pk_codUsu PRIMARY KEY(codUsuario),
    CONSTRAINT fk_codDetPer FOREIGN KEY(codDetallePersona) REFERENCES DetallePersona(codDetallePersona),
    CONSTRAINT unq_nomUsu UNIQUE(nombreUsuario),
    CONSTRAINT chk_rol CHECK(rol IN('admin','lector')),
    CONSTRAINT chk_estUsu CHECK(estadoUsuario IN('0','1'))
  );
INSERT
INTO Usuario
  (
    codUsuario,
    codDetallePersona,
    nombreUsuario,
    claveUsuario,
    rol,
    estadoUsuario
  )
  VALUES
  (
    'U00000001',
    'DP0000001',
    'admin',
    'admin',
    'admin',
    '1'
  );
--Creacion de la tabla Operador
CREATE TABLE Operador
  (
    codOperador       CHAR(9),
    codDetallePersona CHAR(9),
	numeroLicencia 		CHAR(9),
	certificado 	VARCHAR(100),
    estadoOperador    CHAR(1),

    CONSTRAINT pk_codOperador PRIMARY KEY(codOperador),
    CONSTRAINT fk_codDetPerO FOREIGN KEY(codDetallePersona) REFERENCES DetallePersona(codDetallePersona),
    CONSTRAINT chk_estadoOpe CHECK(estadoOperador IN('0','1'))
  );
--Creacion de la tabla DetalleEmpresa
CREATE TABLE DetalleEmpresa
  (
    codDetalleEmpresa CHAR(9),
    razonSocial       VARCHAR2(500),
    ruc               CHAR(11),
    telefono          CHAR(9),
    email             CHAR(100),
    direccion         VARCHAR2(200),
    estadoDetEmp      CHAR(1),

    CONSTRAINT pk_codDetEmp PRIMARY KEY(codDetalleEmpresa),
    CONSTRAINT unq_razSoc UNIQUE(razonSocial),
    CONSTRAINT unq_ruc UNIQUE(ruc),
    CONSTRAINT unq_telE UNIQUE(telefono),
    CONSTRAINT unq_emailE UNIQUE(email),
    CONSTRAINT chk_estDetEmp CHECK(estadoDetEmp IN('0','1'))
  );
--Creación de la tabla Contratista
CREATE TABLE Contratista
  (
    codContratista    CHAR(9),
    codDetalleEmpresa CHAR(9),
	nombreEncargado 	VARCHAR(200),
    tipoEmpresa       CHAR(10) DEFAULT 'Jurídica',
    estadoContratista CHAR(1),

    CONSTRAINT pk_codCon PRIMARY KEY(codContratista),
    CONSTRAINT fk_codDetEmpC FOREIGN KEY(codDetalleEmpresa) REFERENCES DetalleEmpresa(codDetalleEmpresa),
    CONSTRAINT chk_te CHECK(topoEmpresa IN('juridica','natural')),
    CONSTRAINT chk_estCon CHECK(estadoContratista IN('0','1'))
  );
--Creación de la tabla Proveedor
CREATE TABLE Proveedor
  (
    codProveedor      CHAR(9),
    codDetalleEmpresa CHAR(9),
    estadoProveedor   CHAR(1),

    CONSTRAINT pk_codPro PRIMARY KEY(codProveedor),
    CONSTRAINT fk_codDetEmpP FOREIGN KEY(codDetalleEmpresa) REFERENCES DetalleEmpresa(codDetalleEmpresa),
    CONSTRAINT chk_estPro CHECK(estadoProveedor IN('0','1'))
  );
--Creación de la Tabla Alquiler
CREATE TABLE Alquiler
  (
    codAlquiler     CHAR(9),
    codContratista  CHAR(9),
    codUsuario      CHAR(9),
    fecha           DATE,
    tipoDocumento   CHAR(7),
    numeroSerie     NUMERIC(6,0),
    numeroDocumento NUMERIC(6,0),
    subTotal        NUMERIC(13,2),
    igv             NUMERIC(13,2),
    total           NUMERIC(13,2),
    estadoAlquiler  CHAR(1),

    CONSTRAINT pk_codAlq PRIMARY KEY(codAlquiler),
    CONSTRAINT fk_codCon FOREIGN KEY(codContratista) REFERENCES Contratista(codContratista),
    CONSTRAINT fk_codUsu FOREIGN KEY(codUsuario) REFERENCES Usuario(codUsuario),
    CONSTRAINT chk_tipoDoc CHECK(tipoDocumento IN('Boleta','Factura')) ,
    CONSTRAINT chk_estAlq CHECK(estadoAlquiler IN('0','1'))
  );
--Creación de la tabla Equipo
CREATE TABLE Equipo
  (
    codEquipo CHAR(9),
    nombre    VARCHAR2(50),
    marca     VARCHAR(20),
    modelo    VARCHAR2(100),
    placa     CHAR(6),
    categoria VARCHAR2(20),/*Maquinarias - Vehículos*/    
    horometro       NUMERIC(13,2),
    kilometraje     NUMERIC(13,0),
    anioFabricacion CHAR(4),
    operativo CHAR(1),
    observaciones VARCHAR2(500),
    descripcion VARCHAR2(1000),
    estadoEquipo    CHAR(1),

    CONSTRAINT pk_codEqui PRIMARY KEY(codEquipo),
    CONSTRAINT unq_nom UNIQUE(nombre),
	CONSTRAINT unq_pl UNIQUE(placa),
	CONSTRAINT unq_ho UNIQUE(horometro),
	CONSTRAINT unq_kl UNIQUE(kilometraje),
    CONSTRAINT chk_cat CHECK(categoria IN('Maquinarias','Vehículos')) ,
    CONSTRAINT chk_estEqui CHECK(estadoEquipo IN('0','1'))
  );
--Creación de la tabla DetalleAlquiler
CREATE TABLE DetalleAlquiler
  (
    codDetalleAlquiler CHAR(9),
	codAlquiler        CHAR(9),
    codEquipo          CHAR(9),    
    precio             NUMERIC(13,2),
    horas              NUMERIC(13,2),
    subTotal           NUMERIC(13,2),
    estadoDetAlq       CHAR(1),

    CONSTRAINT pk_codDetAlq PRIMARY KEY(codDetalleAlquiler),
    CONSTRAINT fk_codEquipo FOREIGN KEY(codEquipo) REFERENCES Equipo(codEquipo),
    CONSTRAINT fk_codAlquiler FOREIGN KEY(codAlquiler) REFERENCES Alquiler(codAlquiler),
    CONSTRAINT chk_estDetAlq CHECK(estadoDetAlq IN('0','1'))
  );
--Creación de la tabla Repuesto
CREATE TABLE Repuesto
  (
    codRepuesto    CHAR(9),
	codProveedor CHAR(9),
	codEquipo CHAR(9),
	codigo 		VARCHAR(100),
    nombre         VARCHAR2(200),
    marca          VARCHAR2(100),
	equivalente VARCHAR(100),
    stock          NUMERIC(5,0),
    estadoRepuesto CHAR(100),

    CONSTRAINT pk_codRep PRIMARY KEY(codRepuesto),
	CONSTRAINT fk_codEquipoR FOREIGN KEY(codEquipo) REFERENCES Equipo(codEquipo),
	CONSTRAINT fk_codProveedorR FOREIGN KEY(codProveedor) REFERENCES Proveedor(codProveedor),
    CONSTRAINT unq_nomRep UNIQUE(nombre),
    CONSTRAINT chk_estRep CHECK(estadoRepuesto IN('0','1'))
  );
--Creación de la tabla Compra
CREATE TABLE Compra
  (
    codCompra    CHAR(9),
    codUsuario   CHAR(9),
    codProveedor CHAR(9),
    fecha        DATE,
    subTotal     NUMERIC(13,2),
    igv          NUMERIC(13,2),
    total        NUMERIC(13,2),
    estadoCompra CHAR(1),

    CONSTRAINT pk_codCom PRIMARY KEY(codCompra),
    CONSTRAINT fk_codUsuCom FOREIGN KEY(codUsuario) REFERENCES Usuario(codUsuario),
    CONSTRAINT fk_codPro FOREIGN KEY(codProveedor) REFERENCES Proveedor(codProveedor),
    CONSTRAINT chk_estCom CHECK(estadoCompra IN('0','1'))
  );
--Creación de la tabla DetalleCompra
CREATE TABLE DetalleCompra
  (
    codDetalleCompra CHAR(9),
    codCompra        CHAR(9),
    codRepuesto      CHAR(9),
    cantidad         NUMERIC(5,0),
    precio           NUMERIC(13,2),
    subTotal         NUMERIC(13,2),
    estadoDetCom     CHAR(1),

    CONSTRAINT pk_codDetCom PRIMARY KEY(codDetalleCompra),
    CONSTRAINT fk_codCom FOREIGN KEY(codCompra) REFERENCES Compra(codCompra),
    CONSTRAINT fk_codRep FOREIGN KEY(codRepuesto) REFERENCES Repuesto(codRepuesto),
    CONSTRAINT chk_estDetCom CHECK(estadoDetCom IN('0','1'))
  );
--Creación de la tabla Mantenimiento
CREATE TABLE Mantenimiento
  (
    codMantenimiento    CHAR(9),
    codOperador         CHAR(9),
    codEquipo           CHAR(9),
    fecha               DATE,
    horometro           NUMERIC(13,2),
    kilometraje         NUMERIC(13,0),
    estadoMantenimiento CHAR(1),

    CONSTRAINT pk_codMant PRIMARY KEY(codMantenimiento),
    CONSTRAINT fk_codOpe FOREIGN KEY(codOperador) REFERENCES Operador(codOperador),
    CONSTRAINT fk_codEqui FOREIGN KEY(codEquipo) REFERENCES Equipo(codEquipo),
	CONSTRAINT unq_horo UNIQUE(horometro),
	CONSTRAINT unq_kilo UNIQUE(kilometraje),
    CONSTRAINT chk_estMan CHECK(estadoMantenimiento IN('0','1'))
  );
--Creación de la table DetalleMantenimiento
CREATE TABLE DetalleMantenimiento
  (
    codDetalleMantenimiento CHAR(9),
    codMantenimiento        CHAR(9),
    codRepuesto             CHAR(9),
    cantidad                NUMERIC(5,0),
    estadoDetMan            CHAR(1),

    CONSTRAINT pk_codDetMan PRIMARY KEY(codDetalleMantenimiento),
    CONSTRAINT fk_codManM FOREIGN KEY(codMantenimiento) REFERENCES Mantenimiento(codMantenimiento),
    CONSTRAINT fk_codRepM FOREIGN KEY(codRepuesto) REFERENCES Repuesto(codRepuesto),
    CONSTRAINT chk_estDetMan CHECK(estadoDetMan IN('0','1'))
  );
--Creación de la tabla Reporte
CREATE TABLE Reporte
  (
    codReporte     CHAR(9),
    codUsuario     CHAR(9),
    codAlquiler    CHAR(9),
    codContratista CHAR(9),
    codEquipo      CHAR(9),
    codOperador    CHAR(9),
    estado         CHAR(10),
    numeroParte    CHAR(8),
    fecha DATE,
    viajes 		NUMERIC(3,0),
	costoViaje 	NUMERIC(13,2),
    destino             VARCHAR2(200),
    origen              VARCHAR2(200),
    lugar               VARCHAR2(100),
    combustible         NUMERIC(13,2),
    numeroVale          CHAR(8),
    kilometrajeAnterior NUMERIC(13,0),
    kilometrajeFinal NUMERIC(13,0),
    horometroAnterior NUMERIC(13,2),
    horometroFinal    NUMERIC(13,2),
    horas             NUMERIC(13,0),
    minutos           NUMERIC(13,0),
	horasTotales NUMERIC(13,2),
	costoHoras Numeric(13,2),
	totalPagar NUMERIC(13,2),
    observaciones VARCHAR2(1000),
    estadoReporte CHAR(1),

    CONSTRAINT pk_codReporte PRIMARY KEY (codReporte),
    CONSTRAINT fk_codUsuR FOREIGN KEY (codUsuario) REFERENCES Usuario(codUsuario),
    CONSTRAINT fk_codAlqR FOREIGN KEY (codAlquiler) REFERENCES Alquiler(codAlquiler),
    CONSTRAINT fk_codConR FOREIGN KEY (codContratista) REFERENCES Contratista(codContratista),
    CONSTRAINT fk_codEquR FOREIGN KEY (codEquipo) REFERENCES Equipo(codEquipo),
    CONSTRAINT fk_codOpeR FOREIGN KEY (codOperador) REFERENCES Operador(codOperador),
	CONSTRAINT unq_cv UNIQUE(costoViaje),
    CONSTRAINT chk_estado CHECK(estado IN('Pendiente','Cancelado')),
    CONSTRAINT chk_estRepor CHECK(estadoReporte IN('0','1'))
  );
