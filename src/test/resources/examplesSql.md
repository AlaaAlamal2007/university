#examplesSql statements
__________________________
1)SELECT s.name,s.id,u.name,ad.city_name
FROM students as s
left join universities as u on  s.university_id=u.id
join addresses as ad on s.address_id=ad.id;
_____________________________
2)INSERT INTO public.addresses(
id, city_name, street_name, street_number)
VALUES (10, 'Bet Hanena' , 'Almajd street',34);
________________________________
3)INSERT INTO public.students(
id, name, gender, graduated, birth_date, registration_date, payment_fee, email,
university_id, address_id)
VALUES (16, 'Majed', 'female', false, ' 1985-06-01 16:10:19.893354+05:30'
, ' 2000-06-01 16:10:19.893354+05:30', 5000.780, 'Majed@hotmail.com', 3,5);
_______________________________
4)UPDATE public.addresses
SET  city_name='Althouri', street_name='Almanar', street_number=12
WHERE id=5;
___________________________________
5)UPDATE public.students
SET  graduated=true, graduated_date=' 2023-06-01 16:10:19.893354+05:30',
payment_fee=8000
WHERE id=10;
______________________________________
6)DELETE FROM public.students
WHERE id=16;
_________________________________
7)select s.id,s.name,u.name from students as s
left join universities as u on  s.university_id=u.id;
_________________________________
8)SELECT id, name, gender, graduated, birth_date,
registration_date, graduated_date, payment_fee, email, university_id, address_id
FROM public.students;
___________________________________
9)SELECT *
FROM public.students;
__________________________________
10)SELECT *
FROM public.students where id=10
;
