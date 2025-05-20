-- 🧍‍♂️ Insertar un peluquero
INSERT INTO APP_USER (id, email, password, role, name, enabled)
VALUES (100, 'lucas@pelu.com', 'dummy-password', 'PELUQUERO', 'Lucas', true);

-- 👤 Insertar un cliente (para que las citas tengan cliente)
INSERT INTO APP_USER (id, email, password, role, name, enabled)
VALUES (200, 'eva@cliente.com', 'dummy-password', 'CLIENTE', 'Eva', true);

-- 📅 Insertar citas ya cogidas con ese peluquero
INSERT INTO APP_CITA (id, dia, hora, peluquero_id, cliente_id)
VALUES
  (1, 'LUNES', '9:30', 100, 200),
  (2, 'MIÉRCOLES', '13:30', 100, 200);
