INSERT INTO perfiles(id,nombre) VALUES (1,'usuario');
INSERT INTO perfiles(id,nombre) VALUES (2,'moderador');
INSERT INTO perfiles(id,nombre) VALUES (3,'admin');

INSERT INTO usuarios(id,nombre,correo_electronico,contrasena) VALUES (1,'Baofu','logical_result@atlus.com','$2a$12$C.TCV79WNGm7e.FAgEtg.eOjP2ZgK/sDtuqR5HTs7e1fG96XC9d8O');

INSERT INTO perfiles_usuarios(usuario_id,perfil_id) VALUES(1,1);
INSERT INTO perfiles_usuarios(usuario_id,perfil_id) VALUES(1,3);