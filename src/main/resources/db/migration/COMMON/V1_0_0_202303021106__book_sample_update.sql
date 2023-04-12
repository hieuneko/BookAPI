UPDATE books
SET subtitle  = 'The Philosopher Stone',
    publisher = 'Bloomsbury Publishing',
    isbn13    = '1231231231231',
    price     = '$22.99',
    year      = 1997,
    rating    = 4
WHERE title = 'Harry Potter';

UPDATE books
SET subtitle  = 'Softcover',
    publisher = 'Zack Zombie',
    isbn13    = '9780986444135',
    price     = '$3.71',
    year      = 2015,
    rating    = 4.5
WHERE title = 'Diary of a Minecraft Zombie';