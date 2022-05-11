-- Данный файл выполняется при старте приложения
-- и предназначен для заполнения таблиц БД

-- Данные по пациентам
MERGE INTO patient (id, name, surname, age) KEY (ID) VALUES (1, 'Алексей', 'Аксенов', 55);
MERGE INTO patient (id, name, surname, age) KEY (ID) VALUES (2, 'Иван', 'Иванов', 21);
MERGE INTO patient (id, name, surname, age) KEY (ID) VALUES (3, 'Петр', 'Петров', 43);
MERGE INTO patient (id, name, surname, age) KEY (ID) VALUES (4, 'Илья', 'Сидоров', 33);
MERGE INTO patient (id, name, surname, age) KEY (ID) VALUES (5, 'Антон', 'Антонов', 45);
MERGE INTO patient (id, name, surname, age) KEY (ID) VALUES (6, 'Олег', 'Олегов', 19);
MERGE INTO patient (id, name, surname, age) KEY (ID) VALUES (7, 'Андрей', 'Андреев', 15);

-- Данные по диагнозам
MERGE INTO diagnosis KEY (ID) VALUES (1, 'Грипп', '');
MERGE INTO diagnosis KEY (ID) VALUES (2, 'Корь', '');
MERGE INTO diagnosis KEY (ID) VALUES (3, 'Пневмония', 'Инфекционно-воспалительное заболевание, при котором, ' ||
                                                      'поражение распространяется на все составляющие' ||
                                                      ' легочной ткани.');
MERGE INTO diagnosis KEY (ID) VALUES (4, 'Ангина', 'Воспаление небных миндалин, как правило,' ||
                                                   'бактериальной этиологии. Выделяют катаральную ангину' ||
                                                   ', когда имеется только покраснение небных миндалин' ||
                                                   ', и гнойные формы.');
MERGE INTO diagnosis KEY (ID) VALUES (5, 'Конъюнктивит', 'Воспалительное полиэтиологическое поражение' ||
                                                         ' слизистой оболочки, которая покрывает склеру' ||
                                                         'и веко (конъюнктива)');
MERGE INTO diagnosis KEY (ID) VALUES (6, 'Гастрит', 'Заболевание, характеризующееся воспалительным' ||
                                                    'процессом слизистой оболочки желудка.');
MERGE INTO diagnosis KEY (ID) VALUES (7, 'Отравление', 'Пищевое отравление бывает микробным ' ||
                                                       'и не микробным.');

-- Данные по симптомам
-- MERGE INTO symptom KEY (ID) VALUES (1, 'Жар');
MERGE INTO symptom KEY (ID) VALUES (2, 'Озноб');
MERGE INTO symptom KEY (ID) VALUES (3, 'Головная боль');
MERGE INTO symptom KEY (ID) VALUES (4, 'Насморк');
MERGE INTO symptom KEY (ID) VALUES (5, 'Усталость (общая слабость)');
MERGE INTO symptom KEY (ID) VALUES (6, 'Кашель');
MERGE INTO symptom KEY (ID) VALUES (7, 'Температура');
MERGE INTO symptom KEY (ID) VALUES (8, 'Сыпь');
MERGE INTO symptom KEY (ID) VALUES (9, 'Конъюнктивит');
MERGE INTO symptom KEY (ID) VALUES (10, 'Отсутствие аппетита');
MERGE INTO symptom KEY (ID) VALUES (11, 'Боль в груди');
MERGE INTO symptom KEY (ID) VALUES (12, 'Насморк или заложенный нос');
MERGE INTO symptom KEY (ID) VALUES (13, 'боль (першение) в горле');
MERGE INTO symptom KEY (ID) VALUES (14, 'Боль в мышцах и суставах');
MERGE INTO symptom KEY (ID) VALUES (15, 'Резкая и сильная боль в горле');
MERGE INTO symptom KEY (ID) VALUES (16, 'Увеличение лимфатических узлов');
MERGE INTO symptom KEY (ID) VALUES (17, 'Жжение и зуд в глазах');
MERGE INTO symptom KEY (ID) VALUES (18, 'Гиперемия и отечность слизистой оболочки глаза');
MERGE INTO symptom KEY (ID) VALUES (19, 'Гнойный секрет отделяемый из глаз');
MERGE INTO symptom KEY (ID) VALUES (20, 'Боль в глазах');
MERGE INTO symptom KEY (ID) VALUES (21, 'Сильная боль в верхней части живота');
MERGE INTO symptom KEY (ID) VALUES (22, 'Тошнота');
MERGE INTO symptom KEY (ID) VALUES (23, 'Рвота');
MERGE INTO symptom KEY (ID) VALUES (24, 'Метеоризм');
MERGE INTO symptom KEY (ID) VALUES (25, 'Частый жидкий стул');
MERGE INTO symptom KEY (ID) VALUES (26, 'Острая боль в животе (спазмы)');

-- СВЯЗЫВАЕМ ДИАГНОЗЫ С СИМПТОМАМИ
-- Грипп (1) и его симптомы
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (1, 2);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (1, 3);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (1, 5);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (1, 6);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (1, 7);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (1, 12);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (1, 14);
-- Корь (2) и её симптомы
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (2, 5);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (2, 7);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (2, 9);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (2, 10);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (2, 13);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (2, 8);
-- Пневмония (3) и её симптомы
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (3, 6);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (3, 7);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (3, 5);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (3, 10);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (3, 11);
-- Ангина (4) и её симптомы
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (4, 15);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (4, 7);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (4, 5);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (4, 3);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (4, 16);
-- Конъюнктивит (5) и её симптомы
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (5, 17);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (5, 18);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (5, 19);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (5, 20);
-- Гастрит (6) и её симптомы
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (6, 21);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (6, 22);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (6, 23);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (6, 24);
-- Отравление (7) и её симптомы
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (7, 7);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (7, 22);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (7, 23);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (7, 25);
MERGE INTO diagnosis_symptom KEY (id_d, id_s) VALUES (7, 26);

-- СВЯЗЫВАЕМ ПАЦИЕНТОВ С СИМПТОМАМИ
-- MERGE INTO patient_symptom KEY (id_p, id_s) VALUES (2, 1);
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
-- MERGE INTO patient_symptom KEY (id_p, id_s) VALUES (7, 1);
MERGE INTO patient_symptom KEY (id_p, id_s) VALUES (7, 3);

-- СВЯЗЫВАЕМ ПАЦИЕНТА С ДИАГНОЗОМ
UPDATE patient SET diagnosis = 1 WHERE ID = 1;
UPDATE patient SET diagnosis = 1 WHERE ID = 2;
UPDATE patient SET diagnosis = 1 WHERE ID = 3;
UPDATE patient SET diagnosis = 2 WHERE ID = 4;
UPDATE patient SET diagnosis = 2 WHERE ID = 5;
UPDATE patient SET diagnosis = 3 WHERE ID = 6;
UPDATE patient SET diagnosis = 3 WHERE ID = 7;
