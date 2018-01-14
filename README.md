Пользователь задает пароль, состоящий из букв a-z/ A-z/ цифр 0-9. Максимальное число символов N<6. 
Этот пароль хэшируется с помощью хэш-функции SHA1 (специальная утилита включена в проект через maven).

Нужно подобрать пароль по хэшу. Т.е.просто перебором берем каждую возможную комбинацию из нашего алфавита и цифр (длина не более N) и сравниваем ее хэш с хэшом от пароля, который ввел юзер. Если хэш совпал, значит, мы нашли пароль и возвращаем его в main.
Перебор осуществляется в отдельном методе generatePassword(). Метод рекурсивно последовательно генерирует различные комбинации, пробегаясь по массиву из алфавита. Например, ab, ac, ad... aaa, aab, aac, aad и т.д. И у каждого слова мы сравниваем значение хэша SHA1 с хэшом пароля пока не найдем его.

Задание в том, чтобы оптимизировать этот перебор, используя многопоточность.
Мое решение пока состоит в том, чтобы создать пул из m потоков и проверять каждое слово в отдельном потоке. Т.е., например, мы создали пул из 5 потоков, сгенирировали слово ab - отдали в пул на проверку. Следующее слово ac - отдали в пул и т.д. В пуле в методе run() потоки осуществляют проверку. Если хэш совпадает, значит, он должен выбросить результат boolean found = true, остановить работу и закрыть пул, чтобы другие потоки тоже остановились. Флаг boolean found = true нужен для того, чтобы остановить дальнейшую логику по генерации слов и закрыть пул.

У меня почему-то это работает не так, как нужно. После того, как слово найдено, оно печатается в консоль и передается наверх. В методе main я хочу его напечатать. Но, видимо, каким-то фоном другие потоки успевают дальше отработать и в итоге пока дойдет до main результат становится другим. 
Ты это увидишь.

В общем, задача пока такая - понять, почему что-то идет не так и сделать, чтобы все было, как я описал в решении:)

Ну и еще подумай, плиз, может, у тебя будут какие-то другие идеи, как можно еще распараллелить эту логику по перебору слов. Мне нужно именно использовать многопоточность. Как? Конкретного направления нет. Надо что-то оптимизировать, чтобы было быстрее. Пока в этом решении у меня ни хрена не быстрее получается. Что для пула в 3 потока на слове из 4-х, допустим, символов, что на пуле из 10 потоков. Т.е. фигня какая-то. Помоги разобраться, плиз
 



