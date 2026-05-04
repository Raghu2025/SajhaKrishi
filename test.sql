CREATE TABLE role (
  id bigint PRIMARY KEY,
  name varchar(100)
);

CREATE TABLE users (
  id bigint PRIMARY KEY,
  full_name varchar(100),
  phone_number varchar(100),
  email varchar(25),
  address varchar(255),
  district varchar(50),
  role_id bigint REFERENCES role(id)
);

CREATE TABLE category (
  id bigint PRIMARY KEY,
  name varchar(255)
);

CREATE TABLE equipment (
  id bigint PRIMARY KEY,
  name varchar(255),
  category_id bigint REFERENCES category(id),
  owner_id bigint REFERENCES users(id),
  brand varchar(100),
  price_per_day decimal(10,2),
  availability_status varchar(50)
);

CREATE TABLE bookings (
  id bigint PRIMARY KEY,
  equipment_id bigint REFERENCES equipment(id),
  kisan_id bigint REFERENCES users(id),
  owner_id bigint REFERENCES users(id),
  start_date date,
  end_date date,
  total_price decimal(10,2),
  status varchar(20)
);

CREATE TABLE payments (
  id bigint PRIMARY KEY,
  booking_id bigint REFERENCES bookings(id),
  amount decimal(10,2),
  payment_method varchar(50),
  payment_status varchar(20)
);