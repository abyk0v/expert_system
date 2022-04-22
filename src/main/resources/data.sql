-- Данный файл выполняется при старте приложения
-- и предназначен для заполнения таблиц БД

-- Данные по пациентам
MERGE INTO patient KEY (ID) VALUES (1, 'Aleksandr', 'Bykov', 26);
MERGE INTO patient KEY (ID) VALUES (2, 'Иван', 'Иванов', 21);
MERGE INTO patient KEY (ID) VALUES (3, 'Петр', 'Петров', 43);
MERGE INTO patient KEY (ID) VALUES (4, 'Илья', 'Сидоров', 33);
MERGE INTO patient KEY (ID) VALUES (5, 'Антон', 'Антонов', 45);
MERGE INTO patient KEY (ID) VALUES (6, 'Олег', 'Олегов', 19);
MERGE INTO patient KEY (ID) VALUES (7, 'Андрей', 'Андреев', 15);

-- Данные по диагнозам
MERGE INTO diagnosis KEY (ID) VALUES (1, 'Грипп');
MERGE INTO diagnosis KEY (ID) VALUES (2, 'Корь');
MERGE INTO diagnosis KEY (ID) VALUES (3, 'Пневмония');

-- Данные по симптомам
MERGE INTO symptom KEY (ID) VALUES (1, 'Жар');
MERGE INTO symptom KEY (ID) VALUES (2, 'Озноб');
MERGE INTO symptom KEY (ID) VALUES (3, 'Головная боль');
MERGE INTO symptom KEY (ID) VALUES (4, 'Насморк');
MERGE INTO symptom KEY (ID) VALUES (5, 'Усталость');
MERGE INTO symptom KEY (ID) VALUES (6, 'Кашель');
MERGE INTO symptom KEY (ID) VALUES (7, 'Температура');
MERGE INTO symptom KEY (ID) VALUES (8, 'Сыпь');
MERGE INTO symptom KEY (ID) VALUES (9, 'Конъюнктивит');
MERGE INTO symptom KEY (ID) VALUES (10, 'Отсутствие аппетита');
MERGE INTO symptom KEY (ID) VALUES (11, 'Боль в груди');
MERGE INTO symptom KEY (ID) VALUES (12, 'Насморк или заложенный нос');
MERGE INTO symptom KEY (ID) VALUES (13, 'боль (першение) в горле');
MERGE INTO symptom KEY (ID) VALUES (14, 'Боль в мышцах и суставах');

-- СВЯЗЫВАЕМ ДИАГНОЗЫ С СИМПТОМАМИ
-- Грипп (1) и его симптомы
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (1, 1);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (1, 2);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (1, 3);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (1, 5);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (1, 6);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (1, 12);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (1, 14);
-- Корь (2) и её симптомы
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (2, 5);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (2, 7);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (2, 9);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (2, 10);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (2, 4);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (2, 6);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (2, 13);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (2, 8);
-- Пневмония (3) и её симптомы
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (3, 6);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (3, 7);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (3, 5);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (3, 10);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (3, 11);

-- СВЯЗЫВАЕМ ПАЦИЕНТОВ С СИМПТОМАМИ
MERGE INTO patient_symptom KEY (id_p, id_s) VALUES (2, 1);
MERGE INTO patient_symptom KEY (id_p, id_s) VALUES (2, 2);
MERGE INTO patient_symptom KEY (id_p, id_s) VALUES (2, 3);
MERGE INTO patient_symptom KEY (id_p, id_s) VALUES (3, 4);
MERGE INTO patient_symptom KEY (id_p, id_s) VALUES (3, 5);
MERGE INTO patient_symptom KEY (id_p, id_s) VALUES (3, 6);
MERGE INTO patient_symptom KEY (id_p, id_s) VALUES (3, 3);
MERGE INTO patient_symptom KEY (id_p, id_s) VALUES (4, 7);
MERGE INTO patient_symptom KEY (id_p, id_s) VALUES (4, 8);
MERGE INTO patient_symptom KEY (id_p, id_s) VALUES (4, 6);
MERGE INTO patient_symptom KEY (id_p, id_s) VALUES (4, 9);
MERGE INTO patient_symptom KEY (id_p, id_s) VALUES (5, 5);
MERGE INTO patient_symptom KEY (id_p, id_s) VALUES (5, 7);
MERGE INTO patient_symptom KEY (id_p, id_s) VALUES (5, 10);
MERGE INTO patient_symptom KEY (id_p, id_s) VALUES (5, 4);
MERGE INTO patient_symptom KEY (id_p, id_s) VALUES (5, 8);
MERGE INTO patient_symptom KEY (id_p, id_s) VALUES (6, 6);
MERGE INTO patient_symptom KEY (id_p, id_s) VALUES (6, 7);
MERGE INTO patient_symptom KEY (id_p, id_s) VALUES (6, 5);
MERGE INTO patient_symptom KEY (id_p, id_s) VALUES (6, 10);
MERGE INTO patient_symptom KEY (id_p, id_s) VALUES (6, 11);
MERGE INTO patient_symptom KEY (id_p, id_s) VALUES (7, 11);
MERGE INTO patient_symptom KEY (id_p, id_s) VALUES (7, 6);
MERGE INTO patient_symptom KEY (id_p, id_s) VALUES (7, 5);
MERGE INTO patient_symptom KEY (id_p, id_s) VALUES (7, 1);
MERGE INTO patient_symptom KEY (id_p, id_s) VALUES (7, 3);

-- СВЯЗЫВАЕМ ПАЦИЕНТА С ДИАГНОЗОМ
MERGE INTO patient_diagnosis KEY (id_p, id_d) VALUES (2, 1);
MERGE INTO patient_diagnosis KEY (id_p, id_d) VALUES (3, 1);
MERGE INTO patient_diagnosis KEY (id_p, id_d) VALUES (4, 2);
MERGE INTO patient_diagnosis KEY (id_p, id_d) VALUES (5, 2);
MERGE INTO patient_diagnosis KEY (id_p, id_d) VALUES (6, 3);
MERGE INTO patient_diagnosis KEY (id_p, id_d) VALUES (7, 3);