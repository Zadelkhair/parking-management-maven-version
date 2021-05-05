drop table user_roles;
ALTER TABLE users
ADD id_role int;
ALTER TABLE users
ADD CONSTRAINT Fk_R
FOREIGN KEY (id_role) REFERENCES roles(id);