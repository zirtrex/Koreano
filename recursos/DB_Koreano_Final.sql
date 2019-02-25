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
INSERT INTO DetallePersona(codDetallePersona, nombres, estadoDetPer ) VALUES( 'DP0000001', 'admin', '1' );
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
    CONSTRAINT chk_rol CHECK(rol IN('admin','lector', 'secretaria')),
    CONSTRAINT chk_estUsu CHECK(estadoUsuario IN('0','1'))
  );
INSERT INTO Usuario(codUsuario, codDetallePersona, nombreUsuario,  claveUsuario,  rol, estadoUsuario ) VALUES (  'U00000001', 'DP0000001', 'admin', 'admin', 'admin', '1' );
INSERT INTO Usuario(codUsuario, codDetallePersona, nombreUsuario,  claveUsuario,  rol, estadoUsuario ) VALUES (  'U00000002', 'DP0000002', 'lector', 'lec', 'secretaria', '1' );
INSERT INTO Usuario(codUsuario, codDetallePersona, nombreUsuario,  claveUsuario,  rol, estadoUsuario ) VALUES (  'U00000003', 'DP0000003', 'lector', 'lec', 'asistente', '1' );
INSERT INTO Usuario(codUsuario, codDetallePersona, nombreUsuario,  claveUsuario,  rol, estadoUsuario ) VALUES (  'U00000004', 'DP0000004', 'lector', 'lec', 'mecanico', '1' );
INSERT INTO Usuario(codUsuario, codDetallePersona, nombreUsuario,  claveUsuario,  rol, estadoUsuario ) VALUES (  'U00000005', 'DP0000005', 'lector', 'admin', 'operador', '1' );
INSERT INTO Usuario(codUsuario, codDetallePersona, nombreUsuario,  claveUsuario,  rol, estadoUsuario ) VALUES (  'U00000006', 'DP0000006', 'lector', 'admin', 'contador', '1' );
INSERT INTO Usuario(codUsuario, codDetallePersona, nombreUsuario,  claveUsuario,  rol, estadoUsuario ) VALUES (  'U00000007', 'DP0000007', 'lector', 'admin', 'admin', '1' );
INSERT INTO Usuario(codUsuario, codDetallePersona, nombreUsuario,  claveUsuario,  rol, estadoUsuario ) VALUES (  'U00000008', 'DP0000008', 'lector', 'admin', 'admin', '1' );
INSERT INTO Usuario(codUsuario, codDetallePersona, nombreUsuario,  claveUsuario,  rol, estadoUsuario ) VALUES (  'U00000009', 'DP0000009', 'lector', 'admin', 'admin', '1' );
INSERT INTO Usuario(codUsuario, codDetallePersona, nombreUsuario,  claveUsuario,  rol, estadoUsuario ) VALUES (  'U00000010', 'DP0000010', 'lector', 'admin', 'admin', '1' );
INSERT INTO Usuario(codUsuario, codDetallePersona, nombreUsuario,  claveUsuario,  rol, estadoUsuario ) VALUES (  'U00000011', 'DP0000011', 'lector', 'admin', 'admin', '1' );
INSERT INTO Usuario(codUsuario, codDetallePersona, nombreUsuario,  claveUsuario,  rol, estadoUsuario ) VALUES (  'U00000012', 'DP0000012', 'lector', 'admin', 'admin', '1' );
INSERT INTO Usuario(codUsuario, codDetallePersona, nombreUsuario,  claveUsuario,  rol, estadoUsuario ) VALUES (  'U00000013', 'DP0000013', 'lector', 'admin', 'admin', '1' );
INSERT INTO Usuario(codUsuario, codDetallePersona, nombreUsuario,  claveUsuario,  rol, estadoUsuario ) VALUES (  'U00000014', 'DP0000014', 'lector', 'admin', 'admin', '1' );
INSERT INTO Usuario(codUsuario, codDetallePersona, nombreUsuario,  claveUsuario,  rol, estadoUsuario ) VALUES (  'U00000015', 'DP0000015', 'lector', 'admin', 'admin', '1' );
INSERT INTO Usuario(codUsuario, codDetallePersona, nombreUsuario,  claveUsuario,  rol, estadoUsuario ) VALUES (  'U00000016', 'DP0000016', 'lector', 'admin', 'admin', '1' );
INSERT INTO Usuario(codUsuario, codDetallePersona, nombreUsuario,  claveUsuario,  rol, estadoUsuario ) VALUES (  'U00000017', 'DP0000017', 'lector', 'admin', 'admin', '1' );
INSERT INTO Usuario(codUsuario, codDetallePersona, nombreUsuario,  claveUsuario,  rol, estadoUsuario ) VALUES (  'U00000018', 'DP0000018', 'lector', 'admin', 'admin', '1' );
INSERT INTO Usuario(codUsuario, codDetallePersona, nombreUsuario,  claveUsuario,  rol, estadoUsuario ) VALUES (  'U00000019', 'DP0000019', 'lector', 'admin', 'admin', '1' );
INSERT INTO Usuario(codUsuario, codDetallePersona, nombreUsuario,  claveUsuario,  rol, estadoUsuario ) VALUES (  'U00000020', 'DP0000020', 'lector', 'admin', 'admin', '1' );
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
  INSERT INTO Operador(codOperador,codDetallePersona, numeroLicencia , certificado ,estadoOperador ) VALUES (  'O00000001', 'DP0000001', 'F77064720', 'operador de retroexcavadora', '1' );
  INSERT INTO Operador(codOperador,codDetallePersona, numeroLicencia , certificado ,estadoOperador ) VALUES (  'O00000002', 'DP0000002', 'F45856816', 'operador de cargador frontal', '1' );
  INSERT INTO Operador(codOperador,codDetallePersona, numeroLicencia , certificado ,estadoOperador ) VALUES (  'O00000003', 'DP0000003', 'F73942684', 'operador de cargador frontal', '1' );
  INSERT INTO Operador(codOperador,codDetallePersona, numeroLicencia , certificado ,estadoOperador ) VALUES (  'O00000004', 'DP0000004', 'F43349074', 'operador  de rodillo', '1' );
  INSERT INTO Operador(codOperador,codDetallePersona, numeroLicencia , certificado ,estadoOperador ) VALUES (  'O00000005', 'DP0000005', 'F42260405', 'operador de motoniveladora', '1' );
  INSERT INTO Operador(codOperador,codDetallePersona, numeroLicencia , certificado ,estadoOperador ) VALUES (  'O00000006', 'DP0000006', 'F44973266', 'operador de excavadora', '1' );
  INSERT INTO Operador(codOperador,codDetallePersona, numeroLicencia , certificado ,estadoOperador ) VALUES (  'O00000007', 'DP0000007', 'F22281428', 'chofer de volquete', '1' );
  INSERT INTO Operador(codOperador,codDetallePersona, numeroLicencia , certificado ,estadoOperador ) VALUES (  'O00000008', 'DP0000008', 'F70678326', 'operador de tractor sobre oruga ', '1' );
  INSERT INTO Operador(codOperador,codDetallePersona, numeroLicencia , certificado ,estadoOperador ) VALUES (  'O00000009', 'DP0000009', 'F70683427', 'operador de tractor sobre oruga', '1' );
  INSERT INTO Operador(codOperador,codDetallePersona, numeroLicencia , certificado ,estadoOperador ) VALUES (  'O00000010', 'DP0000010', 'F70682786', 'operador de excavadora', '1' );
  INSERT INTO Operador(codOperador,codDetallePersona, numeroLicencia , certificado ,estadoOperador ) VALUES (  'O00000011', 'DP0000011', 'F45961399', 'chofer de camabaja', '1' );
  INSERT INTO Operador(codOperador,codDetallePersona, numeroLicencia , certificado ,estadoOperador ) VALUES (  'O00000012', 'DP0000012', 'F22201274', 'chofer de volquete', '1' );
  
  
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
 INSERT INTO DetalleEmpresa( codDetalleEmpresa,razonSocial,ruc,telefono,email,direccion,estadoDetEmp)VALUES('DE0000001','san fernando sa', '20100154308', '998388009', 'cgarcia@hotmail.com','av,republica de panama n°4295 surquillo lima','1' );
 INSERT INTO DetalleEmpresa( codDetalleEmpresa,razonSocial,ruc,telefono,email,direccion,estadoDetEmp)VALUES('DE0000002','pecuaria las yadiras', '20536079425', '951373961', 'crispy@hotmail.com','jr 28 de julio 269 of.404 barranco','1' );
 INSERT INTO DetalleEmpresa( codDetalleEmpresa,razonSocial,ruc,telefono,email,direccion,estadoDetEmp)VALUES('DE0000003','gt telecom', '20536659189', '956018277', 'gullergt@hotmail.com','av.tupac amaru #7025 s.m.p lima-lima','1' );
 INSERT INTO DetalleEmpresa( codDetalleEmpresa,razonSocial,ruc,telefono,email,direccion,estadoDetEmp)VALUES('DE0000004','consorcio gym conciviles ', '20546632424', '985642542', 'gym@hotmail.com','av.paseo de la republica 4675 surquillo lima','1' );
 INSERT INTO DetalleEmpresa( codDetalleEmpresa,razonSocial,ruc,telefono,email,direccion,estadoDetEmp)VALUES('DE0000005','agropecuaria chrispili sac', '20537172178', '958452165', 'achrispili@hotmail.com','a.p.u los claveles mz "a" lt 20- lurin','1' );
 INSERT INTO DetalleEmpresa( codDetalleEmpresa,razonSocial,ruc,telefono,email,direccion,estadoDetEmp)VALUES('DE0000006','inversiones maritima cpt peru sac', '20500066386', '985424578', 'mperu@hotmail@hotmail.com','av.nestor gambeta #5202','1' );
 INSERT INTO DetalleEmpresa( codDetalleEmpresa,razonSocial,ruc,telefono,email,direccion,estadoDetEmp)VALUES('DE0000007','desmotadora inca sac', '20364404141', '956384419', 'dinca@hotmail.com','via los libertadores km 1520-independencia ','1' ); 
 INSERT INTO DetalleEmpresa( codDetalleEmpresa,razonSocial,ruc,telefono,email,direccion,estadoDetEmp)VALUES('DE0000008','junta de usuario de agua de pisco', '20176316676', '985412457', 'julioju@hotmail.com','av.fermin tanguis s/8n km 3-pisco','1' );
 INSERT INTO DetalleEmpresa( codDetalleEmpresa,razonSocial,ruc,telefono,email,direccion,estadoDetEmp)VALUES('DE0000009','agropecuaria caira eirl', '20556318591', '985457892', 'accañete@hotmail.com','mz"b" lt 8 a.h los naranjos-lurin','1' );
 INSERT INTO DetalleEmpresa( codDetalleEmpresa,razonSocial,ruc,telefono,email,direccion,estadoDetEmp)VALUES('DE0000010','compañia agroindustrial santa fe de lanchas sac', '20506394369', '989240421', 'arturosfl@hotmail.com','av.ramos rivero n° 188 miraflores-lima','1' );
 INSERT INTO DetalleEmpresa( codDetalleEmpresa,razonSocial,ruc,telefono,email,direccion,estadoDetEmp)VALUES('DE0000011','hacienda murga sa', '20535071749', '985421458', 'haciendamurga@hotmail.com','av.san martin dpto 20-pisco','1' );
 INSERT INTO DetalleEmpresa( codDetalleEmpresa,razonSocial,ruc,telefono,email,direccion,estadoDetEmp)VALUES('DE0000012','consorcio agricola andrea 4 fruit company ', '20543039391', '985624574', 'companyfruit@hotmail.com','parque maldonado pueblo libre-lima','1' );
 INSERT INTO DetalleEmpresa( codDetalleEmpresa,razonSocial,ruc,telefono,email,direccion,estadoDetEmp)VALUES('DE0000013','gobierno regional de ica ', '20452393817', '985623145', 'gri@hotmail.com','calle cutervo n°920 ica-ica-ica','1' );
 INSERT INTO DetalleEmpresa( codDetalleEmpresa,razonSocial,ruc,telefono,email,direccion,estadoDetEmp)VALUES('DE0000014','agricola tambo colorado sac ', '20514660396', '985412587', 'atc@hotmail.com','calle alcanflores 1245 miraflores -lima','1' );
 INSERT INTO DetalleEmpresa( codDetalleEmpresa,razonSocial,ruc,telefono,email,direccion,estadoDetEmp)VALUES('DE0000015','agroindustrias 3f sac ', '20549253441', '985624578', 'asistente@axisplast.com','av.jose leal n°1168 lima-lima','1' );
 INSERT INTO DetalleEmpresa( codDetalleEmpresa,razonSocial,ruc,telefono,email,direccion,estadoDetEmp)VALUES('DE0000016','construcciones civilis sa sucursal peru ', '20513149157', '985624587', 'administradorcc@hotmail.com','paz soldan 170 oficina 304 san isidro lima','1' );
 INSERT INTO DetalleEmpresa( codDetalleEmpresa,razonSocial,ruc,telefono,email,direccion,estadoDetEmp)VALUES('DE0000017','consorcio jr ', '20568870248', '956326963', 'cjr@hotmail.com','pasaje diego ferrer n°164tambo huancayo-junin','1' );
 INSERT INTO DetalleEmpresa( codDetalleEmpresa,razonSocial,ruc,telefono,email,direccion,estadoDetEmp)VALUES('DE0000018','kingmax peru sac', '20516610019', '985624587', 'kim@hotmail.com','av.de las artes n°390 dpto 402 san borja','1' );
 INSERT INTO DetalleEmpresa( codDetalleEmpresa,razonSocial,ruc,telefono,email,direccion,estadoDetEmp)VALUES('DE0000019','consorcio huancavelica', '20554476679', '985636985', 'consorciohuancavelica@hotmail.com','calle joaquin bernal 215 of.1102-lince','1' );
 INSERT INTO DetalleEmpresa( codDetalleEmpresa,razonSocial,ruc,telefono,email,direccion,estadoDetEmp)VALUES('DE0000020','san martin contratistas generales sa ', '20102078781', '954212365', 'san martin@hotmail.com','av.pedro miotta n° 103 san juan miraflores lima','1' );

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
 INSERT INTO Contratista(codContratista ,codDetalleEmpresa,nombreEncargado,tipoEmpresa,estadoContratista )VALUES('C00000001','DE0000001','ronceros', 'juridica','1' );
 INSERT INTO Contratista(codContratista ,codDetalleEmpresa,nombreEncargado,tipoEmpresa,estadoContratista )VALUES('C00000002','DE0000002','leandro hilasaca', 'juridica','1' );
 INSERT INTO Contratista(codContratista ,codDetalleEmpresa,nombreEncargado,tipoEmpresa,estadoContratista )VALUES('C00000003','DE0000003','guillermo mori', 'juridica','1' );
 INSERT INTO Contratista(codContratista ,codDetalleEmpresa,nombreEncargado,tipoEmpresa,estadoContratista )VALUES('C00000004','DE0000004','señor lorenzo', 'juridica','1' );
 INSERT INTO Contratista(codContratista ,codDetalleEmpresa,nombreEncargado,tipoEmpresa,estadoContratista )VALUES('C00000005','DE0000005','leandro hilasaca', 'juridica','1' );
 INSERT INTO Contratista(codContratista ,codDetalleEmpresa,nombreEncargado,tipoEmpresa,estadoContratista )VALUES('C00000006','DE0000006','ing.corahua', 'juridica','1' );
 INSERT INTO Contratista(codContratista ,codDetalleEmpresa,nombreEncargado,tipoEmpresa,estadoContratista )VALUES('C00000007','DE0000007','señor soso', 'juridica','1' );
 INSERT INTO Contratista(codContratista ,codDetalleEmpresa,nombreEncargado,tipoEmpresa,estadoContratista )VALUES('C00000008','DE0000008','ing.julio', 'juridica','1' );
 INSERT INTO Contratista(codContratista ,codDetalleEmpresa,nombreEncargado,tipoEmpresa,estadoContratista )VALUES('C00000009','DE0000009','leandro hilasaca', 'juridica','1' );
 INSERT INTO Contratista(codContratista ,codDetalleEmpresa,nombreEncargado,tipoEmpresa,estadoContratista )VALUES('C00000010','DE0000010','ing.arturo', 'juridica','1' );
 INSERT INTO Contratista(codContratista ,codDetalleEmpresa,nombreEncargado,tipoEmpresa,estadoContratista )VALUES('C00000011','DE0000011','ing.ramirez', 'juridica','1' );
 INSERT INTO Contratista(codContratista ,codDetalleEmpresa,nombreEncargado,tipoEmpresa,estadoContratista )VALUES('C00000012','DE0000012','ing.mendiola', 'juridica','1' );
 INSERT INTO Contratista(codContratista ,codDetalleEmpresa,nombreEncargado,tipoEmpresa,estadoContratista )VALUES('C00000013','DE0000013','lic. sancher', 'juridica','1' );
 INSERT INTO Contratista(codContratista ,codDetalleEmpresa,nombreEncargado,tipoEmpresa,estadoContratista )VALUES('C00000014','DE0000014','señor estrada', 'juridica','1' );
 INSERT INTO Contratista(codContratista ,codDetalleEmpresa,nombreEncargado,tipoEmpresa,estadoContratista )VALUES('C00000015','DE0000015','ing.soto', 'juridica','1' );
 INSERT INTO Contratista(codContratista ,codDetalleEmpresa,nombreEncargado,tipoEmpresa,estadoContratista )VALUES('C00000016','DE0000016','señor ramon montero', 'juridica','1' );
 INSERT INTO Contratista(codContratista ,codDetalleEmpresa,nombreEncargado,tipoEmpresa,estadoContratista )VALUES('C00000017','DE0000017','ing.karol', 'juridica','1' );
 INSERT INTO Contratista(codContratista ,codDetalleEmpresa,nombreEncargado,tipoEmpresa,estadoContratista )VALUES('C00000018','DE0000018','ign.bannesa', 'juridica','1' );
 INSERT INTO Contratista(codContratista ,codDetalleEmpresa,nombreEncargado,tipoEmpresa,estadoContratista )VALUES('C00000019','DE0000019','ing.artiaga', 'juridica','1' );
 INSERT INTO Contratista(codContratista ,codDetalleEmpresa,nombreEncargado,tipoEmpresa,estadoContratista )VALUES('C00000020','DE0000020','ing.rosendo', 'juridica','1' );
  
  
  
  
  
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
  INSERT INTO Alquiler(codAlquiler ,codContratista,codUsuario ,tipoDocumento,numeroSerie,numeroDocumento,subTotal,igv ,total,estadoAlquiler )
  VALUES('A00000001','C00000001','U00000001','factura','001-00001','001', '30,000.00',' 5,400.00','35,400.00','1' );
  INSERT INTO Alquiler(codAlquiler ,codContratista,codUsuario ,tipoDocumento,numeroSerie,numeroDocumento,subTotal,igv ,total,estadoAlquiler )
  VALUES('A00000002','C00000002','U00000002', 'factura', '001-00002', '002', '25,000.00', ' 4,500.00', ' 29,500.00','1' );
  INSERT INTO Alquiler(codAlquiler ,codContratista,codUsuario ,tipoDocumento,numeroSerie,numeroDocumento,subTotal,igv ,total,estadoAlquiler )
  VALUES('A00000003','C00000003','U00000003', 'factura', '001-00003', '003', '15,000.00', ' 2,700.00', ' 17,700.00','1' );
  INSERT INTO Alquiler(codAlquiler ,codContratista,codUsuario ,tipoDocumento,numeroSerie,numeroDocumento,subTotal,igv ,total,estadoAlquiler )
  VALUES('A00000004','C00000004','U00000004', 'factura', '001-00004', '004', '3,000.00', ' 540.00', ' 3,540.00','1' );
  INSERT INTO Alquiler(codAlquiler ,codContratista,codUsuario ,tipoDocumento,numeroSerie,numeroDocumento,subTotal,igv ,total,estadoAlquiler )
  VALUES('A00000005','C00000005','U00000005', 'factura', '001-00005', '005', '8,500.00', ' 1,530.00', ' 10,030.00','1' );
  INSERT INTO Alquiler(codAlquiler ,codContratista,codUsuario ,tipoDocumento,numeroSerie,numeroDocumento,subTotal,igv ,total,estadoAlquiler )
  VALUES('A00000006','C00000006','U00000006', 'factura', '001-00006', '006', '11,000.00', ' 1,980.00', ' 12,980.00','1' );
  INSERT INTO Alquiler(codAlquiler ,codContratista,codUsuario ,tipoDocumento,numeroSerie,numeroDocumento,subTotal,igv ,total,estadoAlquiler )
  VALUES('A00000007','C00000007','U00000007', 'factura', '001-00007', '007', '13,000.00', ' 2,340.00', ' 15,340.00','1' );
  INSERT INTO Alquiler(codAlquiler ,codContratista,codUsuario ,tipoDocumento,numeroSerie,numeroDocumento,subTotal,igv ,total,estadoAlquiler )
  VALUES('A00000008','C00000008','U00000008', 'factura', '001-00008', '008', '65,000.00', ' 11,700.00', ' 76,400.00','1' );
  INSERT INTO Alquiler(codAlquiler ,codContratista,codUsuario ,tipoDocumento,numeroSerie,numeroDocumento,subTotal,igv ,total,estadoAlquiler )
  VALUES('A00000009','C00000009','U00000009', 'factura', '001-00009', '009', '125,000.00', ' 22,500.00', ' 147,500.00','1' );
  INSERT INTO Alquiler(codAlquiler ,codContratista,codUsuario ,tipoDocumento,numeroSerie,numeroDocumento,subTotal,igv ,total,estadoAlquiler )
  VALUES('A00000010','C00000010','U00000010', 'factura', '001-00010', '010', '800.00', ' 144.00', '944.00','1' );
  INSERT INTO Alquiler(codAlquiler ,codContratista,codUsuario ,tipoDocumento,numeroSerie,numeroDocumento,subTotal,igv ,total,estadoAlquiler )
  VALUES('A00000011','C00000011','U00000011', 'factura', '002-00001', '011', '1,000.00', ' 180.00', ' 1,180.00','1' );
  INSERT INTO Alquiler(codAlquiler ,codContratista,codUsuario ,tipoDocumento,numeroSerie,numeroDocumento,subTotal,igv ,total,estadoAlquiler )
  VALUES('A00000012','C00000012','U00000012', 'factura', '002-00002', '012', '15,450.00', ' 2,781.00', ' 18,231.00','1' );
  INSERT INTO Alquiler(codAlquiler ,codContratista,codUsuario ,tipoDocumento,numeroSerie,numeroDocumento,subTotal,igv ,total,estadoAlquiler )
  VALUES('A00000013','C00000013','U00000013', 'factura', '002-00003', '013', '45,000.00', ' 8,100.00', ' 53,100.00','1' );
  INSERT INTO Alquiler(codAlquiler ,codContratista,codUsuario ,tipoDocumento,numeroSerie,numeroDocumento,subTotal,igv ,total,estadoAlquiler )
  VALUES('A00000014','C00000014','U00000014', 'factura', '002-00004', '014', '87,000.00', ' 15,660.00', ' 102,660.00','1' );
  INSERT INTO Alquiler(codAlquiler ,codContratista,codUsuario ,tipoDocumento,numeroSerie,numeroDocumento,subTotal,igv ,total,estadoAlquiler )
  VALUES('A00000015','C00000015','U00000015', 'factura', '002-00005', '015', '12,000.00', ' 2,160.00', ' 14,160.00','1' );
  INSERT INTO Alquiler(codAlquiler ,codContratista,codUsuario ,tipoDocumento,numeroSerie,numeroDocumento,subTotal,igv ,total,estadoAlquiler )
  VALUES('A00000016','C00000016','U00000016', 'factura', '002-00006', '016', '65,654.00', ' 11,817.72', ' 77,471.72','1' );
  INSERT INTO Alquiler(codAlquiler ,codContratista,codUsuario ,tipoDocumento,numeroSerie,numeroDocumento,subTotal,igv ,total,estadoAlquiler )
  VALUES('A00000017','C00000017','U00000017', 'boleta', '002-00007', '017', '300.00', ' 0.00', '300.00','1' );
  INSERT INTO Alquiler(codAlquiler ,codContratista,codUsuario ,tipoDocumento,numeroSerie,numeroDocumento,subTotal,igv ,total,estadoAlquiler )
  VALUES('A00000018','C00000018','U00000018', 'boleta', '002-00008', '018', '200.00', ' 0.00', '200.00','1' );
  INSERT INTO Alquiler(codAlquiler ,codContratista,codUsuario ,tipoDocumento,numeroSerie,numeroDocumento,subTotal,igv ,total,estadoAlquiler )
  VALUES('A00000019','C00000019','U00000019', 'boleta', '002-00009', '019', '160.00', '0.00', '160.00','1' );
  INSERT INTO Alquiler(codAlquiler ,codContratista,codUsuario ,tipoDocumento,numeroSerie,numeroDocumento,subTotal,igv ,total,estadoAlquiler )
  VALUES('A00000020','C00000020','U00000020', 'boleta', '002-00010', '020', '350.00', ' 0.00', '350.00','1' );
--Creación de la tabla Equi
CREATE TABLE Equipo
  (
    codEquipo CHAR(9),
    nombre    VARCHAR2(50),
    marca     VARCHAR(20),
    modelo    VARCHAR2(100),
    placa     CHAR(7),
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
   INSERT INTO Equipo(codEquipo ,nombre,marca ,modelo,placa,categoria,horometro,kilometraje ,anioFabricacion ,operativo,observaciones,descripcion,estadoEquipo  )
   VALUES('E00000001','TRAILER CAMABAJA	','INTERNATIONAL', 'RECONCISA', 'F8B-99e', 'vehiculos', '0.00', ' 23039', '2012','operativo','activo todas las partes de la camabaja','se utiliza para traslado','1' );
   INSERT INTO Equipo(codEquipo ,nombre,marca ,modelo,placa,categoria,horometro,kilometraje ,anioFabricacion ,operativo,observaciones,descripcion,estadoEquipo  )
   VALUES('E00000002','TRAILER CAMABAJA	','SCANNIA', 'GA6X4', 'V4Q-826', 'vehiculos', '0.00', ' 23040', '2006','operativo','activo todas las partes de la camabaja','se utiliza para traslado','1' );
   INSERT INTO Equipo(codEquipo ,nombre,marca ,modelo,placa,categoria,horometro,kilometraje ,anioFabricacion ,operativo,observaciones,descripcion,estadoEquipo  )
   VALUES('E00000003','VOLQUETE','MERCEDES BENZ', 'WDB9321621L220356', 'B2S-926', 'vehiculos', '0.00', ' 23054', '2007','operativo','activo todas las partes del volquete','se utiliza para traslado','1' );
   INSERT INTO Equipo(codEquipo ,nombre,marca ,modelo,placa,categoria,horometro,kilometraje ,anioFabricacion ,operativo,observaciones,descripcion,estadoEquipo  )
   VALUES('E00000004','VOLQUETE','DONG FENG', 'DFL3251A', 'F7X-734', 'vehiculos', '0.00', ' 23054', '2008','operativo','activo todas las partes del volquete','se utiliza para traslado','1' );
   INSERT INTO Equipo(codEquipo ,nombre,marca ,modelo,placa,categoria,horometro,kilometraje ,anioFabricacion ,operativo,observaciones,descripcion,estadoEquipo  )
   VALUES('E00000005','VOLQUETE','SCANNIA', 'P-360 CB6x4', 'B8A-823', 'vehiculos', '0.00', ' 23087', '2006','operativo','activo todas las partes del volquete','se utiliza para traslado','1' );
   INSERT INTO Equipo(codEquipo ,nombre,marca ,modelo,placa,categoria,horometro,kilometraje ,anioFabricacion ,operativo,observaciones,descripcion,estadoEquipo  )
   VALUES('E00000006','VOLQUETE','SCANNIA', 'P-360 CB6x4', 'B8A-824', 'vehiculos', '0.00', ' 23023', '2006','operativo','activo todas las partes del volquete','se utiliza para traslado','1' );
   INSERT INTO Equipo(codEquipo ,nombre,marca ,modelo,placa,categoria,horometro,kilometraje ,anioFabricacion ,operativo,observaciones,descripcion,estadoEquipo  )
   VALUES('E00000007','CARGADOR FRONTAL','SEM', '659', '      ', 'maquinarias', '234.3', '00', '2010','operativo','activo todas las partes del cargador frontal','se utiliza para cargado de desmonte','1' );
   INSERT INTO Equipo(codEquipo ,nombre,marca ,modelo,placa,categoria,horometro,kilometraje ,anioFabricacion ,operativo,observaciones,descripcion,estadoEquipo  )
   VALUES('E00000008','CARGADOR FRONTAL','JCB', '456ZX-FELP-007', '      ','maquinarias', '657.4', '00', '2007','operativo','activo todas las partes del cargador','se utiliza para cargado de desmonte','1' );
   INSERT INTO Equipo(codEquipo ,nombre,marca ,modelo,placa,categoria,horometro,kilometraje ,anioFabricacion ,operativo,observaciones,descripcion,estadoEquipo  )
   VALUES('E00000009','EXCAVADORA ','HYUNDAI', '305LC7', '      ', 'maquinarias', '876.5', ' 00', '2009','operativo','activo todas las partes de la excavadora','se utiliza para excavacion','1' );
   INSERT INTO Equipo(codEquipo ,nombre,marca ,modelo,placa,categoria,horometro,kilometraje ,anioFabricacion ,operativo,observaciones,descripcion,estadoEquipo  )
   VALUES('E00000010','EXCAVADORA','JCB', 'JS200LC', '    ', 'maquinarias', '432.4', ' 00', '2007','operativo','activo todas las partes de la excavadora','se utiliza para excavacion','1' );
   INSERT INTO Equipo(codEquipo ,nombre,marca ,modelo,placa,categoria,horometro,kilometraje ,anioFabricacion ,operativo,observaciones,descripcion,estadoEquipo  )
   VALUES('E00000011','EXCAVADORA','KOMATSU', 'PC200 LC-88', '      ', 'maquinarias', '435.7', ' 00', '2008','operativo','activo todas las partes de la excavadora','se utiliza para excavacion','1' );
   INSERT INTO Equipo(codEquipo ,nombre,marca ,modelo,placa,categoria,horometro,kilometraje ,anioFabricacion ,operativo,observaciones,descripcion,estadoEquipo  )
   VALUES('E00000012','MOTONIVELADORA','CHAMPION', '720H', '     ', 'maquinaria', '675.9', ' 00', '2003','operativo','activo todas las partes de la motoniveladora','se utiliza para nivelar terreno','1' );
   INSERT INTO Equipo(codEquipo ,nombre,marca ,modelo,placa,categoria,horometro,kilometraje ,anioFabricacion ,operativo,observaciones,descripcion,estadoEquipo  )
   VALUES('E00000013','MOTONIVELADORA','CARTEPILLAR', '140H', '     ', 'maquinaria', '435.8', '00', '2008','operativo','activo todas las partes de la motoniveladora','se utiliza para nivelar terreno','1' );
   INSERT INTO Equipo(codEquipo ,nombre,marca ,modelo,placa,categoria,horometro,kilometraje ,anioFabricacion ,operativo,observaciones,descripcion,estadoEquipo  )
   VALUES('E00000014','RETROEXCAVADORA','CASE', '580 SUPER N', '     ', 'maquinaria', '9878.9', ' 00', '2011','operativo','activo todas las partes de la retroexcavadora','se utiliza para realizar sanjas con pala y martillo','1' );
   INSERT INTO Equipo(codEquipo ,nombre,marca ,modelo,placa,categoria,horometro,kilometraje ,anioFabricacion ,operativo,observaciones,descripcion,estadoEquipo  )
   VALUES('E00000015','RODILLO','CARTEPILLAR', 'CS-533C', '      ', 'maquinaria', '4535.6', ' 00', '1997','operativo','activo todas las partes del rodillo','se utiliza para compactacion','1' );
   INSERT INTO Equipo(codEquipo ,nombre,marca ,modelo,placa,categoria,horometro,kilometraje ,anioFabricacion ,operativo,observaciones,descripcion,estadoEquipo  )
   VALUES('E00000016','RODILLO','LIUGONG', '612H', '      ', 'maquinaria', '345.7', ' 00', '2009','operativo','activo todas las partes del rodillo','se utiliza para compactacion','1' );
   INSERT INTO Equipo(codEquipo ,nombre,marca ,modelo,placa,categoria,horometro,kilometraje ,anioFabricacion ,operativo,observaciones,descripcion,estadoEquipo  )
   VALUES('E00000017','TRACTOR AGRICOLA','JHON DEERE', '5065E', '     ', 'maquinaria', '787.9', '00', '2011','operativo','activo todas las partes del tractor','se utiliza para arar el huano','1' );
   INSERT INTO Equipo(codEquipo ,nombre,marca ,modelo,placa,categoria,horometro,kilometraje ,anioFabricacion ,operativo,observaciones,descripcion,estadoEquipo  )
   VALUES('E00000018','TRACTOR AGRICOLA','JHON DEERE', '5065E', '     ', 'maquinaria', '9878.9', ' 00', '2013','operativo','activo todas las partes del tractor','se utiliza para arar el huano','1' );
   INSERT INTO Equipo(codEquipo ,nombre,marca ,modelo,placa,categoria,horometro,kilometraje ,anioFabricacion ,operativo,observaciones,descripcion,estadoEquipo  )
   VALUES('E00000019','TRACTOR ORUGA','CAT', 'D8R - 659', '      ', 'maquinaria', '768.9', ' 00', '1998','operativo','activo todas las partes del tractor oruga','se utiliza para movimiento de tierra','1' );
   INSERT INTO Equipo(codEquipo ,nombre,marca ,modelo,placa,categoria,horometro,kilometraje ,anioFabricacion ,operativo,observaciones,descripcion,estadoEquipo  )
   VALUES('E00000020','TRACTOR ORUGA','CAT', 'D8R - 658', '       ', 'maquinaria', '7656.9', '00', '1998','operativo','activo todas las partes del tractor oruga','se utiliza para movimiento de tierra','1' );
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
  INSERT INTO DetalleAlquiler(codDetalleAlquiler ,codAlquiler,codEquipo,precio,horas ,subTotal ,estadoDetAlq )
  VALUES('DA000001','A00000001','E00000001','1,200.00','2','2400.00','1' );
  INSERT INTO DetalleAlquiler(codDetalleAlquiler ,codAlquiler,codEquipo,precio,horas ,subTotal ,estadoDetAlq )
  VALUES('DA000002','A00000002','E00000002','1,200.00','2','2400.00','1' );
  INSERT INTO DetalleAlquiler(codDetalleAlquiler ,codAlquiler,codEquipo,precio,horas ,subTotal ,estadoDetAlq )
  VALUES('DA000003','A00000003','E00000003','130.00','30','3900.00','1' );
  INSERT INTO DetalleAlquiler(codDetalleAlquiler ,codAlquiler,codEquipo,precio,horas ,subTotal ,estadoDetAlq )
  VALUES('DA000004','A00000004','E00000004','110.00','25','2750','1' );
  INSERT INTO DetalleAlquiler(codDetalleAlquiler ,codAlquiler,codEquipo,precio,horas ,subTotal ,estadoDetAlq )
  VALUES('DA000005','A00000005','E00000005','80.00','20','1600','1' );
  INSERT INTO DetalleAlquiler(codDetalleAlquiler ,codAlquiler,codEquipo,precio,horas ,subTotal ,estadoDetAlq )
  VALUES('DA000006','A00000006','E00000006','140.00','34','4760.00','1' );
  INSERT INTO DetalleAlquiler(codDetalleAlquiler ,codAlquiler,codEquipo,precio,horas ,subTotal ,estadoDetAlq )
  VALUES('DA000007','A00000007','E00000007','170.00','10.5','1785.00','1' );
  INSERT INTO DetalleAlquiler(codDetalleAlquiler ,codAlquiler,codEquipo,precio,horas ,subTotal ,estadoDetAlq )
  VALUES('DA000008','A00000008','E00000008','120.00','8.9','1068.00','1' );
  INSERT INTO DetalleAlquiler(codDetalleAlquiler ,codAlquiler,codEquipo,precio,horas ,subTotal ,estadoDetAlq )
  VALUES('DA000009','A00000009','E00000009','220.00','45.8','10076.00','1' );
  INSERT INTO DetalleAlquiler(codDetalleAlquiler ,codAlquiler,codEquipo,precio,horas ,subTotal ,estadoDetAlq )
  VALUES('DA000010','A00000010','E00000010','240.00','38.9','9336.00','1' );
  INSERT INTO DetalleAlquiler(codDetalleAlquiler ,codAlquiler,codEquipo,precio,horas ,subTotal ,estadoDetAlq )
  VALUES('DA000011','A00000011','E00000011','180.00','14.3','2574.00','1' );
  INSERT INTO DetalleAlquiler(codDetalleAlquiler ,codAlquiler,codEquipo,precio,horas ,subTotal ,estadoDetAlq )
  VALUES('DA000012','A00000012','E00000012','110.00','12.8','1408.00','1' );
  INSERT INTO DetalleAlquiler(codDetalleAlquiler ,codAlquiler,codEquipo,precio,horas ,subTotal ,estadoDetAlq )
  VALUES('DA000013','A00000013','E00000013','150.00','23.8','3570.00','1' );
  INSERT INTO DetalleAlquiler(codDetalleAlquiler ,codAlquiler,codEquipo,precio,horas ,subTotal ,estadoDetAlq )
  VALUES('DA000014','A00000014','E00000014','140.00','23.7','3318.00','1' );
  INSERT INTO DetalleAlquiler(codDetalleAlquiler ,codAlquiler,codEquipo,precio,horas ,subTotal ,estadoDetAlq )
  VALUES('DA000015','A00000015','E00000015','100.00','5.6','001','1' );
  INSERT INTO DetalleAlquiler(codDetalleAlquiler ,codAlquiler,codEquipo,precio,horas ,subTotal ,estadoDetAlq )
  VALUES('DA000016','A00000016','E00000016','120.00','12.5','1500.00','1' );
  INSERT INTO DetalleAlquiler(codDetalleAlquiler ,codAlquiler,codEquipo,precio,horas ,subTotal ,estadoDetAlq )
  VALUES('DA000017','A00000017','E00000017','1300.00','3','3900.00','1' );
  INSERT INTO DetalleAlquiler(codDetalleAlquiler ,codAlquiler,codEquipo,precio,horas ,subTotal ,estadoDetAlq )
  VALUES('DA000018','A00000018','E00000018','1200.00','5','6000','1' );
  INSERT INTO DetalleAlquiler(codDetalleAlquiler ,codAlquiler,codEquipo,precio,horas ,subTotal ,estadoDetAlq )
  VALUES('DA000019','A00000019','E00000019','250.00','6.8','1700.00','1' );
  INSERT INTO DetalleAlquiler(codDetalleAlquiler ,codAlquiler,codEquipo,precio,horas ,subTotal ,estadoDetAlq )
  VALUES('DA000020','A00000020','E00000020','250.00','15.8','3950.00','1' );
  
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
