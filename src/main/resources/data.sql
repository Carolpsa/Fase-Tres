INSERT INTO tb_roles (id, name) VALUES (1, 'MEDICO');
INSERT INTO tb_roles (id, name) VALUES (2, 'ENFERMEIRO');
INSERT INTO tb_roles (id, name) VALUES (3, 'PACIENTE');

INSERT INTO tb_usuario (id, nome, senha, ativo) VALUES (1, 'Dr. João Silva',  'senha123', true);
INSERT INTO tb_usuario_role (user_id, role_id) VALUES (1, 1);

INSERT INTO tb_usuario (id, nome, senha, ativo) VALUES (2, 'Enf. Maria Santos', 'senha456', true);
INSERT INTO tb_usuario_role (user_id, role_id) VALUES (2, 2);

INSERT INTO tb_usuario (id, nome,  senha, ativo) VALUES (3, 'Pac. Carlos Oliveira', 'senha789', true);
INSERT INTO tb_usuario_role (user_id, role_id) VALUES (3, 3);