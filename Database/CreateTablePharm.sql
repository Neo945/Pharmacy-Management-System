drop database Pharmacy;
create database Pharmacy;
use Pharmacy;

create table employee(
emp_id char(6) primary key check(emp_id like 'E%'),
emp_name varchar(50) not null,
emp_add varchar(255) not null,
emp_email varchar(50) not null,
constraint email check(emp_email like '%_@%_._%'),
emp_pass varchar(50) unique not null,
emp_sal decimal(7,2),
emp_role varchar(10) check(emp_role in('Pharmacist', 'Cashier'))
);


create table employee_num(
emp_id char(6),
foreign key(emp_id) references employee(emp_id),
emp_num varchar(20)
);

create table pharmacist(
pemp_id char(6),
foreign key(pemp_id) references employee(emp_id),
primary key(pemp_id),
phar_degree blob
);

create table cashier(
cemp_id char(6),
foreign key(cemp_id) references employee(emp_id),
primary key(cemp_id),
year_of_exp integer
);

create table supplier(
supp_id integer primary key,
sup_name varchar(50)
);

create table supplier_contact(
sup_id integer,
foreign key(sup_id) references supplier(supp_id),
sup_num varchar(20)
);

create table patient(
pat_num varchar(20) not null,
pat_name varchar(50),
pat_age integer not null,
pat_gender char(1) check(pat_gender in('f','m')),
pat_id char(6) primary key check(pat_id like 'P%')
);

create table company(
comp_name varchar(50) not null,
com_id varchar(6) primary key check(com_id like 'C%'),
cmp_num varchar(20)
);

create table medicine(
med_name varchar(50) not null,
med_id char(6) primary key check(med_id like 'M%'),
med_price decimal(5,2) not null,
med_expdate date not null,
quant_med integer default 0,
med_mfg date not null,
constraint medicine_exp check(med_expdate>med_mfg),
com_id char(6) not null,
foreign key(com_id) references company(com_id)
);

create table bill(
bill_date date not null,
bill_id char(6) primary key check(bill_id like 'B%'),
pat_id char(6),
foreign key(pat_id) references patient(pat_id),
bill_amount decimal(5,2) not null
);


create table med_in_bill(
bill_id char(7),
foreign key(bill_id) references bill(bill_id),
med_id char(6),
foreign key(med_id) references medicine(med_id),
quantity integer not  null check(quantity>=1)
);
