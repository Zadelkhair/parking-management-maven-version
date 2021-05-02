ALTER TABLE sorties
ADD COLUMN id_camera int;

ALTER TABLE entrees
ADD COLUMN id_camera int;

ALTER TABLE sorties
ADD CONSTRAINT FK_camera_id_s
FOREIGN KEY (id_camera) REFERENCES cameras(id);

ALTER TABLE entrees
ADD CONSTRAINT FK_camera_id_e
FOREIGN KEY (id_camera) REFERENCES cameras(id);