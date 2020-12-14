insert into company values('Pharma plus','C1','9873455');

insert into company values('Pharmeasy','C2','654321');

insert into company values('Viva Laboratories','C3','123456');

insert into company values('Cipla','C4','178496');

insert into company values('Wockhardt','C5','123375');

insert into company values('Glenmark','C6','623446');
use pharmacy;
insert into company values('Lupin','C7','999556');

insert into company values('Biocon','C8','434756');


insert into medicine(med_name, med_id, med_price,med_expdate,quant_med,med_mfg,com_id) values('Combiflam', 'M4', 30, '2022-08-09', 80, '2020-08-09', 'C4');

insert into medicine(med_name, med_id, med_price,med_expdate,quant_med,med_mfg,com_id) values('Cefixime', 'M5', 50, '2022-07-09', 400, '2020-07-09', 'C5');

insert into medicine(med_name, med_id, med_price,med_expdate,quant_med,med_mfg,com_id) values('Domstal', 'M6', 40, '2022-06-09', 800, '2020-06-09', 'C6');

insert into medicine(med_name, med_id, med_price,med_expdate,quant_med,med_mfg,com_id) values('Folic Acid', 'M7', 50, '2022-02-09', 60, '2020-02-09', 'C7');
insert into medicine(med_name, med_id, med_price,med_expdate,quant_med,med_mfg,com_id) values('Crocin', 'M1', 10, '2022-09-09', 5, '2020-09-09', 'C1');

insert into medicine(med_name, med_id, med_price,med_expdate,quant_med,med_mfg,com_id) values('Tobramycin', 'M2', 20, '2022-09-09', 50, '2020-09-09', 'C2');
insert into medicine(med_name, med_id, med_price,med_expdate,quant_med,med_mfg,com_id) values('Paracetamol', 'M3', 30, '2022-09-09', 500, '2020-09-09', 'C3');

insert into patient(pat_num, pat_name,pat_age,pat_gender,pat_id) values('123456789', 'Priyesh', 18, 'm', 'P4');

insert into patient(pat_num, pat_name,pat_age,pat_gender,pat_id) values('987654321', 'Janvi', 19, 'f', 'P5');